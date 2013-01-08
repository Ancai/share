/**
 * 
 */
package com.share.common;

/**
 * 公共类：核心枚举
 *
 * @author Ancai email：ancai0729@gmail.com
 * @since 2012-8-26 上午10:20:09
 * @version 1.0
 */
public final class CoreEnum {
	/**
	 * 网址类型
	 */
	public enum URLType {
		COMPLEX("综合"), GAME("游戏"), TV("影视"), READING("阅读"), COMMUNITY("社区"), OTHER("其它"),
		LICAI("理财"), SHOPING("购物"), MUSIC("音乐"), PE("体育") ;
		
	    private String name;  
		private URLType(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
}
