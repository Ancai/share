/**
 * 
 */
package com.share.util;

/** 
 *         工具类：CCP项目的逻辑处理密切相关
 *
 * @author 作      者：lac
 *		  E-mail: liangancai@vrvmail.com.cn 
 * @version V1.0
 *         创建时间：2013-1-23 上午06:14:01 
 */
public final class CCPUtil {
	/**
	 * 根据起始ip(如：192.168.32.1)和终止ip(如：192.168.32.188)，获得ip段
	 * 
	 * @return String ip段
	 */
	public static String getIPRange(String ip1, String ip2) {
		StringBuffer buf = new StringBuffer();
		int num1 = Integer.valueOf(ip1.split("\\.")[3]);
		int num2 = Integer.valueOf(ip2.split("\\.")[3]);
		String tempIP = ip1.substring(0, ip1.lastIndexOf("."));
		for (int i = num1; i < num2; i++) {
			buf.append("'");
			buf.append(tempIP);
			buf.append(".");
			buf.append(i);
			buf.append("'");
			buf.append(",");
		}
		buf.append("'"+ip2+"'");
		
		return buf.toString();
	}
}
