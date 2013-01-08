/**
 * 
 */
package com.share.webservice;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * WebService试验类：在main方法中运行
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-5 下午3:28:37
 * @version 1.0
 */
@WebService 
public class Java6WebService { 
	/** 
	* Web服务中的业务方法 
	* 
	* @return 一个字符串 
	*/ 
	public String doSomething() { 
		return "Hello Java6 WebService!"; 
	} 
	public static void main(String[] args) { 
	//发布一个WebService 
		Endpoint.publish("http://localhost:8089/java6ws/lavasoft.Java6WebService", new Java6WebService()); 
	} 
}
