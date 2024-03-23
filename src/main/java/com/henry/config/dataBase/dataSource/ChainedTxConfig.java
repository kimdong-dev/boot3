package com.henry.config.dataBase.dataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 각 트랜잭션 객체들를 하나로 묶은 트랜잭션 생성 
 * 각 서비스에선 chainedTxManager 를 사용할 것
 * ex) @Transactional(value="chainedTxManager", propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
 * @author 김동진
 */
@Configuration
public class ChainedTxConfig {

    @Bean(name="chainedTxManager")
    @Primary
    public PlatformTransactionManager transactionManager(
    		@Qualifier("txManagerHS") PlatformTransactionManager txManagerHS
    		, @Qualifier("txManagerEMMA") PlatformTransactionManager txManagerEMMA
    ) {
        return new ChainedTransactionManager(txManagerHS, txManagerEMMA);
    }

}