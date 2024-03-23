package com.henry.config.dataBase.dataSource;

import com.henry.config.dataBase.mybatis.MyBatisInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * emma db dataSource 및 트랜잭션  설정
 * @author 김동진
 */
@Configuration
@MapperScan(value="com.henry.api.*", sqlSessionFactoryRef="sqlSessionFactoryEMMA")
public class DataSourceConfigEMMA {

    @Bean(name="dataSourceEMMA")
    @ConfigurationProperties(prefix = "datasource.emma")
    public DataSource dataSourceEMMA() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="sqlSessionFactoryEMMA")
    public SqlSessionFactory sqlSessionFactoryEMMA(@Qualifier("dataSourceEMMA") DataSource dataSourceEMMA, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceEMMA);
        
        // mybatis 설정
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCallSettersOnNulls(true); // null 인 컬럼도 resultType 에 출력
        configuration.setDefaultStatementTimeout(30); // 쿼리 타임아웃 30초 설정
        sqlSessionFactoryBean.setConfiguration(configuration);

        // mybatis plug in 에 쿼리로그를 남기는 Interceptor 객체 셋팅 
        Interceptor[] plugIns = {new MyBatisInterceptor()};
        sqlSessionFactoryBean.setPlugins(plugIns);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="sqlSessionTemplateEMMA")
    public SqlSessionTemplate sqlSessionTemplateEMMA(SqlSessionFactory sqlSessionFactoryEMMA) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryEMMA);
    }
    
    @Bean(name="txManagerEMMA")
    @Autowired
    public DataSourceTransactionManager txManagerEMMA(@Qualifier("dataSourceEMMA") DataSource datasource) {
        DataSourceTransactionManager txm = new DataSourceTransactionManager(datasource);
        return txm;
    }

}