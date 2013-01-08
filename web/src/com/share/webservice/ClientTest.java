/**
 * 
 */
package com.share.webservice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * WebService试验：客户端调用
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-5 下午5:56:59
 * @version 1.0
 */
public class ClientTest {
	public final static QName SERVICE_NAME = new QName("http://webservice.share.com/", "HelloWorldImplService");
    public final static QName PORT_NAME = new QName("http://webservice.share.com/", "HelloWorldImplPort");  
  
    private static final String WSDL_LOCATION = "http://localhost:8080/web/services/HelloWorld?wsdl";  
	/**
	 * 1,整合Spring
	 */
	public static void bondSpring() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("webservice/client.xml");
		HelloWorld client = (HelloWorld) ctx.getBean("client");
		String result = client.say("你好!");
		System.out.println(result);
	}
	
	/**
	 * 2,WSDL2Java generated Client
	 * 依据wsdl文件生成java客户端，直接调用
	 * 详见package：com.share.webservice.client
	 */
	
	/**
	 * 3,JAX-WS Proxy
	 * 和生成存根类的方式相反，proxy是不需要执行wsdl2java的。但是在编译环境中需要接口类和VO类。
	 * 这里，通过指定WSDL_LOCATION和PORT_NAME，使用Service.create创建service，使用service.getPort获得服务引用
	 * 
	 * @throws MalformedURLException 
	 */
	public static void proxyClient() throws MalformedURLException {
		URL wsdlURL = new URL(WSDL_LOCATION);  
        Service service = Service.create(wsdlURL, SERVICE_NAME);  
        HelloWorld helloWorld = service.getPort(PORT_NAME, HelloWorld.class);     
        System.out.println(helloWorld.say("世界，你好！"));
	}
	
	/**
	 * 4,使用JaxWsProxyFactoryBean 类简化Proxy。
	 * 		同1
	 * 
	 */
	public static void proxyClient2() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();  
        factory.setServiceClass(HelloWorld.class);  
        factory.setAddress(WSDL_LOCATION);  
        HelloWorld helloWorld = (HelloWorld) factory.create();    
        System.out.println(helloWorld.say("世界，你好！"));
	}
	
	/**
	 * JaxWsDynamicClientFactory.newInstance获得JaxWsDynamicClientFactory实例。
	 * 通过dcf.createClient获得Client客户端引用。
	 * 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static void dynamicClient() 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, 
			SecurityException, NoSuchMethodException, IllegalArgumentException, 
			InvocationTargetException {
		//JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		//Client client = dcf.createClient(WSDL_LOCATION);
		Object helloWorld = Thread.currentThread().getContextClassLoader()
				.loadClass("com.share.webservice.HelloWorld").newInstance();
		Method method = helloWorld.getClass().getMethod("say");
		System.out.println(method.invoke(helloWorld, "世界，你好！"));
	}
	
	public static void main(String[] args) {
		try {
			//bondSpring();
			//proxyClient();
			//proxyClient2();
			dynamicClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
