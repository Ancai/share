/**
 * 
 */
package com.share.controller.back;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.share.bean.FileBean;
import com.share.bean.Pager;
import com.share.common.Constant;
import com.share.controller.BaseDo;
import com.share.util.CoreUtil;
import com.share.util.WebUtil;

/**
 * 控制器(后台)：文件(浏览/上传等操作)
 * 		{@link FileBean}
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-8-29 下午5:56:07
 * @version 1.0
 */
@Controller
@RequestMapping("/file")
public class FileDo extends BaseDo {
	/**视图根路径**/
	private static final String VIEW_DEFAULT = "file/";
	/**上传文件的默认目录**/
	private static final String DEFAULT_UP_DIR = "/upload/";
	/**页面的传递的参数名称**/
	private static final String PARAM_DIR = "dir"; //服务器目录或文件路径
	private static final String PARAM_UP_DIR = "upDir"; //要上传到服务器的位置
	
	
	/**
	 * 上传文件
	 */
	@RequestMapping(value = "/upload", method = {RequestMethod.POST})
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String uploadPath = CoreUtil.notNull(request.getParameter(PARAM_UP_DIR)) ? request.getParameter(PARAM_UP_DIR) : DEFAULT_UP_DIR;
		String realPath = WebUtil.realPath(request, uploadPath);
		if (!file.isEmpty()) {
			byte[] bytes;
			try {
				bytes = file.getBytes();
				File tempFile = new File(realPath);
				if (!tempFile.exists()) {
					tempFile.mkdir();
				}
				FileOutputStream fos = new FileOutputStream(realPath +"/"+ file.getOriginalFilename());
				fos.write(bytes);
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("上传文件异常", e);
			}
		}
		
		return Constant.VIEW_REDIRECT + "browse?" + PARAM_DIR + "=" + uploadPath;
	}
	
	/**
	 * 浏览服务器上的目录
	 */
	@RequestMapping("/browse")
	public String browse(HttpServletRequest request, Model model) {	
		String dir = request.getParameter(PARAM_DIR);
		String path = WebUtil.realPath(request, "/") + dir;
		File file = new File(path);
		if (file.isDirectory()) {
			List<FileBean> fileBeanList = new ArrayList<FileBean>();
			for (String item : file.list()) {
				File tempFile = new File(path + "/" + item);
				fileBeanList.add(new FileBean(item, tempFile.length()/Constant.HEX_DISK_STORE, null,
						new Date(tempFile.lastModified()), tempFile.isFile() ? FileBean.Type.FILE : FileBean.Type.DIRECTORY,
								"/".equals(dir)? dir + tempFile.getName() : dir +"/"+ tempFile.getName()));
			}
			String pageLink = Constant.SERVER_ROOT_PATH + "/file/browse?dir=" + dir; //分页链接地址
			model.addAttribute(Constant.PAGE_DATA, 
					new Pager<FileBean>(request.getParameter(Constant.PAGE_NUMBER), pageLink, fileBeanList));
		}
		
		return VIEW_DEFAULT + Constant.VIEW_LIST; //目录列表页面
	}
	
	/**
	 * 读取服务器上的文件
	 */
	@RequestMapping("/read")
	public String readFile(HttpServletRequest request, Model model) {
		String result = null;
		String dir = request.getParameter(PARAM_DIR);
		String path = WebUtil.realPath(request, dir);
		File file = new File(path);
		if (file.isFile()) {
			try {
				model.addAttribute(Constant.PAGE_DATA, FileUtil.readAsString(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("浏览服务器上的文件", e);
			} finally {
				result  = VIEW_DEFAULT + Constant.VIEW_SHOW; //文件内容页面
			}	
		}
		
		return result;
	}
	
	/**
	 * 删除服务器上的文件/目录
	 */
	@RequestMapping("/del")
	public String delFile(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		String dir = request.getParameter(PARAM_DIR);
		dir = dir.charAt(0) == '/' ? dir : "/" + dir; //确保以 / 开头
		String path = session.getServletContext().getRealPath("/") + dir;
		File file = new File(path);
	    if (file.exists()) {
	    	file.delete();
		}

	    return Constant.VIEW_REDIRECT + "browse?" + PARAM_DIR + "=" + dir.substring(0, dir.lastIndexOf("/")); //返回上一级目录
	}
	
	/**
	 * 下载服务器上的文件
	 */
	@RequestMapping("/down")
	public void download(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String dir = request.getParameter(PARAM_DIR);
		String path = session.getServletContext().getRealPath("/") + dir;
		WebUtil.download(path, response);
	}
}
