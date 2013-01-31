/**
 * 
 */
package com.share.util;

import org.apache.commons.lang.ArrayUtils;

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
	
	/**
	 * 将数字分解成字符串，适用于内网特定的逻辑规则
	 * 		例如：15 ---> 1,2,4,8; 9 ---> 1,8
	 * 
	 * @param str
	 * @return String
	 */
	public static String numSplit(String str) {
		String val = "";
		String[] nums = {"1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048", "4096", "8192"};
		char[] chars = Integer.toBinaryString(Integer.valueOf(str)).toCharArray();
		if (ArrayUtils.contains(nums, str)) {
			val = str;
		} else {
			StringBuffer sbu = new StringBuffer();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] == '1') {
					sbu.append(nums[i]);
					sbu.append(",");
				}
			}
			if (sbu.charAt(sbu.length()-1) == ',') {//去除最后一个逗号
				sbu.deleteCharAt(sbu.length()-1);
			}
			val = sbu.toString();
		}
		
		return val;
	}
}
