/**
 * 
 */
package com.share.controller.web;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.bean.GridData;
import com.share.model.Ip;
import com.share.model.Staff;
import com.share.service.IpService;
import com.share.service.StaffService;
import com.share.util.CoreUtil;

/**
 * 控制器：operamasks-ui(基于Jquery并提供丰富组件的前端UI库)试验
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-28 下午6:09:03
 * @version 1.0
 */
@Controller
@RequestMapping("/operamasks-ui")
public class OperamasksUI {
	/** 页面参数：行数据的开始位置 **/
	private static final String P_START = "start";
	/** 页面参数：每页大小 **/
	private static final String P_LIMIT = "limit";
	/** 排序方式：升序 **/
	private static final String ASC = "asc";
	
	@Resource(name = "ipService") 
	private IpService ipService;
	@Resource(name = "staffService")
	private StaffService staffService;
	
	/**
	 * 普通的Grid
	 */
	@RequestMapping(value = "/demos/grid/griddata.do", method = {RequestMethod.GET})
	@ResponseBody
	public GridData<Ip> grid(@RequestParam(P_START) int start, @RequestParam(P_LIMIT) int limit) {
		GridData<Ip> gridData = new GridData<Ip>();
		gridData.setTotal(ipService.ipInfos().size());
		int end = (start + limit) > ipService.ipInfos().size() ? ipService.ipInfos().size() : (start + limit);
		gridData.setRows(ipService.ipInfos().subList(start, end));
		
		return gridData;
	}
	
	/**
	 * 排序的Grid
	 */
	@RequestMapping(value = "/sortgriddata.data", method = {RequestMethod.GET})
	@ResponseBody
	public GridData<Ip> gridSort(@RequestParam(P_START) int start, @RequestParam(P_LIMIT) int limit, 
			HttpServletRequest request) {
		GridData<Ip> gridData = new GridData<Ip>();	
		int end = (start + limit) > ipService.ipInfos().size() ? ipService.ipInfos().size() : (start + limit);
		String sortBy = CoreUtil.isNull(request.getParameter("sortBy")) ? null : request.getParameter("sortBy");
		String sortDir = CoreUtil.isNull(request.getParameter("sortDir")) ? null : request.getParameter("sortDir");
		
		if (CoreUtil.isNull(sortBy)) {
			gridData.setRows(ipService.ipInfos().subList(start, end));
		} else {
			final int sort = ASC.equals(sortDir) ? 1 : -1;
			Comparator<Ip> comparator = null;
			List<Ip> tempList = ipService.ipInfos();
			if ("city".equals(sortBy)) {
				comparator = new Comparator<Ip>() {
					@Override
					public int compare(Ip o1, Ip o2) {
						// TODO Auto-generated method stub
						return sort * o1.getCity().compareTo(o2.getCity());
					}
				};
			}
			Collections.sort(tempList, comparator);
			gridData.setRows(tempList.subList(start, end));
		}
		gridData.setTotal(ipService.ipInfos().size());
		
		return gridData;
	}
	
	/**
	 * 复杂的Grid
	 */
	@RequestMapping(value = "/complexGridData.do", method = {RequestMethod.GET})
	@ResponseBody
	public GridData<Staff> gridComplex(@RequestParam(P_START) int start, @RequestParam(P_LIMIT) int limit) {
		GridData<Staff> gridData = new GridData<Staff>();
		gridData.setTotal(staffService.getTotal());
		gridData.setRows(staffService.query(start, limit));
		
		return gridData;
	}
}
