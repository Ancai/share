package com.share.util;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.JComException;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;

/**
 * 用来加载并调用Com组件的方法的工具类
 * @author glw
 *
 */
public class LoadComUtil {
	/**
	 * 加载并调用方法
	 * @param comName Com组件名称
	 * @param methodName 方法名称 
	 * @param params 方法中需要的参数
	 * @return 返回值(Object类型)
	 */
	public static Object loadMethod(String comName,String methodName,Object[] params){
		ReleaseManager rm = new ReleaseManager();
		try {
			IDispatch jcom = new IDispatch(rm,comName);
			return jcom.method(methodName, params);
		} catch (JComException e) {
			e.printStackTrace();
			throw new RuntimeException("加载["+comName+"]组件或调用"+methodName+"接口时出错"+e);
		}
	}
	
	/**
	 * 判断Com组件或Ocx控件是否已注册
	 * @param comName 组件名称
	 * @return true 已注册
	 *         false 没有注册
	 */
	public static boolean isRegedit(String comName){
		ReleaseManager rm = new ReleaseManager();
		try {
			new IDispatch(rm,comName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
//	public static void main(String[] args) {
//		Object[] params = {"192.168.88.101","AssistIP=192.168.88.100&UserName=glw",153,1,0,0,"temp.txt",-1,"22"};
//		Object obj = loadMethod("MyCom.VrvCom", "SendPktInfo", params);
//		System.out.println(obj.toString());
//		System.out.println(isRegedit("MyCom.VrvCom"));
//	}
}
