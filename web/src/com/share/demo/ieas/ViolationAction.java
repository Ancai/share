//package com.vrv.ieas.action;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//
//import net.sf.json.JSONArray;
//
//import com.vrv.ieas.utils.HQLBuilderUtil;
//import com.vrv.ieas.base.BaseAction;
//import com.vrv.ieas.bean.AmChart;
//import com.vrv.ieas.bean.ViolationBean;
//import com.vrv.ieas.domain.ViolationEvent;
//import com.vrv.ieas.utils.PageViewUtil;
//
///** 
// *         说      明：终端 Action
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2012-8-8 下午02:53:23 
// */
//@Controller
//@Scope("prototype")
//public class ViolationAction extends BaseAction{
//
//	private static final long serialVersionUID = 1L;
//	
//	/**前台页面传递的参数名:日期范围**/
//	private static final String PARAM_RANGE = "recentDays";
//	/**默认的参数(日期范围)值**/
//	private static final Integer PARAM_DEFAULT_VALUE = 7;
//	private ViolationEvent violation;
//
//	/**
//	 * 违规出口随时间的变化(曲线)
//	 */
//	public void portByTime() {
//		this.msg = RESULT_SUCCESS;
//		try {
//			int recentDays = (null != getRequest().getParameter(PARAM_RANGE))?
//					Integer.valueOf(getRequest().getParameter(PARAM_RANGE)):
//						PARAM_DEFAULT_VALUE; 
//			this.msg = JSONArray.fromObject(violationEventService.portByTimeChart(recentDays)).toString();
//		} catch (Exception e) {
//			// TODO: handle exception
//			this.msg= RESULT_ERROR;
//			log.error( "获取'违规出口随时间的变化(曲线)数据'时报错！",e);
//		} finally {
//			print(this.msg);
//		}
//	}
//	
//	/**
//	 * 违规终端随时间的变化(曲线)
//	 */
//	public void terminalByTime() {
//		this.msg = RESULT_SUCCESS;
//		try {
//			int recentDays = (null != getRequest().getParameter(PARAM_RANGE))?
//					Integer.valueOf(getRequest().getParameter(PARAM_RANGE)):
//						PARAM_DEFAULT_VALUE; 
//			this.msg = JSONArray.fromObject(violationEventService.terminalByTimeChart(recentDays)).toString();
//		} catch (Exception e) {
//			// TODO: handle exception
//			this.msg= RESULT_ERROR;
//			log.error( "获取'违规终端随时间的变化(曲线)数据'时报错！",e);
//		} finally {
//			print(this.msg);
//		}
//	}
//	
//	/**
//	 * "区域"树形菜单
//	 */
//	public void areaTree() {
//		try {
//			this.msg = areaService.getTreeCodeMenu(
//					areaService.queryByHQLBuilder(new HQLBuilderUtil(ViolationEvent.class).addWhereClause(" this.parent is null ")), true);
//		} catch (Exception e) {
//			// TODO: handle exception
//			this.msg= RESULT_ERROR;
//			log.error( "获取'区域'树形菜单时报错！",e);
//		} finally {
//			print(this.msg);
//		}
//	}
//	
//	/**
//	 * 不同单位中违规出口的横向对比(柱状图)
//	 */
//	public void portWithUnit() {
//		this.msg = RESULT_SUCCESS;
//		try {
//			int recentDays = (null != getRequest().getParameter(PARAM_RANGE))?
//					Integer.valueOf(getRequest().getParameter(PARAM_RANGE)):
//						PARAM_DEFAULT_VALUE;
//			List<AmChart> chartList = 
//				violationEventService.portWithUnit(violationEventService.unitByTimeList(recentDays));
//			this.msg = JSONArray.fromObject(chartList).toString();
//		} catch (Exception e) {
//			// TODO: handle exception
//			this.msg= RESULT_ERROR;
//			log.error( "获取'不同单位中违规出口的横向对比(柱状图)数据'时报错！",e);
//		} finally {
//			print(this.msg);
//		}
//	}
//	
//	/**
//	 * 不同单位中违规终端的横向对比(柱状图)
//	 */
//	public void terminalWithUnit() {
//		this.msg = RESULT_SUCCESS;
//		try {
//			int recentDays = (null != getRequest().getParameter(PARAM_RANGE))?
//					Integer.valueOf(getRequest().getParameter(PARAM_RANGE)):
//						PARAM_DEFAULT_VALUE; 
//			List<AmChart> chartList = 
//				violationEventService.terminalWithUnit(violationEventService.unitByTimeList(recentDays));
//			this.msg = JSONArray.fromObject(chartList).toString();				
//		} catch (Exception e) {
//			// TODO: handle exception
//			this.msg= RESULT_ERROR;
//			log.error( "获取'不同单位中违规终端的横向对比(柱状图)数据'时报错！",e);
//		} finally {
//			print(this.msg);
//		}
//	}
//	
//	/**
//	 * 分析》违规出口
//	 */
// 	public String analyzePort() {
//  		return "analyzePort";
//	}
// 	/**
//	 * 分析》违规出口 列表页面 
//	 */
//   @SuppressWarnings("unchecked")
//   public String analyzePortTable(){
//	 	try {
//			int recentDays = (null != getRequest().getParameter(PARAM_RANGE))?
//					Integer.valueOf(getRequest().getParameter(PARAM_RANGE)):
//						PARAM_DEFAULT_VALUE; 
//			 getRequest().setAttribute("recentDays", recentDays);
// 			//有选择地抓取数据，实现分页
//			List<ViolationBean> totalList = violationEventService.portByTimeList(recentDays);
//			List pageList = new ArrayList();
//			for (int i = (getPageNum()-1)*getPageSize(); i < totalList.size() && i < getPageNum()*getPageSize(); i++) {
//				pageList.add(totalList.get(i));
//			}
//			pageView = new PageViewUtil(getPageNum(), getPageSize(), totalList.size(), pageList);
//			this.msg = RESULT_SUCCESS;
//		} catch (Exception e) {
//			// TODO: handle exception
//			this.msg = RESULT_ERROR;
//			log.error("获取'分析》违规出口'列表数据时报错！", e);
//		}
//		return "analyzePortTable";
//   }
//	/**
//	 * 分析》违规出口 图形界面
//	 */
//   public String analyzePortGraph(){
// 		return "analyzePortGraph";
//  }
//	/**
//	 * 分析》违规终端
//	 */
//	public String analyzeTerminal() {
//  		return "analyzeTerminal";
//	}
//	/**
//	 * 分析》违规终端列表页面
//	 */
//	@SuppressWarnings("unchecked")
//	public String analyzeTerminalTable(){
//		try {
//			int recentDays = (null != getRequest().getParameter(PARAM_RANGE))?
//					Integer.valueOf(getRequest().getParameter(PARAM_RANGE)):
//						PARAM_DEFAULT_VALUE; 
//			getRequest().setAttribute("recentDays", recentDays);
//			//有选择地抓取数据，实现分页
//			List<ViolationBean> totalList = violationEventService.terminalByTimeList(recentDays);
//			List pageList = new ArrayList();
//			for (int i = (getPageNum()-1)*getPageSize(); i < totalList.size() && i < getPageNum()*getPageSize(); i++) {
//				pageList.add(totalList.get(i));
//			}
//			pageView = new PageViewUtil(getPageNum(), getPageSize(), totalList.size(), pageList);
// 			this.msg = RESULT_SUCCESS;
//		} catch (Exception e) {
//			// TODO: handle exception
//			this.msg = RESULT_ERROR;
//			log.error("获取'分析》违规终端'列表数据时报错！", e);
//		}
//		return "analyzeTerminalTable";
//	}
//	/**
//	 * 分析》违规终端图表页面
//	 */
//	
//	public String analyzeTerminalGraph(){
//		return "analyzeTerminalGraph";
//	}
//	/**
//	 * 分析》违规单位
//	 */
// 	public String analyzeUnit() {
// 		return "analyzeUnit";
//	}
//	/**
//	 * 分析》违规单位列表
//	 */
//	@SuppressWarnings("unchecked")
//	public String analyzeUnitTable(){
//		try {
//			int recentDays = (null != getRequest().getParameter(PARAM_RANGE))?
//					Integer.valueOf(getRequest().getParameter(PARAM_RANGE)):
//						PARAM_DEFAULT_VALUE; 
//			getRequest().setAttribute("recentDays", recentDays);
//			//有选择地抓取数据，实现分页
//			List<ViolationBean> totalList = violationEventService.unitByTimeList(recentDays);
//			List pageList = new ArrayList();
//			for (int i = (getPageNum()-1)*getPageSize(); i < totalList.size() && i < getPageNum()*getPageSize(); i++) {
//				pageList.add(totalList.get(i));
//			}
//			pageView = new PageViewUtil(getPageNum(), getPageSize(), totalList.size(), pageList);
//			
//			this.msg = RESULT_SUCCESS;
//		} catch (Exception e) {
//			// TODO: handle exception
//			this.msg = RESULT_ERROR;
//			log.error("获取'分析》违规单位'列表数据时报错！", e);
//		}
//		return "analyzeUnitTable";
//	}
//	
//	public String analyzeUnitPort(){
//		return "analyzeUnitPort";
//	}
//	
//	public String analyzeUnitTerminal(){
//		return "analyzeUnitTerminal";
//	}
//	/**
//	 * 分析》违规列表
//	 */
//	public String analyzeList() {
//		try {
//			//组装hql
//			HQLBuilderUtil hqlUtil = new HQLBuilderUtil(ViolationEvent.class);
//			if(violation!=null){
//				if( StringUtils.isNotBlank( violation.getTerminal().getUserName() )){
//					hqlUtil.addWhereClause(" this.terminal.userName like ? ", "%"+StringUtils.trim( violation.getTerminal().getUserName() )+"%" );
//				}
//				if( StringUtils.isNotBlank( violation.getTerminal().getArea().getName())){
//					hqlUtil.addWhereClause(" this.terminal.area.name like ? ", "%"+StringUtils.trim( violation.getTerminal().getArea().getName() )+"%" );
//				}
//				if( StringUtils.isNotBlank( violation.getViolationPort())){
//					hqlUtil.addWhereClause(" this.violationPort like ? ", "%"+StringUtils.trim( violation.getViolationPort() )+"%" );
//				}
//				if( StringUtils.isNotBlank( violation.getTerminal().getIpAddress())){
//					hqlUtil.addWhereClause(" this.terminal.ipAddress like ? ", "%"+StringUtils.trim( violation.getTerminal().getIpAddress() )+"%" );
//				}
//				if( StringUtils.isNotBlank( violation.getTerminal().getName())){
//					hqlUtil.addWhereClause(" this.terminal.name like ? ", "%"+StringUtils.trim( violation.getTerminal().getName() )+"%" );
//				}
//			}
//			pageView = violationEventService.getPageView(hqlUtil,
//					getPageNum(), getPageSize());
//			this.msg = RESULT_SUCCESS;
//		} catch (Exception e) {
//			// TODO: handle exception
//			this.msg = RESULT_ERROR;
//			log.error("获取'分析》违规列表'数据时报错！", e);
//		}
//		
//		return "analyzeList";
//	}
//	//属性方法
//	public ViolationEvent getViolation() {
//		return violation;
//	}
//	public void setViolation(ViolationEvent violation) {
//		this.violation = violation;
//	}
//}
