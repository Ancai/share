package com.share.common;

/**
 * 公共类：系统常量
 * 
 * @author Ancai email: ancai0729@gmail.com
 * @since 2012-8-4 下午8:35:41
 * @version 1.0
 */
public interface Constant {
	/**会话保存的键：用户**/
	 String SESSION_USER = "userKey";
	/**会话保存的键：管理员**/
	 String SESSION_ADMIN = "adminKey";
	/**会话保存的键：验证码**/
	 String SESSION_CHECKCODE = "checkCode";
	
	/**Cookie保存的键：用户名**/
	 String COOKIE_USER_NAME = "username";
	/**Cookie保存的键：用户编号**/
	 String COOKIE_USER_ID = "userId";
	
	/**路径：列表**/
	 String URL_LIST = "list";
	/**路径：新增**/
	 String URL_ADD = "add";
	/**路径：创建(保存)**/
	 String URL_SAVE = "save";
	/**路径：编辑**/
	 String URL_EDIT = "edit";
	/**路径：修改**/
	 String URL_UPDATE = "update";
	/**路径：删除**/
	 String URL_DEL = "del";
	
	
	/**消息：成功**/
	 String MES_SUCCESS = "SUCCESS";
	/**消息：失败**/
	 String MES_FAIL = "FAIL";
	
	/**默认的字符编码格式：UTF-8**/
	 String CHARSET = "UTF-8";
	
	/**上传文件路径：图片**/
	 String UPLOAD_IMAGE_DIR = "/upload/image/"; // 
	/**上传文件路径：媒体**/
	 String UPLOAD_MEDIA_DIR = "/upload/media/";
	/**上传文件路径：其它文件**/
	 String UPLOAD_FILE_DIR = "/upload/file/";
	
	/**格式化：日期**/
	 String FORMAT_DATE = "yyyy-MM-dd";
	/**格式化：日期时间**/
	 String FORMAT_DATATIME = "yyyy-MM-dd HH:mm:ss";
	
	/**列表：行记录的ID**/
	 String ID = "id";
	/**列表：行记录的ID集合**/
	 String IDS = "ids";
	
	/**分页相关：当前页码**/
	 String PAGE_NUMBER = "pageNumber";
	/**分页相关：每页记录数**/
	 String PAGE_SIZE = "pageSize";
	/**分页相关：页面数据集合**/
	 String PAGE_DATA = "pageData";
	/**分页相关：页面数据模型**/
	 String PAGE_MODEL = "model";
	
	/**视图：主页面**/
	 String VIEW_INDEX = "/html/index2.html";
	/**视图：登录页面**/
	 String VIEW_LOGIN = "/html/login.html";
	/**视图：列表页面**/
	 String VIEW_LIST = "listUI";
	/**视图：添加页面**/
	 String VIEW_ADD = "addUI";
	/**视图：编辑页面**/
	 String VIEW_EDIT = "editUI";
	/**视图：详情页面**/
	 String VIEW_SHOW = "showUI";
	
	/**视图生成方式：转发**/
	 String VIEW_FORWARD = "forward:";
	/**视图生成方式：重定向**/
	 String VIEW_REDIRECT = "redirect:";
	
	/**服务器端信息：日期**/
	 String SERVER_DATE = "serverDate";
	/**服务器端信息：根路径**/
	 String SERVER_ROOT_PATH = "/Share";
	
	/** 进制转换：计算机存储 **/
	 int HEX_DISK_STORE = 1024;
	
	/** 符号：与符 **/
	 String SYMBOL_AND = "&";

}
