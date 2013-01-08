package com.share.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/** 
 *         工具类：(依赖web环境Servlet容器)
 *
 * @author 作      者：lac
 *		  E-mail: liangancai@vrvmail.com.cn 
 * @version V1.0
 *         创建时间：2012-8-4 上午11:44:21 
 */
public final class WebUtil {
	/** MIME Type: bin\html\doc\xls **/
	public static final String MIME_TYPE_HTML = "text/html";
	public static final String MIME_TYPE_BIN = "application/octet-stream";
	public static final String MIME_TYPE_DOC = "application/vnd.ms-word";
	public static final String MIME_TYPE_XLS = "application/vnd.ms-excel";
	
	/** Character Encoding: utf-8 **/
	private static final String ENCODING = "utf-8";
			
	/** 
	* 设置禁止客户端缓存的Header. 
	*/  
	public static void setDisableCacheHeader(HttpServletResponse response) {  
		//Http 1.0 header  
		response.setDateHeader("Expires", 1L);  
		response.addHeader("Pragma", "no-cache");  
		//Http 1.1 header  
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");  
	}
	
	/**
	 * 向相应流Response中写入
	 * 
	 * @param response 响应对象
	 * @param data 字符内容
	 */
	public static void write(HttpServletResponse response, String data) {
		try {
			response.getWriter().write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 向相应流Response中写入
	 * 
	 * @param response 响应对象
	 * @param data 字节内容
	 */
	public static void write(HttpServletResponse response, byte[] data) {
		try {
			response.getOutputStream().write(data);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 重定向
	 * 
	 * @param response
	 * @param url 
	 */
	public static void redirect(HttpServletResponse response, String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件下载
	 * 
	 * @param path 文件路径(绝对)
	 * @param response 响应对象
	 */
	public static void download(String path, HttpServletResponse response) {
		File file = new File(path);
		try {
			//以流的形式下载文件
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			
			//设置响应报头
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), ENCODING));
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType(MIME_TYPE_BIN);
			response.setCharacterEncoding(ENCODING);
			
			//写入响应流数据
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得ServletContext接口的实例
	 * 
	 * @param request 
	 * @return ServletContext
	 */
	public static ServletContext context(HttpServletRequest request) {
		if (null != request) {
			HttpSession session = request.getSession(false);
			if (null == session) {
				session = request.getSession(true);
			}
			return session.getServletContext();
		} else {
			throw new NullPointerException("Request must not be null!");
		}
	}
	
	/**
	 * 向会话中添加属性
	 * 
	 * @param request 请求对象
	 * @param name 属性名称
	 * @param value 属性值 或 null
	 */
	public static void sessionAttr(HttpServletRequest request, String name, Object value) {
		if (value != null) {
			request.getSession().setAttribute(name, value);
		}
		else {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute(name);
			}
		}
	}
	
	/**
	 * 从会话中获取属性
	 * 
	 * @param request 请求对象
	 * @param name 属性名称
	 * @return 属性值 或 null
	 */
	public static Object sessionAttr(HttpServletRequest request, String name) {
		if (null != request) {
			HttpSession session = request.getSession(false);
			return (session != null ? session.getAttribute(name) : null);
		} else {
			throw new NullPointerException("Request must not be null!");
		}
	}
	
	/**
	 * 获得真实的物理路径
	 * 
	 * @param request 请求对象
	 * @param path 相对路径
	 * @return 绝对路径
	 */
	public static String realPath(HttpServletRequest request, String path) {
		if (null != request) {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			HttpSession session = request.getSession(false);
			if (null == session) {
				session = request.getSession(true);
			}
			return session.getServletContext().getRealPath(path);
	    } else {
			throw new NullPointerException("Request must not be null!");
		}
	}
	
	/**
	 * 从Cookie中获取属性
	 * 
	 * @param request 请求对象
	 * @param name 属性名称
	 * @return 属性值 或 null
	 */
	public static String cookieAttr(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
