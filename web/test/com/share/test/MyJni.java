/**
 * 
 */
package com.share.test;

/**
 * 测试类：JNI
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-5 上午9:38:43
 * @version 1.0
 */
public class MyJni {
	//使用JNI的关键字native
	//这个关键字决定我们的哪些方法能在C文件中使用
	//只需声明，不必实现
	public native void display();
	public native double sum(double x, double y);
	
	//到时调用我们写好的C文件
//	static {
//		System.loadLibrary("sum");
//	}
	public static void main(String[] args) {
		//测试用
//		new MyJni().display();
//		System.out.println(new MyJni().sum(2.0, 3.0));
	}
}
