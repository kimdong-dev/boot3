package com.henry.config.dataBase.mybatis;

import com.henry.util.StringUtil;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.ParameterMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * DB관련 로그 찍어줌
 */
public class SqlLogging {
	private static final String FOREACH_KEY = "__frch_";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, Object> parameter = null;
	private String sql;
	private final List<String> paramList;
	private String property;

	/**
	 * 로그 생성
	 * @param _sql
	 * @param parameterMappings
	 * @param _parameter
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SqlLogging(String _sql, List<ParameterMapping> parameterMappings, Object _parameter) {
		this.paramList = new ArrayList<String>();
		this.sql = _sql;
		if (_parameter instanceof Map) {
			this.parameter = (Map<String, Object>)_parameter;
		} else if (_parameter instanceof String || _parameter instanceof Integer) {
			parameter = new HashMap();
			for (ParameterMapping parameterMapping : parameterMappings) {
				parameter.put(parameterMapping.getProperty(), _parameter);
			}
		} else {
			this.parameter = new HashMap<String, Object>();
		}
	}

	/**
	 * 실제로 로그를 찍습니다.
	 * @param parameterMappings
	 */
	@SuppressWarnings({"rawtypes"})
	public void logging(List<ParameterMapping> parameterMappings) {
		for (ParameterMapping parameterMapping : parameterMappings) {
			this.property = parameterMapping.getProperty();

			Object parameterValue = getParameterValue();
			if (parameterValue instanceof Map) {
				addMapToParamList((Map)parameterValue);
			} else if (parameterValue instanceof String) {
				addStringToParamList((String)parameterValue);
			} else if (parameterValue instanceof List) {
				addListToParamList((List)parameterValue);
			} else if (parameterValue == null) {
				addNullToParamListNull();
			} else {
				addStringToParamList(parameterValue.toString());
				logger.debug(String.format("Parameter Value is not String. property: %s, type: %s", property, parameterValue.getClass().getName()));
			}
		}

		sql = StringUtil.replace(sql, "?", "'%s'");
		for (String param : paramList) {
			if (param == null) {
				sql = StringUtils.replaceOnce(sql, "'%s'", "null");
			} else {
				sql = StringUtils.replaceOnce(sql, "%s", param);
			}
		}

		sql = trimSpace(sql);
		logger.debug(System.getProperty("line.separator") + sql);
	}

	@SuppressWarnings("rawtypes")
	private void addListToParamList(List parameterValue) {
		int listIndex = getListIndex();
		listIndex = listIndex % parameterValue.size();
		if (parameterValue.size() > listIndex) {
			Object element = parameterValue.get(listIndex);
			if (element instanceof LinkedHashMap) {
				paramList.add((String)((LinkedHashMap)element).get(getProvertyAfterDot()));
			} else if (element instanceof String) {
				paramList.add((String)element);
			} else { // Bean
				paramList.add((String)new BeanMap(element).get(getProvertyAfterDot()));
			}
		}
	}

	private int getListIndex() {
		String listItemKey = property;
		if (property.contains(".")) {
			listItemKey = getPropertyBeforeDot();
		}
		String listIndexString = listItemKey.substring(listItemKey.lastIndexOf("_") + 1);
		return Integer.parseInt(listIndexString);
	}

	private void addNullToParamListNull() {
		logger.warn(String.format("VALUE DOES NOT EXIST. KEY: %s", property));
		paramList.add(null);
	}

	private Object getParameterValue() {
		if (property.contains(FOREACH_KEY)) {
			return parameter.get(getListTypeKey());
		} else if (property.contains(".")) {
			return new BeanMap(parameter.get(getPropertyBeforeDot()));
		}
		return parameter.get(property);
	}

	@SuppressWarnings("rawtypes")
	private void addMapToParamList(Map parameterValue) {
		paramList.add((String)parameterValue.get(getProvertyAfterDot()));
	}

	private String getPropertyBeforeDot() {
		return property.substring(0, property.indexOf("."));
	}

	private String getProvertyAfterDot() {
		return property.substring(property.indexOf(".") + 1);
	}

	private void addStringToParamList(String parameterValue) {
		paramList.add(parameterValue);
	}

	private String trimSpace(String query) {
		String sqlQuery;
		sqlQuery = query.replaceAll("\n\\s{3,}\n", "\n");
		sqlQuery = sqlQuery.replaceAll("\n\\s{3,},\\s*\n", ",\n");
		sqlQuery = sqlQuery.replaceAll("    ", "\t");
		sqlQuery = sqlQuery.replaceAll("\n\t\t", "\n");
		sqlQuery = sqlQuery.replaceAll("^", "\t");
		sqlQuery = sqlQuery.replaceAll("\n", "\n\t");
		return sqlQuery;
	}

	private String getListTypeKey() {
		Set<String> keySet = parameter.keySet();
		List<String> listTypeKeys = new ArrayList<String>();
		for (String key : keySet) {
			if (key.contains("param")) {
				continue;
			}
			if (parameter.get(key) instanceof List) {
				listTypeKeys.add(key);
			}
		}

		if (listTypeKeys.size() == 1) {
			return listTypeKeys.get(0);
		}

		for (String listTypeKey : listTypeKeys) {
			if (listTypeKey.toUpperCase().contains(property.toUpperCase())) {
				return listTypeKey;
			}
		}

		logger.error("listTypeKeys: " + listTypeKeys);
		logger.error("property: " + property);
		if (listTypeKeys.size() == 0) {
			throw new IllegalArgumentException("Doesn't have list element in parameter");
		}

		throw new IllegalArgumentException("MyBatis Mapper has more than 2 foreach element and doesnt match container and element id. Do not use 'param' as foreach collection key");
	}
}