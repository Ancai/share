package com.share.util;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/** 
 *         说      明：核心工具类
 *
 * @author 作      者：lac
 *		  E-mail: liangancai@vrvmail.com.cn 
 * @version V1.0
 *         创建时间：2012-8-4 下午02:25:29 
 */
public final class CoreUtil { 
	/** 常量：26个英文字母(含大小写)和数字 **/
	private static final char[] CHAR_ARRAY = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 
	    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    
    /**
     * 通用加密：SHA(或MD5)
     * 
     * @param data 要加密的数据
     * @return 加密后的数据
     */
	public static String encrypt(String data) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',   
				'a', 'b', 'c', 'd', 'e', 'f' };   
	    try {   
		    byte[] strTemp = data.getBytes();   
		    //使用MD5创建MessageDigest对象   
		    MessageDigest mdTemp = MessageDigest.getInstance("MD5");   
		    mdTemp.update(strTemp);   
		    byte[] md = mdTemp.digest();   
		    int j = md.length;   
		    char str[] = new char[j * 2];   
		    int k = 0;   
		    for (int i = 0; i < j; i++) {
			    byte b = md[i];   
			    //System.out.println((int)b);   
			    //将没个数(int)b进行双字节加密   
			    str[k++] = hexDigits[b >> 4 & 0xf];   
			    str[k++] = hexDigits[b & 0xf];   
		    }   
		    return new String(str);   
	    } catch (Exception e) {
		    return null;
	    }   
	} 
	
	/**
	 * 判断传递来的对象obj是否为空
	 * 
	 * @param obj
	 * @return boolean (null == obj)
	 */
	public static boolean isNull(Object obj) {
		return null == obj;
	}
	
	/**
	 * 判断传递来的对象obj是否不为空
	 * 
	 * @param obj
	 * @return boolean (null == obj)
	 */
	public static boolean notNull(Object obj) {
		return null != obj;
	}
	
	/**
	 * 日期格式化
	 * 
	 * @param dt 日期/时间
	 * @param pattern 格式
	 * @return String
	 */
	public static String formatDate(Date dt, String pattern) {
		return new SimpleDateFormat(pattern).format(dt);
	}
	
	/**
	 * 类型转换：
	 * 		String ---> Date
	 * 
	 * @param dtStr 日期/时间(字符串类型)
	 * @param pattern 格式
	 * @return Date
	 */
	public static Date parseDate(String dtStr, String pattern) {
		Date parsedDt = null;
		try {
			parsedDt = new SimpleDateFormat(pattern).parse(dtStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parsedDt;
	}
		
	/**
	 * 拼接JSON格式的数据
	 * 
	 * @param list 对象集合
	 * @param props 对象的属性(数组)
	 * @return String
	 */
	public static <T> String json(List<T> list, String... props) {
		StringBuffer jsonBuff = new StringBuffer("[");
		String[] methods = new String[props.length];
		if (notNull(props) && props.length > 0) {
			for (int i = 0; i < props.length; i++) {
				methods[i] = "get" + props[i].substring(0, 1).toUpperCase() + props[i].substring(1);
			}
		}
		for (T t : list) {
			jsonBuff.append("{");
			for (int i = 0; i < props.length; i++) {
				try {
					jsonBuff.append(props[i] + ": '" + t.getClass().getMethod(methods[i]).invoke(t) + "'");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (props.length > 1 && i != (props.length-1)) {
					jsonBuff.append(", ");
				}
			}
			jsonBuff.append("},");
		}
		
		return jsonBuff.replace(jsonBuff.length()-1, jsonBuff.length(), "]").toString();
	}
	
	/**
	 * 获得随机字符
	 * 
	 * @return char 字符：数字或字母
	 */
	public static char randomChar() {
		return CHAR_ARRAY[(int)(Math.random() * 46)];
	}
}
