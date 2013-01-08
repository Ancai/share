/**
 * 
 */
package com.share.controller.back;

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
import com.share.model.Section;
import com.share.service.SectionService;

/**
 * 控制器(后台)：栏目/板块/分类
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-8-23 上午11:24:51
 * @version 1.0
 */
@Controller
@RequestMapping("/section")
public class SectionDo extends BaseDo{
	@Resource
	private SectionService sectionService;
	
	/**视图根路径**/
	private static final String VIEW_DEFAULT = "section/";
	
	/**
	 * 分页列表
	 */
	@RequestMapping(Constant.URL_LIST)
	public String list(Model model, HttpServletRequest request) {
		model.addAttribute(Constant.PAGE_DATA, sectionService.findByPager(
				new Pager<Section>(request.getParameter(Constant.PAGE_NUMBER), pageLink("/section/"))));
		return VIEW_DEFAULT + Constant.VIEW_LIST;
	}
	
	/**
	 * 编辑页面
	 */
	@RequestMapping(Constant.URL_EDIT)
	public String editUI(ModelMap map, @PathVariable(Constant.ID)Integer id) {
		map.put(Constant.PAGE_MODEL, sectionService.get(id));
		return VIEW_DEFAULT + Constant.VIEW_EDIT;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(Constant.URL_UPDATE)
	public String update(Section section) {
		Section tempSection = sectionService.get(section.getId());
		if (StringUtils.isBlank(section.getName())) {
			section.setName(tempSection.getName());
		}
		if (null != section.getLinks()) {
			section.setLinks(tempSection.getLinks());		
		}
		if (null != section.getParent()) {
			section.setParent(tempSection.getParent());
		}
		section.setUpdated(new Date());
		sectionService.update(section);
		
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
	public String save(Section section) {
		section.setUpdated(new Date());
		sectionService.save(section);
		
		return Constant.VIEW_FORWARD + Constant.URL_LIST;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(Constant.URL_DEL)
	public String delete(@RequestParam(Constant.ID)Integer id) {
		sectionService.delete(id);
		
		return Constant.VIEW_FORWARD + Constant.URL_LIST;
	}
}
