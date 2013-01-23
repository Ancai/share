package com.share.util; 
/** 
 *         说      明：MyCom工具类
 *
 * @author 作      者：glw
 *		  E-mail: linwu_gao@163.com 
 * @version V1.0
 *         创建时间：2013-1-5 下午02:47:14 
 */
public class ComMyComUtil {

	/**
	 * MyCom组件的名称
	 */
	public final static String COM_MYCOM_NAME = "MyCom.vrvCom";
	
	/**
	 * 获取MyCom所在路径
	 * @return MyCom所在路径
	 */
	public static String getComDllPth(){
		return (String) LoadComUtil.loadMethod(COM_MYCOM_NAME, "GetComDllPath", null);
	}
	
	/**
	 * 获取MyCom所在计算机的MAC地址
	 * @return
	 */
	public static String getMacAddress(){
		return (String) LoadComUtil.loadMethod(COM_MYCOM_NAME, "GetMacAddress", null);
	}
	
	/**
	 * 远程服务接口
	 * @param params 参数
	 * @return
	 */
	public static Integer sendPktInfo(Object[] params){
		return (Integer) LoadComUtil.loadMethod(COM_MYCOM_NAME, "SendPktInfo", params);	
	}
	
	/**
	 * 计算CRC值
	 * 注意：此处没有直接调用GetCRCValue方法，因为该方法的返回值是Dword类型，Java无法接受，就扩展了一个GetCRCValueJava方法
	 * @param strs 要被CRC的字符串
	 * @return
	 */
	public static String getCRCValue(String[] strs){
		return (String)LoadComUtil.loadMethod(COM_MYCOM_NAME, "GetCRCValueJava", strs);
	}
	
	/**
	 * 计算CRC的16进制的值
	 * @param strs 要被CRC的字符串
	 * @return
	 */
	public static String getCRCHexValue(String[] strs){
		String o = (String) LoadComUtil.loadMethod(COM_MYCOM_NAME, "GetCRCHexValue", strs);
		return  o;
	}
}
 