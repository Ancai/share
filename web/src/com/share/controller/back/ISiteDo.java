/**
 * 
 */
package com.share.controller.back;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.share.bean.Pager;
import com.share.common.Constant;
import com.share.controller.BaseDo;
import com.share.model.ISite;
import com.share.service.ISiteService;

/**
 * 控制器(后台)：我的网址
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-10-31 下午4:39:00
 * @version 1.0
 */
@Controller
@RequestMapping("/iSite")
public class ISiteDo extends BaseDo {
	@Resource
	private ISiteService iSiteService;
	
	/**视图根路径**/
	private static final String VIEW_DEFAULT = "iSite/";
	
	/**
	 * 分页列表
	 */
	@RequestMapping(Constant.URL_LIST)
	public String list(Model model, HttpServletRequest request) {
		model.addAttribute(Constant.PAGE_DATA, iSiteService.findByPager(
				new Pager<ISite>(request.getParameter(Constant.PAGE_NUMBER), pageLink("/iSite/"))));
		
		return VIEW_DEFAULT + Constant.VIEW_LIST;
	}
	
	/**
	 * 编辑页面
	 */
	@RequestMapping(Constant.URL_EDIT + "/{"+ Constant.ID +"}")
	public String editUI(ModelMap map, @PathVariable(Constant.ID)Long id) {
		map.put(Constant.PAGE_MODEL, iSiteService.get(id));
		return VIEW_DEFAULT + Constant.VIEW_EDIT;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(Constant.URL_UPDATE)
	public String update(ISite iSite) {
		ISite tempISite = iSiteService.get(iSite.getId());
		tempISite.setUrlType(iSite.getUrlType());
		tempISite.setUpdated(new Date());
		iSiteService.update(tempISite);
		
		return Constant.VIEW_REDIRECT + Constant.URL_LIST;
	}
}
