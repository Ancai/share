package com.share.test;

/**
 * 测试类：公共
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-8-4 下午8:35:41
 * @version 1.0
 */
public class CommonTest {

	@Override
	public String toString(){
		return "this is CommonTest class";
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(CommonTest.class);
		CommonTest ct = new CommonTest();
		System.out.println(ct);
	}

}
