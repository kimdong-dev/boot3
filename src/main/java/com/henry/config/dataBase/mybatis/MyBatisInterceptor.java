package com.henry.config.dataBase.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

@Intercepts({
	@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })
	, @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class }) })
public class MyBatisInterceptor implements Interceptor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * DB 관련 처리전에 공통적으로 처리해야 할것들
	 * 외부에서 별도 호출을 금지합니다.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Object intercept(final Invocation invocation) throws Exception {
		// 환경 파악
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		if (isDuplicated(stackTrace)) { // when mapper.xml file reloaded by jRebel, plugIn called duplicate by mapper class
			return invocation.proceed();
		}

		// 넘겨받은 값들 정의
		Object[] invocationArgs = invocation.getArgs();

		Map<String, Object> mParam = null;
		MappedStatement mappedStatement = (MappedStatement)invocationArgs[0];
		BoundSql boundSql = mappedStatement.getBoundSql(invocationArgs[1]);

		try {
			if (invocationArgs[1] == null) {
				mParam = new HashMap<String, Object>();
			}
			if (invocationArgs[1] instanceof String) {
			}

			if (invocationArgs[1] instanceof Integer) {
			}

			if (invocationArgs[1] instanceof Map) {
				mParam = (Map<String, Object>)invocationArgs[1];
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		long startTime = System.currentTimeMillis();
		Object objResultData = invocation.proceed();
		long endTime = System.currentTimeMillis();


		String sqlEndTime      = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()); // SQL완료된 시간:YYYY/MM/DD HH:MI:SS MSC
		String elapsedTime     = (endTime-startTime)+"msec"; // 소요시간단위=ms
		String userId          = getSessionKey(mParam, "ses_userId"); // 로그인아이디
		String sessionKey      = getSessionKey(mParam, "all"); // SessionKey
		String moduleName      = getCallObjectInfo(mappedStatement.getId(), "package") + ".xml"; // :서비스패키지명
		String sqlId           = getCallObjectInfo(mappedStatement.getId(), "queryId"); // 쿼리명
		String sQuery          = boundSql.getSql(); // 실행쿼리

		String resultRowCount  = ""; // 결과레코드수
		if( objResultData instanceof Integer ) {
			resultRowCount  = String.valueOf(objResultData);
		} if( objResultData instanceof ArrayList) {
			resultRowCount  = String.valueOf(((ArrayList)objResultData).size());
		}

		//String inputParameters = getQueryParameter(boundSql.getParameterObject()); // 입력파라메터

		if( !isIgnoreQuery(sQuery) ) {

			// 로그를 남깁시다.
			sqlLogging(mappedStatement, invocationArgs[1]);

			if (invocationArgs[1] instanceof Map) {
				((Map<String, Object>)invocationArgs[1]).remove("user");
			}
		}

		return objResultData;
	}

	/**
	 * 중복발생건인지 확인.
	 * @param stackTrace
	 * @return intercepCount
	 */
	private boolean isDuplicated(StackTraceElement[] stackTrace) {
		int intercepCount = 0;
		String interceptorClassName = this.getClass().getName();
		for (StackTraceElement e : stackTrace) {
			if (e.getClassName().equals(interceptorClassName)) {
				intercepCount++;
			}
		}
		return intercepCount != 1;
	}

	/**
	 * 실제 LOG를 남기는 부분
	 * @param mappedStatement
	 * @param parameters
	 */
	private void sqlLogging(MappedStatement mappedStatement, Object parameters) {
		BoundSql boundSql = mappedStatement.getBoundSql(parameters);
		SqlLogging sqlLogging = new SqlLogging(boundSql.getSql(), boundSql.getParameterMappings(), parameters);
		sqlLogging.logging(boundSql.getParameterMappings());
	}

	/**
	 * Plugin으로 지정
	 * @param target
	 * @return Plugin
	 */
	public Object plugin(final Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * @deprecated 작업내용 없음
	 * @param properties
	 */
	@Deprecated
	public void setProperties(final Properties properties) {
	}

	/**
	 * Session 정보 리턴
	 * @param parameterObject
	 * @return sessionKey
	 */
	private String getSessionKey(Map<String, Object> paramMap, String type) {
		String sessionKey = "";

		try {
			Set<String> keyset = paramMap.keySet();
			Iterator<String> iKey = keyset.iterator();

			while(iKey.hasNext()) {
				String sKey = iKey.next();
				if(sKey.indexOf("ses_") != -1) { // 세션 정보인 경우
						if( type.equals("all") ) {
							if(sessionKey.length() == 0) {
								sessionKey = "" + sKey + ":" + paramMap.get(sKey);
							} else {
								sessionKey += "," + sKey + ":" + paramMap.get(sKey);
							}
						} else {
							sessionKey = String.valueOf(paramMap.get(type));
						}
				}
			}
		} catch (NullPointerException ne) {
			// ne.printStackTrace();
		}
		return sessionKey;
	}

	/**
	 * parameter 정보 리턴(세션 정보, 브라우져, 쿼리 컬럼정보 제외)
	 * @param parameterObject
	 * @return sParameters
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private String getQueryParameter(Object parameterObject) {
		String sParameters = "";

		try {
			Class<? extends Object> clazz = parameterObject.getClass();
			String ClassName = clazz.getName();

			if( parameterObject instanceof HashMap ) { // parameter가 Map
				HashMap<String, String> paramMap = (HashMap<String, String>)parameterObject;

				Set<String> keyset = paramMap.keySet();
				Iterator<String> iKey = keyset.iterator();

				while(iKey.hasNext()) {
					String sKey = iKey.next();
					if( sKey.indexOf("ses_") == -1 && !sKey.equals("USER-AGENT") && !sKey.equals("COLUMNIDS") ) {
						if(sParameters.length() == 0) {
							sParameters = "" + sKey + ":" + paramMap.get(sKey);
						} else {
							sParameters += "," + sKey + ":" + paramMap.get(sKey);
						}
					}
				}
			} else if( ClassName.indexOf("dto") > 0 || ClassName.indexOf("entity")  > 0 ) { // parameter가 dto, entity
				Method[] methods = clazz.getMethods();
				for(Method m : methods) {
					String methodName = m.getName();
					if(methodName.indexOf("get") != -1 ) {
						if(methodName.startsWith("get") && !methodName.equalsIgnoreCase("getClass") ) {
							Object value = m.invoke(parameterObject);

							if("".equals(value.toString()) || "0".equals(value.toString()) || "null".equals(value.toString()) ) continue;

							if(sParameters.length() == 0) {
								sParameters = "Class : " + ClassName + ", " + methodName.replaceFirst("get", "") + ":" + value.toString();
							} else {
								sParameters += "," + methodName.replaceFirst("get", "") + ":" + value.toString();
							}
						}
					}
				}
			} else {
				sParameters = String.valueOf(parameterObject);
			}
		} catch (Exception e) {
		}

		return sParameters;
	}

	/**
	 * 호출한 객체의 Package정보와 실행 쿼리 아이디 리턴
	 * @param objectInfo
	 * @param type
	 * @return sReturnData(package, query id)
	 * @author swi
	 */
	 private String getCallObjectInfo(String objectInfo, String type) {
		String sReturnData = "";
		if( type.equals("package")) {
			sReturnData = objectInfo.substring(0,objectInfo.lastIndexOf("."));
		} else if( type.equals("queryId")) {
			sReturnData = objectInfo.substring(objectInfo.lastIndexOf(".")+1);
		}
		return sReturnData;
	}

	/**
	 * 로그를 남기지 않는 쿼리인지 체크
	 * @param sQuery : 실행 쿼리
	 */
	private boolean isIgnoreQuery(String sQuery) {
		boolean isIgnore = true;

		// 로그를 남기지 않는 쿼리 체크
		if( sQuery.toLowerCase().indexOf("getcodecombo") < 0 &&
		    sQuery.toLowerCase().indexOf("getcodelist") < 0) {
			isIgnore = false;
		}

		return isIgnore;
	}

}