/**
 * 
 */
package com.share.controller.back;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.share.bean.Pager;
import com.share.common.Constant;
import com.share.controller.BaseDo;
import com.share.model.Link;
import com.share.service.LinkService;

/**
 * 控制器(后台)：链接
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-8-23 上午11:22:29
 * @version 1.0
 */
@Controller
@RequestMapping("/link")
public class LinkDo extends BaseDo{
	@Resource 
	private LinkService linkService;
	
	/**视图根路径**/
	private static final String VIEW_DEFAULT = "link/";
	
	/**
	 * 分页列表
	 */
	@RequestMapping(Constant.URL_LIST)
	public String list(Model model, HttpServletRequest request) {
		model.addAttribute(Constant.PAGE_DATA, linkService.findByPager(
				new Pager<Link>(request.getParameter(Constant.PAGE_NUMBER), pageLink("/link/"))));
		
		return VIEW_DEFAULT + Constant.VIEW_LIST;
	}
	
	/**
	 * 编辑页面
	 */
	@RequestMapping(Constant.URL_EDIT + "/{"+ Constant.ID +"}")
	public String editUI(ModelMap map, @PathVariable(Constant.ID)Integer id) {
		map.put(Constant.PAGE_MODEL, linkService.get(id));
		return VIEW_DEFAULT + Constant.VIEW_EDIT;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(Constant.URL_UPDATE)
	public String update(Link link) {
		Link tempLink = linkService.get(link.getId());
		if (StringUtils.isNotBlank(link.getDescription())) {
			tempLink.setDescription(link.getDescription());
		}
		if (StringUtils.isNotBlank(link.getName())) {
			tempLink.setName(link.getName());
		}
		if (StringUtils.isNotBlank(link.getUrl())) {
			tempLink.setUrl(link.getUrl());
		}
		if (StringUtils.isNotBlank(link.getImage())) {
			tempLink.setImage(link.getImage());
		}
		if (StringUtils.isNotBlank(link.getTarget())) {
			tempLink.setTarget(link.getTarget());
		}
		if (null != link.getVisible()) {
			tempLink.setVisible(link.getVisible());
		}
		if (null != link.getLinkMetas()) {
			tempLink.setLinkMetas(link.getLinkMetas());
		}
		if (null != link.getSection()) {
			tempLink.setSection(link.getSection());
		}
		tempLink.setRating(link.getRating());
		tempLink.setUpdated(new Date());
		this.linkService.update(link);
		
		return Constant.VIEW_FORWARD + Constant.URL_LIST;
	}
	
	/**
	 * 添加页面
	 */
	@RequestMapping(Constant.URL_ADD)
	public String addUI() {
		return VIEW_DEFAULT + Constant.VIEW_ADD;
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(Constant.URL_SAVE)
	public String save(Link link) {
		link.setRating(0);
		link.setUpdated(new Date());
		link.setVisible(true);
		return Constant.VIEW_FORWARD + Constant.URL_LIST;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(Constant.URL_DEL)
	public String delete(@RequestParam(Constant.ID) Integer id) {
		this.linkService.delete(id);
		return Constant.VIEW_FORWARD + Constant.URL_LIST;
	}
	
	/**
	 * 抓取网址链接
	 */
	@RequestMapping("/catchLinks")
	public String catchLinks(@RequestParam("url")String url) {
		try {
			linkService.catchLinks(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("抓取网址链接：", e);
		}
		return null;
	}
}
