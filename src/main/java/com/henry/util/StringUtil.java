package com.henry.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 문자열 관련 함수
 * @author 김동진
 */
@SuppressWarnings("deprecation")
public class StringUtil extends StringUtils {

	private static SHA sha = new SHA();
	/**
	 * Object를 JSONString으로 변환
	 * Convert Object to JSONString
	 * @param jsonBean
	 * @return String
	 * @throws Exception
	 */
	public static String getJsonString(Object jsonBean) throws Exception {
		String jsonString = null;
		ObjectMapper objMapper = new ObjectMapper();
		jsonString = objMapper.writeValueAsString(jsonBean);
		return jsonString;
	}

	/**
	 * Array를 JSON으로 변환
	 * @param sResult
	 * @return String
	 * @throws ExceptionRR
	 */
	public static String arrayToJSON(String[] sResult) throws Exception {
		String retStr = "";

		retStr = "{\"code\":\"" + sResult[0] + "\", \"message\":\"" + sResult[1] + "\"}";

		return retStr;
	}

	/**
	 * JSONString을 읽어 객체로 반환
	 * Convert JSONString to Object
	 * @param jsonStr
	 * @param objType
	 * @return <T>
	 * @throws Exception
	 */
	public static <T> T readJsonObject(String jsonStr, Class<T> objType) throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		T t = objMapper.readValue(jsonStr, objType);
		return t;
	}

	/**
	 * str에서 sub의 반복 횟수를 반환 합니다.<br>
	 * return matching sub string count
	 * @param str  확인할 문자열 null이 들어 올 수도 있습니다.
	 * source string
	 * @param sub  count 할 부분 문자열 null이 들어 올 수도 있습니다.
	 * target String
	 * @return int
	 */
	public static int countMatches(String str, String sub) {
		return StringUtils.countMatches(str, sub);
	}

	/**
	* 문자열이 비었거나 null인지 확인 합니다.
	* NOTE: white space에 대한 검사는 하지 않습니다.<br>
	* return true when String is null or empty<br>
	* @param str  확인할 문자열 null일 수 있습니다.
	* @return 문자열이 null이거나 빈문자열 일 경우 {@code true}를 반환 합니다.
	*/
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	/**
	 * 문자열이 비었거나 null인지 확인 합니다.<br>
	 * return false when String is null or empty
	 * @param str  확인할 문자열 null일 수 있습니다.
	 * @return 문자열이 null 또는 공백이 아닐 경우 {@code true}를 반환 합니다.
	 */
	public static boolean isNotEmpty(String str) {
		return StringUtils.isNotEmpty(str);
	}

	/**
	 * String Array를 한 문자열로 합침니다.
	 * 처음과 끝은 separator가 추가 되지 않습니다.
	 * separator null 인 경우 빈문자열과 같이 취급 됩니다.<br>
	 *
	 * concat string array
	 * Beginning and end of the String will not be added separator.
	 *  separator null will be treated as empty string.
	 * @param array  한 문자열로 합쳐질 배열
	 * source
	 * @param separator 구분자, null일 경우 빈문자열로 취급
	 * seprator
	 * @return String
	 */
	public static String combinationArr(String[] array, String separator) {
		return StringUtils.join(array, separator);
	}

	/**
	* 왼쪽에 문자열 길이가 파라미터의 길이가 될때 까지 repChar를 채웁니다.<br>
	* add left with given character and length
	* @param str  패딩을 할 대상 문자열
	* source
	* @param length  문자열 길이
	* length
	* @param repChar  채울 물자열
	* fill character
	* @return String
	*/
	public static String lpad(String str, int length, String repChar) {
		return StringUtils.leftPad(str, length, repChar);
	}
	
	/**
     * L pad.
     *
     * @param str the str
     * @param len the len
     * @param pad the pad
     * @return the string
     */
    public static String lPad(String str, int len, char pad) {
        return lPad(str, len, pad, false);
    }
    
    /**
     * L pad.
     *
     * @param str the str
     * @param len the len
     * @param pad the pad
     * @param isTrim the is trim
     * @return the string
     */
    public static String lPad(String str, int len, char pad, boolean isTrim) {
        if (isNull(str)) {
            return null;
        }
        if (isTrim) {
            str = str.trim();
        }
        for (int i = str.length(); i < len; i++) {
            str = pad + str;
        }
        return str;
    }

	/**
	 * 오른쪽에 문자열 길이가 length가 될때 까지 repChar를 채웁니다.<br>
	 * add right with given character and length
	 * @param str  패딩을 할 대상 문자열
	 * source
	 * @param length  문자열 길이
	 * length
	 * @param repChar  채울 물자열
	 * fill character
	 * @return String
	 */
	public static String rpad(String str, int length, String repChar) {
		return StringUtils.rightPad(str, length, repChar);
	}

	/**
	 * str을 separatorChars 문자열로 잘라 문자열 배열을 만듭니다.<br>
	 * str to string array with separator
	 * @param str  자를 문자열
	 * @param separatorChars  구분자
	 * @return String[]
	 */
	public static String[] strToArray(String str, String separatorChars) {
		return StringUtils.split(str, separatorChars);
	}

	/**
	 * compare String
	 * @param foo
	 * @param bar
	 * @return when same StringValue then reutrn true else false
	 */
	public static boolean equals(String foo, String bar) {
		return StringUtils.equals(foo, bar);
	}

	/**
	 * compare String
	 * @param foo
	 * @param bar
	 * @return when not same StringValue then reutrn true else false
	 */
	public static boolean notEquals(String foo, String bar) {
		return !StringUtils.equals(foo, bar);
	}

	/**
	 * String foo의 값이 "true" 이면 fos "false" 이면 neg 둘다 아니면 foo를 반환<br>
	 * return fos's value when foo's value is "true"
	 * return neg's value when foo's value is "false"
	 * else return foo's value
	 * @param foo String
	 * @param pos String
	 * @param neg String
	 * @return String
	 * @throws Exception
	 */
	public static String repToStr(String foo, String pos, String neg) {
		if (StringUtils.isEmpty(foo) || StringUtils.equals(foo, "null")) {
			return "";
		}
		if (StringUtils.equals(foo, "true")) {
			return pos;
		}
		if (StringUtils.equals(foo, "false")) {
			return neg;
		}
		return foo;
	}

	/**
	 * 무작위 문자열을 randomStringLength 만큼 type으로 생성<br>
	 * create random string
	 * @param randomStringLength int 무작위 문자열을 만들 길이
	 * string length
	 * @param type String N: 숫자만, S: 문자열만, NS: 문자,숫자 혼합
	 * N: only number, S:  only character, NS: number or character
	 * @return String
	 */
	public static String getRandomString(int randomStringLength, String type) {
		if ("N".equals(type)) {
			return RandomStringUtils.randomNumeric(randomStringLength);
		}
		if ("S".equals(type)) {
			return RandomStringUtils.randomAlphabetic(randomStringLength);
		}
		if ("NS".equals(type)) {
			return RandomStringUtils.randomAlphanumeric(randomStringLength);
		}
		return null;
	}

	/**
	 * 문자열 중에 del문자열 제거<br>
	 * remove del in str
	 * @param str String
	 * @param del String
	 * @return String
	 */
	public static String ignoreSeparator(String str, String del) {
		return StringUtils.replace(str, del, "");
	}

	/**
	 * exception 정보를 String 형태로 return<br>
	 * return exception infomation as String
	 * @param throwable Throwable
	 * @return String
	 */
	public static String getStackTrace(Throwable throwable) {
		return ExceptionUtils.getStackTrace(throwable);
	}

	/**
	 * USE ESAPI
	 * @param str
	 * @return String
	 */
	public static String CheckInjection(String str) {
		return  replaceJavascriptInjectionString(str);
	}
	/**
	 * 문자 코드
	 * org.owasp.esapi.codecs.HTMLEntityCodec 에서 추출해서 사용
	 * http://owasp-esapi-java.googlecode.com/svn/trunk/src/main/java/org/owasp/esapi/codecs/HTMLEntityCodec.java
	 * @param str
	 * @return String
	 */
	public static String replaceJavascriptInjectionString(String str){
		Map<Character, String> map = new HashMap<Character,String>(252);
//        map.put((char)34,       "＂");        /* quotation mark */
//        map.put((char)39,       "＇");        /* single quotation mark */
        map.put((char)60,       "＜");        /* less-than sign */
        map.put((char)62,       "＞");        /* greater-than sign */
        if(str == null) {
			return null;
		}
        for (Character ch : map.keySet()) {
        	str = str.replaceAll(ch.toString(), map.get(ch));
		}
		return str;
	}

	/**
	 * 문자열을 byte - UTF-8 로 변환후, 다시 UTF-8로 String 화. ( ? )
	 * @param str
	 * @return String
	 */
	public static String toEmpty(String str) {

		String rtnVal = "";
		byte[] byteRes = null;

		if (str == null) {
			rtnVal = "";
		} else {
			try {
				//rtnVal = URLDecoder.decode(str, "UTF-8");
				byteRes = str.getBytes("UTF-8");
				rtnVal = new String(byteRes, "UTF-8");
			} catch (UnsupportedEncodingException unsupportedencodingexception) {
				unsupportedencodingexception.printStackTrace();
			}
		}
		return rtnVal;
	}

	/**
	 * 널 문자열이 들어오면 빈 문자열을 return
	 * 아닐 경우는 입력 값을 return<br>
	 * if input is  null then return empty String
	 * else return input
	 * @param str
	 * @return String
	 */
	public static String nullToEmptyString(String str) {
		if (isEmpty(str)) {
			return "";
		}
		return str;
	}

	/**
	 * Obj를 String으로 강제 변환시켜서 null이면 빈 문자열 반환
	 * @param obj
	 * @return String
	 */
	public static String nullToEmptyString(Object obj) {
		if (isEmpty((String)obj)) {
			return "";
		}
		return (String)obj;
	}

	/**
	 * 널 또는 빈 문자열의 경우 디폴트 스트링을 반환
	 * @param target
	 * @param defaultString
	 * @return String
	 */
	public static String defaultIfEmpty(String target, String defaultString) {
		return StringUtils.defaultIfEmpty(target, defaultString);
	}

	/**
	 * java의 String.replace와 같은 기능.
	 * @param text
	 * @param searchString
	 * @param replacement
	 * @return String
	 */
	public static String replace(String text, String searchString, String replacement) {
		return StringUtils.replace(text, searchString, replacement);
	}

	/**
	 * Java의 String.replaceAll과 같은 기능
	 * @param text
	 * @param searchString
	 * @param replacement
	 * @return String
	 */
	public static String replaceAll(String text, String searchString, String replacement) {
		String returnText = "";
		returnText = text;
		for (int i = 0; i < returnText.length(); i++) {
			if (String.valueOf(returnText.charAt(i)).equals(searchString)) {
				returnText = returnText.replace(searchString, replacement);
			}
		}
		return returnText;
	}

	/**
	 * 2차원 문자열 배열에서 해당 문자열의 인덱스를 반환<br>
	 * return searchString index in Dimension String array
	 * from enpm framework getIndex
	 * difference: 첫번째 인덱스를 넘기고 두번째 인덱스만 리턴 받음
	 * @param strComplexArray
	 * @param searchString
	 * @return String
	 */
	public static int[] getIndexFromDimensionStringArray(String[][] strComplexArray, String searchString) {
		for (int i = 0; i < strComplexArray.length; i++) {
			for (int j = 0; j < strComplexArray.length; j++) {
				if (strComplexArray[i][j].equals(searchString)) {
					return new int[] {i, j};
				}
			}
		}
		return new int[] {-1, -1};
	}

	/**
	 * convert \n to &lt;br/&gt;
	 * @param str
	 * @return String
	 */
	public static String nToBr(String str) {
		return StringUtils.replace(str, "\n", "<br/>");
	}

	/**
	 * convert &lt;br/&gt; to \n
	 * @param str
	 * @return String
	 */
	public static String brToN(String str) {
		return StringUtils.replace(str, "<br/>", "\n");
	}

	/**
	 * 문자열 내에 delimeter 로 구분된 항목 들을 sql in 절에 맞는 형태로 변형(for in query)<br>
	 * convert string with delimeter as Sql IN Statement
	 * @param str
	 * @param delimeter
	 * @return String
	 */
	public static String forInQuery(String str, String delimeter) {
		if( str == null || "".equals(str) )	return "";
		String[] split = StringUtils.split(str, delimeter);
		String join = StringUtils.join(split, "', '");
		return String.format("('%s')", join);
	}

	/**
	 * Url encoding
	 * @param str
	 * @return String
	 */
	public static String encodeUrl(String str) {
		/* @formatter:off */
		String[][] replaceMapArray = { {"%", "%25"}, {"#", "%23"}, {"&", "%26"}, {"'", "%27"}, {"+", "%2B"}, {":", "%3A"}, {";", "%3B"}, {"=", "%3D"}, {"\"", "%22"}, {" ", "+"}};
		/* @formatter:on */

		StrBuilder strBuilder = new StrBuilder(str);
		for (String[] replaceMap : replaceMapArray) {
			strBuilder.replaceAll(replaceMap[0], replaceMap[1]);
		}
		return strBuilder.toString();
	}

	/**
	 * 문자열 덧셈<br>
	 * Add number String
	 * @param value1
	 * @param value2
	 * @return String
	 */
	public static String add(String value1, String value2) {
		return new BigDecimal(value1).add(new BigDecimal(value2)).toString();
	}

	/**
	 * 문자열 뺄셈<br>
	 * subtract number String
	 * @param value1
	 * @param value2
	 * @return String
	 */
	public static String subtract(String value1, String value2) {
		return new BigDecimal(value1).subtract(new BigDecimal(value2)).toString();
	}

	/**
	 * 문자열 곱셈<br>
	 * multiply number String
	 * @param value1
	 * @param value2
	 * @return String
	 */
	public static String multiply(String value1, String value2) {
		return new BigDecimal(value1).multiply(new BigDecimal(value2)).toString();
	}

	/**
	 * 문자열 나눗셈 rounding 모드와 자릿수 지정<br>
	 * divide number String with rounding mode and digits
	 * @param value1
	 * @param value2
	 * @param scale
	 * @param roundMode
	 * @return String
	 */
	public static String divide(String value1, String value2, int scale, int roundMode) {
		return new BigDecimal(value1).divide(new BigDecimal(value2), scale, roundMode).toString();
	}

	/**
	 * 문자열 나눗셈 반올림 할 자리수 지정<br>
	 *  divide number String with rounding digits
	 * @param value1
	 * @param value2
	 * @param scale
	 * @return String
	 */
	public static String divide(String value1, String value2, int scale) {
		return divide(value1, value2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 문자열 나눗셈 소숫점 둘째 자리 까지<br>
	 * divide number Up to two decimal places
	 * @param value1
	 * @param value2
	 * @return String
	 */
	public static String divide(String value1, String value2) {
		return divide(value1, value2, 2);
	}

	/**
	 * 문자열 나머지(mod, %)<br>
	 * mod number String
	 * @param value1
	 * @param value2
	 * @return String
	 */
	public static String remainder(String value1, String value2) {
		return new BigDecimal(value1).remainder(new BigDecimal(value2)).toString();
	}

	/**
	 * String을 지정 길이 단위로 잘라서 배열로 반환
	 * @param data
	 * @param length
	 * @return ArrayList
	 * @throws Exception
	 */
	public static ArrayList<String> chopSplitString(String data, int length) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		String ui = data;
		int dataLength = getLengthb(ui);

		while (dataLength > 0) {
			list.add(getSubString(ui, 0, length));
			ui = getSubString(ui, length, dataLength);
			dataLength = getLengthb(ui);
		}

		return list;
	}

	/**
	 * UTF8로 인코딩 된 데이터를 byte 단위로 substring 한다.
	 * @param str
	 * @param start
	 * @param end
	 * @return String
	 */
	public static String getSubString(String str, int start, int end) {
		int rSize = 0;
		int len = 0;

		StringBuffer sb = new StringBuffer();

		for (; rSize < str.length(); rSize++) {
			if (str.charAt(rSize) > 0x007F) {
				len += 2;
			} else if (str.charAt(rSize) > 0x07FF) {
				len += 3;
			} else {
				len++;
			}

			if ((len > start) && (len <= end)) {
				sb.append(str.charAt(rSize));
			}
		}

		return sb.toString();
	}

	/**
	 * 문자열의 바이트수를 계산한다.
	 * @param str
	 * @return int
	 */
	public static int getLengthb(String str) {
		int rSize = 0;
		int len = 0;

		if ( str == null ) {
			return 0;
		}

		for (; rSize < str.length(); rSize++) {
			if (str.charAt(rSize) > 0x007F) {
				len += 2;
			} else if (str.charAt(rSize) > 0x07FF) {
				len += 3;
			} else {
				len++;
			}
		}

		return len;
	}

	/**
	 * Client에서 넘어온 JSON String을 List 형식으로 변환한다.
	 * @param sJsonString
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, String>> convertJSonToList(String sJsonString) {
		List<Map<String, String>> lJsondata = new ArrayList<Map<String, String>>();
		if( sJsonString == null ) {
			return lJsondata;
		}

		String sParam = nullToEmptyString(sJsonString);
		JSONArray jsonArrayObj = (JSONArray) JSONValue.parse(sParam);
		for( int idx = 0; idx < jsonArrayObj.size(); idx++ )  {
			lJsondata.add((Map) jsonArrayObj.get(idx));
		}

		return lJsondata;
	}

	/**
	 * Client에서 넘어온 JSON String을 List 형식으로 변환한다. - 리턴값의 변경
	 * @param sJsonString
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, Object>> convertJSonToListObject(String sJsonString) {
		List<Map<String, Object>> lJsondata = new ArrayList<Map<String, Object>>();
		if( sJsonString == null ) {
			return lJsondata;
		}

		String sParam = nullToEmptyString(sJsonString);
		JSONArray jsonArrayObj = (JSONArray) JSONValue.parse(sParam);
		for( int idx = 0; idx < jsonArrayObj.size(); idx++ )  {
			lJsondata.add((Map) jsonArrayObj.get(idx));
		}

		return lJsondata;
	}

	/**
	 * 암호화를 위한 Encoding
	 * @param str
	 * @return String
	 * @throws Exception
	 */
	public static String decoderString(String str) throws Exception {
		String rtn = "";

		try {
//			BASE64Decoder decoder = new BASE64Decoder();
			String decStr = new StringBuffer(str).toString();
			byte[] byteStr = Base64.decodeBase64(decStr);

			rtn = new String(byteStr, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtn;
	}

	/** null 또는 공백문자사를 처리 stripToEmpty을 대신한다.
	 * @param str
	 * @return String
	 */
	public static String nvl(String str) {
		return nvl(str, EMPTY);
	}

	/** null 또는 공백문자를 처리 stripToEmpty을 대신한다.
	 * @param obj
	 * @param escape
	 * @return String
	 */
	public static String nvlObject(Object obj, String escape) {
		if (obj == null) {
			return escape;
		}
		return nvl(obj.toString(), escape);
	}

	/** null 또는 공백문자를 처리 stripToEmpty을 대신한다. ( default 적용 )
	 * @param str
	 * @param defaultStr
	 * @return String
	 */
	public static String nvl(String str, String defaultStr) {
		return isEmpty(str) ? defaultStr : str.trim();
	}

	/*상동*/
    public static String nvl(Object obj, String ifNull)
    {
        return obj == null ? ifNull : String.valueOf(obj);
    }

	/**
	 * Integer 값이 null 이면 0 으로 반환
	 *
	 * @param integer
	 * @return Integer
	 */
	public static Integer nvl(Integer integer) {
		return (integer == null) ? 0 : integer;
	}

	/**
	 * Integer 값이 null 이면 defaultint 으로 반환
	 *
	 * @param str
	 * @param defaultint
	 * @return Integer
	 */
	public static Integer nvl(String str, Integer defaultint) {
		return isEmpty(str) ? defaultint : Integer.parseInt(str.trim());
	}

	/**
	 * null safe한 toString
	 * @param str
	 * @return String
	 */

	public static String toString(Object str) {
		if (str == null) {
			return EMPTY;
		}
		return str.toString();
	}

	/**
	 * int를 0000 붙인 문자로 반환 1, 5 => 00005.
	 * @param num
	 * @param len
	 * @return String
	 */
	public static String getZeroString(int num, int len)
    {
        return leftPad(Integer.toString(num), len, "0");
    }

	/**
	 * 오른쪽에 붙여넣기.
	 * @param padStr
	 * @param size
	 * @param padChar
	 * @return String
	 */
	public static String getRpad(String padStr, int size, String padChar)
    {
        StringBuffer str = new StringBuffer();
        int len = getLengthb(padStr);

        if (len < size)
        {
            str.append(padStr);

            for (int i = 0; i < (size - len); i++)
            {
                str.append(padChar);
            }
        }
        else
        {
            str.append(padStr);
        }

        return str.toString();
    }

	/**
	 * 오른쪽에 붙여넣기.
	 * @param padStr
	 * @param size
	 * @param padChar
	 * @return String
	 */
    public static String getRpadTrim(String padStr, int size, String padChar)
    {
        StringBuffer str = new StringBuffer();
        int len = getLengthb(padStr);

        if (len < size)
        {
            str.append(padStr);

            for (int i = 0; i < (size - len); i++)
            {
                str.append(padChar);
            }
        }
        else
        {
            str.append(getSubString(padStr, 0, size));
        }

        return str.toString();
    }

    /** EDI 전문통신 시점에 왼쪽에 '0' 문자를 삭제하는 아래의 모든 메소드 장성진 추가. 2013-08-08
     * @param original_string
     * @param remove_char
     * @return String
     */
    public static String removeStringHead(String original_string, String remove_char)
	{
		String rtn_string = "";

		if(original_string.indexOf(remove_char) == 0 && original_string.length() > 1)
		{
			rtn_string = removeStringHead(original_string.substring(1), remove_char);
		}
		else if(original_string.indexOf(remove_char) == 0 && original_string.length() == 1)
		{
			rtn_string = "";
		}
		else
		{
			rtn_string = original_string;
		}

		return rtn_string;
	}

    /**
     *
     * @param sJsonString
     * @return List
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, Object>> convertJSonToListFilter(String sJsonString) {
		List<Map<String, Object>> lJSonData = new ArrayList<Map<String, Object>>();

		if( sJsonString == null) {
			return lJSonData;
		}

		String sParam = nvl(sJsonString);
		JSONArray jsonArrayObj = (JSONArray) JSONValue.parse(sParam);
		for( Object mRow : jsonArrayObj )  {
			lJSonData.add((Map) mRow);
		}

		return lJSonData;
	}

    /**
     *
     * @param sJsonString
     * @return List
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, String>> convertJSonList(String sJsonString) {
		List<Map<String, String>> lJSonData = new ArrayList<Map<String, String>>();

		if( sJsonString == null) {
			return lJSonData;
		}

		String sParam = nvl(sJsonString);
		JSONArray jsonArrayObj = (JSONArray) JSONValue.parse(sParam);
		for( Object mRow : jsonArrayObj )  {
			lJSonData.add((Map) mRow);
		}

		return lJSonData;
	}

    /**
     * String 을 sha로 변환 ( Key ="1" )
     * @param str
     * @return String
     * @throws Exception
     */
	public static String strToSha(String str) throws Exception  {
		str = sha.getSHA(str, "1");
		return str;
	}

    /**
     * BASE64를 SHA로 변경
     * @param str
     * @return String
     * @throws Exception
     */
	public static String base64ToSha(String str) throws Exception  {
		str = decoderString(str);
		str = strToSha(str);

		return str;
	}

	/**
	 * 계행문자 삭제
	 * @param str - 소스 문자열
	 * @return 삭제된 문자열
	 */
	public static String toValidStrRemove(String str) {
    	if (str==null || str.trim().length()==0){
			return "" ;
		}
		str = str.replaceAll("'", "");
		str = str.replaceAll("\"", "");
		str = str.replaceAll("\r", "");
		str = str.replaceAll("\n", "");

		return str;
    }

	/**
	 * 온라인 주문번호 변환
	 * @author 김재욱
	 * @since 2015. 7. 2.
	 * @param ordNo
	 * @return
	 */
	public static String getONLINE_ORDRNO(String ordNo) {
		String ONLINE_ORDRNO = null;
		String yearCd = "";
		String monthCd = "";

		if(ordNo.length() == 0) {
			return null;
		}

		String year = ordNo.substring(0, 4);
		String month = ordNo.substring(4, 6);
		String restOrdNo = ordNo.substring(6);

		if(year.equals("2011")) {
			yearCd = "A";
		} else if(year.equals("2012")) {
			yearCd = "B";
		} else if(year.equals("2013")) {
			yearCd = "C";
		} else if(year.equals("2014")) {
			yearCd = "D";
		} else if(year.equals("2015")) {
			yearCd = "E";
		} else if(year.equals("2016")) {
			yearCd = "F";
		} else if(year.equals("2017")) {
			yearCd = "G";
		} else if(year.equals("2018")) {
			yearCd = "H";
		} else if(year.equals("2019")) {
			yearCd = "I";
		} else if(year.equals("2020")) {
			yearCd = "J";
		} else if(year.equals("2021")) {
			yearCd = "K";
		} else if(year.equals("2022")) {
			yearCd = "L";
		} else if(year.equals("2023")) {
			yearCd = "M";
		} else if(year.equals("2024")) {
			yearCd = "N";
		} else if(year.equals("2025")) {
			yearCd = "O";
		} else if(year.equals("2026")) {
			yearCd = "P";
		} else if(year.equals("2027")) {
			yearCd = "Q";
		} else if(year.equals("2028")) {
			yearCd = "R";
		} else if(year.equals("2029")) {
			yearCd = "S";
		} else if(year.equals("2030")) {
			yearCd = "T";
		} else if(year.equals("2031")) {
			yearCd = "U";
		} else if(year.equals("2032")) {
			yearCd = "V";
		} else if(year.equals("2033")) {
			yearCd = "W";
		} else if(year.equals("2034")) {
			yearCd = "X";
		} else if(year.equals("2035")) {
			yearCd = "Y";
		} else if(year.equals("2036")) {
			yearCd = "Z";
		} else {
			yearCd = "_";
		}

		if(month.equals("01")) {
			monthCd = "A";
		} else if(month.equals("02")) {
			monthCd = "B";
		} else if(month.equals("03")) {
			monthCd = "C";
		} else if(month.equals("04")) {
			monthCd = "D";
		} else if(month.equals("05")) {
			monthCd = "E";
		} else if(month.equals("06")) {
			monthCd = "F";
		} else if(month.equals("07")) {
			monthCd = "G";
		} else if(month.equals("08")) {
			monthCd = "H";
		} else if(month.equals("09")) {
			monthCd = "I";
		} else if(month.equals("10")) {
			monthCd = "J";
		} else if(month.equals("11")) {
			monthCd = "K";
		} else if(month.equals("12")) {
			monthCd = "L";
		} else {
			monthCd = "_";
		}

		ONLINE_ORDRNO = "M" + yearCd + monthCd + restOrdNo;

		return ONLINE_ORDRNO;
	}

	/**
	 * 가구 기준정보 관리
	 * @author 전민우
	 * @since 2015. 9. 15.
	 * @param str
	 * @param token
	 * @return
	 */
	public static String removeDuplicateStringToken(String str, String token){
		  String removeDubString="";
		  String[] array = StringUtils.split(str, token.trim());

		  TreeSet<String> ts=new TreeSet<String>();
		  for(int i=0; i<array.length; i++){
			  ts.add(array[i]);
		  }

		  Iterator<String> it=ts.iterator();

		  while(it.hasNext()){
			  removeDubString+= it.next() + token.trim();
		  }
	      removeDubString=removeDubString.substring(0, removeDubString.lastIndexOf(token.trim()));


		 return removeDubString;
	   }

	/**
	 * 설명 한글코드처리
	 * @param     String strSrcText
	 * @param strSrcText
	 * @return    String
	 * @exception
	 * @roseuid 411625BE020C
	 */
	public static String toUniCode(String strSrcText) {
		try {
			if (strSrcText != null) {
				return (new String(strSrcText.getBytes("euc-kr"),"8859_1"));
			}
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		return "";
	}

	/**
	 * 문자열을 전화번호 포맷으로 만든다.
	 * @param strTelNumber
	 * @return
	 *
	 * @author M.Park
	 * @date 2015. 9. 23.
	 */
	public static String getTelNumberFormat(String strTelNumber) {
		String strTelNumberFormated = "";

		if(strTelNumber == null) {
			return "";
		}

		if(strTelNumber.length() > 0) {
			strTelNumberFormated = strTelNumber;
		}

		if(strTelNumber.length()>8){
			strTelNumberFormated = "";

			if(strTelNumber.length()>10){
				strTelNumberFormated += strTelNumber.substring(0, 3) + "-";
				strTelNumberFormated += strTelNumber.substring(3, 7) + "-";
				strTelNumberFormated += strTelNumber.substring(7);
			} else if("02".equals(strTelNumber.substring(0, 2))){
				if(strTelNumber.length()<10 && strTelNumber.length()>=9){
					strTelNumberFormated += strTelNumber.substring(0,2) + "-";
					strTelNumberFormated += strTelNumber.substring(2,5) + "-";
					strTelNumberFormated += strTelNumber.substring(5);
				} else if(strTelNumber.length()>9){
					strTelNumberFormated += strTelNumber.substring(0,2) + "-";
					strTelNumberFormated += strTelNumber.substring(2,6) + "-";
					strTelNumberFormated += strTelNumber.substring(6);
				}
			} else if(strTelNumber.length()==10){
				strTelNumberFormated += strTelNumber.substring(0,3) + "-";
				strTelNumberFormated += strTelNumber.substring(3,6) + "-";
				strTelNumberFormated += strTelNumber.substring(6);
			}
		}
		return strTelNumberFormated ;
	}

	/**
	 * 문자열에서 HTML 태그 제거
	 * @param sSource
	 * @return
	 *
	 * @author M.Park
	 * @date 2015. 12. 1.
	 */
	public static String removeHtml(String sSource) {
		if ( sSource == null ) {
			return "";
		}

		String sTarget = sSource.replaceAll("<[^>]*>", "");

		return sTarget;

	}

	/**
	 * String 배열을 지정한 seprator로 구분하여 문자열로 만들어준다. ( String.split와 반대개념 )
	 * @param sSources
	 * @param sSeprator
	 * @return
	 *
	 * @author M.Park
	 * @date 2015. 11. 11.
	 */
	public static String arrayToString(String[] sSources, String sSeprator) {
		if ( sSources == null ) {
			return null;
		}

		String sResult = "";

		for ( String sValue : sSources ) {
			if ( sResult.length() == 0 ) {
				sResult += sValue;
			} else {
				sResult += sSeprator + sValue;
			}
		}

		return sResult;
	}

	/**
	 * String 으로 받은 날짜가 유효한지 체크
	 * @param sDate
	 * @return
	 *
	 * @author M.Park
	 * @date 2015. 12. 16.
	 */
	public static boolean isDateTime(String sDate, String sFormat) {
		if ( sFormat == null || sFormat.equals("") ) {
			sFormat = "yyyy-MM-dd";
		}

	    SimpleDateFormat sdf = new SimpleDateFormat(sFormat);

	    sdf.setLenient(false);

	    return sdf.parse(sDate, new ParsePosition(0)) != null;
	}

	/**
	 * 문자열에서 숫자를 제외한 나머지는 모두제거
	 * @param sString
	 * @return
	 *
	 * @author M.Park
	 * @date 2016. 2. 17.
	 */
	public static String removeCharacters(String sString) {
		String sReturn = "";

		try {
			sReturn = sString.replaceAll("[^\\d]", "");
		} catch (Exception e) {
			sReturn = "";
		}

		return sReturn;
	}

	/**
	 * 집/핸드폰 번호 판단하여 배열로 반환
	 * @param String
	 * @return Array || null
	 * @author frodo
	 * @date 2017. 9. 26.
	 */
	public static String[] makePhoneStrToArray(String phoneNumber) {
		String regEx = "";
		// 집/사무실
		if(isHomePhoneNo(phoneNumber)){
			// 서울이면 지역번호 2자리, 아니면 3자리
			regEx = isSeoulNo(phoneNumber) ? "(\\d{2})(\\d{3,4})(\\d{4})" : "(\\d{3})(\\d{3,4})(\\d{4})";

		// 핸드폰
		}else if(isMobilePhoneNo(phoneNumber)){
			regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
		}

		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(phoneNumber);

		if(!matcher.matches()) return null;

		return new String[]{matcher.group(1), matcher.group(2), matcher.group(3)};

	}
	/**
	 * 집/사무실 전화번호 검증
	 * @param String
	 * @return boolean
	 * @author frodo
	 * @date 2017. 9. 26.
	 */
	public static boolean isHomePhoneNo(String newPhoneNo) {
	    String regex = "^(02|0[3-9]{1}[0-9]{1})(\\d{4}|\\d{3})\\d{4}$";
	    return newPhoneNo.matches(regex);
	}
	/**
	 * 지역번호(서울) 검증
	 * @param String
	 * @return boolean
	 * @author frodo
	 * @date 2017. 9. 26.
	 */
	public static boolean isSeoulNo(String newPhoneNo) {
	    String regex = "^(02)(\\d{4}|\\d{3})\\d{4}$";
	    return newPhoneNo.matches(regex);
	}
	/**
	 * 휴대폰 번호 검증
	 * @param String
	 * @return boolean
	 * @author frodo
	 * @date 2017. 9. 26.
	 */
	public static boolean isMobilePhoneNo(String newPhoneNo) {
	    String regex = "^(01[0|1|6|7|8|9])(\\d{4}|\\d{3})\\d{4}$";
	    return newPhoneNo.matches(regex);
	}

	/**
	 * 이름 마스킹처리
	 * @param sString
	 * @return
	 *
	 * @author 김동진
	 * @date 2017. 11. 17.
	 */
	public static String setMaskingUserName(String sString) {
		if(isBlank(sString)) return sString;

		try {
			if(sString.length() >= 2) {
				return sString.substring(0, 1) + "*" + sString.substring(2, sString.length());
			} else {
				return sString;
			}
		} catch (Exception e) {
			return sString;
		}
	}

	/**
	 * 전화번호 마스킹처리
	 * @param sString
	 * @return
	 *
	 * @author 김동진
	 * @date 2017. 11. 17.
	 */
	public static String setMaskingTelNumber(String sString) {
		if(isBlank(sString)) return sString;

		try {
			if( ! sString.contains("-") ) { // 하이푼이 없는경우
				if(sString.length() >= 3) {
					return sString.substring(0, 3) + "****" + sString.substring(7, sString.length());
				}
			} else { // 하이푼이 있는경우
				if(StringUtils.countMatches(sString, "-") == 1) { // 하이푼이 1개
					return sString.substring(0, sString.indexOf("-")+1) + "****" + sString.substring(sString.indexOf("-")+5, sString.length());
				} else { // 하이푼이 2개 이상
					int maskCnt = sString.lastIndexOf("-") -sString.indexOf("-");
					String maskStr = "";
					for(int i=1; i<maskCnt; i++) {
						maskStr += "*";
					}
					return sString.substring(0, sString.indexOf("-")+1) + maskStr + sString.substring(sString.indexOf("-", sString.indexOf("-")+1), sString.length());
				}
			}
		} catch (Exception e) {
			return sString;
		}
		return sString;
	}

	/**
	 * 주소(FullNameAddr) 마스킹처리
	 * ex) 서울 서초구 방배동 한샘빌딩 > 서울 서초구 방배동 ****
	 * ex) [137060] 서울 서초구 방배동 한샘빌딩 > [137060] 서울 서초구 방배동 ****
	 * 	n번째 space 이후문자열을 다 마스킹처리
	 * @param sString
	 * @return
	 *
	 * @author 김동진
	 * @date 2017. 11. 17.
	 */
	public static String setMaskingFullNameAddr(String sString, int nth) {
		if(isBlank(sString)) return sString;

		try {
			String tmpArr[] = sString.split(" ");
			if(tmpArr != null && tmpArr.length > nth) {
				int maskStartIndex = getIndexOf_n(sString, " ", nth);
				String maskStr = "";
				for(int i=maskStartIndex+(nth-1); i<=sString.length(); i++) {
					maskStr += "*";
				}
				return sString.substring(0, maskStartIndex) + " " + maskStr;
			}
		} catch (Exception e) {
			return sString;
		}
		return sString;
	}

	/**
	 * 특정 문자열의 n번째 위치(index) 가져오기
	 * @param sString, searchStr, nth
	 * @return
	 *
	 * @author 김동진
	 * @date 2017. 11. 17.
	 */
	public static int getIndexOf_n(String sString, String searchStr, int nth) {
		int times = 0;
		int num = 0;
		while(times < nth && num != -1) {
			num = sString.indexOf(searchStr, num+1);
			times++;
		}
		return num;
	}

	// 날짜체크 date:날짜, format:포멧형식(yyyy, yyyyMM, yyyyMMdd 등등...)
    public static boolean dateCheck( String date, String format ){
        SimpleDateFormat dateFormatParser = new SimpleDateFormat(format, Locale.KOREA);
        dateFormatParser.setLenient(false);
        try{
            dateFormatParser.parse(date);
            return true;
        }
        catch( Exception Ex ){
            return false;
        }
    }

    // e.printStackTrace()결과를 String 객체로 변환하기
    public static String stackTraceToString( Throwable e ){
	    StringBuilder sb = new StringBuilder();
	    sb.append(e.toString());
	    sb.append("\n");
	    for( StackTraceElement element : e.getStackTrace() ){
	        sb.append(element.toString());
	        sb.append("\n");
	    }
	    return sb.toString();
	}

    // 할인율(%)
	public static String getPercentage( int pre, int now ){
		double dPre = 0d;
		double dNow = 0d;
		if( pre == 0 )	dPre = 1d;
		else			dPre = (double)pre;

		if( now == 0 )	dNow = 1d;
		else			dNow = (double)now;

		double result = 0d;
		if( pre != 0 && now != 0 ){
	       result = ((dNow - dPre) / dNow) * 100d;
		}
		DecimalFormat decimalFormat = new DecimalFormat("##0.0");
		String percent = decimalFormat.format(result);

		return percent;
	}

	/**
	 * 바이트를 체크한다. 기준보다 크면 false, 작거나 같으면 true
	 * @param txt 체크할 text
	 * @param standardByte 기준 바이트 수
	 * @return boolean
	 * @author 김동진
	 * @date 2018. 08. 09.
	 */
	public static boolean byteCheck(String txt, int standardByte) {
        if (isEmpty(txt)) { return true; }

        // 바이트 체크 (영문 1, 한글 2, 특문 1)
        int en = 0;
        int ko = 0;
        int etc = 0;

        char[] txtChar = txt.toCharArray();
        for (int j = 0; j < txtChar.length; j++) {
            if (txtChar[j] >= 'A' && txtChar[j] <= 'z') {
                en++;
            } else if (txtChar[j] >= '\uAC00' && txtChar[j] <= '\uD7A3') {
                ko++;
                ko++;
            } else {
                etc++;
            }
        }

        int txtByte = en + ko + etc;
        if (txtByte > standardByte) {
            return false;
        } else {
            return true;
        }
    }

	/**
     * Cal gds sale percent
     * 할인율 계산
     * 소수점 두자리 올림처리
     * @param normalPrc 기본가격
     * @param salePrc 할인가격
     * @return string
     */
    public static String calGdsSalePercent(int normalPrc, int salePrc) {
    	BigDecimal bNormal = new BigDecimal(normalPrc);
    	BigDecimal bSale = new BigDecimal(salePrc);
    	if(bNormal.equals(bSale)) {
    		return "0";
    	}
    	return integer2string((bNormal.subtract(bSale)).divide(bNormal, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
    }

    /**
     * Integer2string.
     * @param integer the integer
     * @return the string
     */
    public static String integer2string(int integer) {
        return ("" + integer);
    }

    /**
     * 객체가 null 이거나 객체안의 String이 null 이거나 비어있는지 확인
     * ex) isNull(paramMap.get("gdsNo"))
     * @param obj
     * @return boolean
     * @author 김동진
	 * @date 2019. 1. 22.
     */
    @SuppressWarnings({"unchecked"})
	public static <T> boolean isNull(Object obj) {
		String str = null;
		if(obj == null) return true;

		if (obj instanceof String) {
			str = (String) obj;
		} else if (obj instanceof List) {
			List<T> list = (List<T>) obj;
			if(list == null || list.size() <= 0) {
				return true;
			} else {
				str = list.toString();
			}
		} else if(obj instanceof Integer) {
			str = String.valueOf(obj);
		} else if(obj instanceof Boolean) {
			str = String.valueOf(obj);
		}
		return isBlank(str);
	}

	/**
     * 객체가 null 이 아니고 객체안의 String 값이 있는지 확인
     * ex) isNotNull(paramMap.get("gdsNo"))
     * @param obj
     * @return boolean
     * @author 김동진
	 * @date 2019. 1. 22.
     */
    @SuppressWarnings({"unchecked"})
	public static <T> boolean isNotNull(Object obj) {
		String str = null;
		if(obj == null) return false;

		if (obj instanceof String) {
			str = (String) obj;
		} else if (obj instanceof List) {
			List<T> list = (List<T>) obj;
			if(list == null || list.size() <= 0) {
				return false;
			} else {
				str = list.toString();
			}
		} else if(obj instanceof Integer) {
			str = String.valueOf(obj);
		} else if(obj instanceof Boolean) {
			str = String.valueOf(obj);
		}
		return isNotBlank(str);
	}

	/**
	 * 문자열을 잘라 List로 리턴
	 * @param strValue : 문자열
	 * @param regex : 구분자
	 * @return ArrayList<String>
	 * @author 허용
	 * @date 2019. 01. 23
	 */
	public static List<String> splitStrList (String strValue, String regex) {
		List<String> resultList = new ArrayList<String>();
		
		String str = strValue.trim();
		try {
			if(isNotEmpty(str)) {
				String [] tmpStrList = str.split(regex);
				for(String sValue : tmpStrList) {
					resultList.add(sValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return resultList;
		}
		
		return resultList;
	}
	
	/**
     * 쉼표로 구분되어진 코드값 체크
     * ex) regExpCommaCode("01,02,03")
     * @param str
     * @param 각 코드별 허용길이
     * @return boolean
     * @author 김동진
	 * @date 2019. 2. 1.
     */
	public static boolean regExpCommaCode(String str) {
		if(isEmpty(str)) return false;
		
		String [] tmpStrList = str.split(",", -1);
		for(String sValue : tmpStrList) {
			if(isEmpty(sValue)) return false;
			if(! Pattern.matches("[0-9]+", sValue)) return false;
		}
		
		return true;
	}
	
	/**
     * 쉼표로 구분되어진 코드값 체크 (각 코드 길이도 체크할 시에 사용)
     * ex) regExpCommaCode("01,02,03")
     * @param str
     * @param 각 코드별 허용길이
     * @return boolean
     * @author 김동진
	 * @date 2019. 1. 22.
     */
	public static boolean regExpCommaCode(String str, int chkLength) {
		if(isEmpty(str)) return false;
		
		String [] tmpStrList = str.split(",", -1);
		for(String sValue : tmpStrList) {
			if(isEmpty(sValue)) return false;
			if(sValue.length() != chkLength) return false;
			if(! Pattern.matches("[0-9]+", sValue)) return false;
		}
		
		return true;
	}
	
	public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        if (sz == 0)
            return false;
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

	/**
	 * 숫자 + 대문자 + 소문자 조합 랜덤 String 생성
	 * @param length
	 * @return
	 */
	public static String getRandomAlphabetAndDigit(int length) {
		String resultString = "";
		int typeCount = 3; // 문자열 종류 갯수 (숫자, 소문자, 대문자)
		
		for (int i = 0; i < length; i++) {
			Random rnd = new Random();
			int type = rnd.nextInt(typeCount);
			
			if (type == 0) { // 숫자
				resultString += rnd.nextInt(10);
			} else if (type == 1) { // 소문자
				resultString += (char)((int)(Math.random()*26)+97);
			} else if (type == 2) { // 대문자
				resultString += (char)((int)(Math.random()*26)+65);
			}
		} 
		
		return resultString;
	}
	
	/**
     * Determine whether a (trimmed) string is empty.
     *
     * @param foo The text to check.
     * @return Whether empty.
     */
    public static final boolean isEmptyTrimmed(String foo) {
        return (foo == null || foo.trim().length() == 0);
    }
    
    /**
     * String2integer.
     *
     * @param str the str
     * @return the int
     */
    public static int string2integer(String str) {

        if (isNull(str)) {
            return 0;
        }

        return Integer.parseInt(str);
    }
    
    /**
     * text 형태를 html태그로 변환
     * @author 허용
     * @date 2019. 02. 27
     * @param str
     * @return
     */
    public static String replaceTextToHtml(String str) {
    	if (str == null || str.trim().equals("")) {
			return "";
		}
    	
    	str = str.replace("&lt;", "<");
    	str = str.replace("&gt;", ">");
    	str = str.replace("&quot;", "\"");
    	str = str.replace("&amp;", "&");
    	str = str.replace("&nbsp;", " ");
    	str = str.replace("&apos;", "'");
    	str = str.replace("&#37;", "%");
    	
    	return str;
    }
    
    /**
     * xss 방어
     * @author 허용
     * @date 2019. 02. 27
     * @param str
     * @return
     */
    public static String replaceForXSS(String str) {
    	if (str == null || str.trim().equals("")) {
			return "";
		}
    	
    	str = str.replace("<", "&lt;");
    	str = str.replace(">", "&gt;");
    	str = str.replace("\"", "&quot;");
    	str = str.replace("'", "&apos;");
    	str = str.replace("%", "&#37;");
    	str = str.replace("update", "");
    	str = str.replace("delete", "");
    	str = str.replace("drop", "");
    	str = str.replace("alert", "");
    	str = str.replace("<script>", "");
    	str = str.replace("</script>", "");
    	return str;
    }
    
    public static String numberFormat(double val){
		NumberFormat nf = NumberFormat.getNumberInstance();
		return nf.format(val);
	}
    
    /**
     * 랜덤으로 정렬하여 배열로 리턴
     * @author 김동진
     * @date 2019. 04. 01
     * @param sortCnt (정렬대상 갯수)
     * @return int[]
     */
    public static int[] getRandomSortArr(int sortCnt) {
    	int[] arr = new int[sortCnt];
        int ran=0;
        boolean cheak;
        Random r = new Random();

        for (int i=0; i<arr.length; i++) {
            ran = r.nextInt(sortCnt)+1; // 0보다 큰 숫자를 조회하기 위함
            cheak = true;
            for (int j=0; j<i; j++) { 
                if(arr[j] == ran) {
                    i--;
                    cheak=false;
                }
            }
            if(cheak)
                arr[i] = ran;
        }
    	return arr;
    }
    
}