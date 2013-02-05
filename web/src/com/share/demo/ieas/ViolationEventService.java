//package com.vrv.ieas.service;
//
//import java.util.List;
//
//import com.vrv.ieas.base.BaseService;
//import com.vrv.ieas.bean.AmChart;
//import com.vrv.ieas.bean.ViolationBean;
//import com.vrv.ieas.domain.ViolationEvent;
//
///** 
// *         说      明：违规事件Service层接口
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2012-8-8 上午11:37:16 
// */
//public interface ViolationEventService extends BaseService<ViolationEvent> {
//	/**
//	 * 得到最近一段时间(recentDt -- > now)的违规事件列表
//	 * 
//	 * @param recentDays 最近天数
//	 * @return List<ViolationEvent> 违规事件
//	 */
//	public List<ViolationEvent> getViolationEvents(int recentDays);
//	
//	/**
//	 * 违规出口随时间的变化(曲线)图表数据
//	 * 
//	 * @param recentDays 最近天数
//	 * @return List<AmChart> 图表数据
//	 */
//	public List<AmChart> portByTimeChart(int recentDays);
//	
//	/**
//	 * 违规终端随时间的变化(曲线)图表数据
//	 * 
//	 * @param recentDays 最近天数
//	 * @return List<Amchart> 图表数据
//	 */
//	public List<AmChart> terminalByTimeChart(int recentDays);
//	
//	/**
//	 * 违规出口随时间的变化(曲线)列表数据
//	 * 
//	 * @param recentDays 最近天数
//	 * @return List<ViolationBean> 列表数据
//	 */
//	public List<ViolationBean> portByTimeList(int recentDays);
//	
//	/**
//	 * 违规终端随时间的变化(曲线)列表数据
//	 * 
//	 * @param recentDays 最近天数
//	 * @return List<ViolationBean> 列表数据
//	 */
//	public List<ViolationBean> terminalByTimeList(int recentDays);
//	
//	/**
//	 * 违规单位随时间的变化(柱状)列表数据
//	 * 
//	 * @param recentDays 最近天数
//	 * @return 列表数据
//	 */
//	public List<ViolationBean> unitByTimeList(int recentDays);
//	
//	/**
//	 * 不同单位中违规出口的横向对比(柱状图)图表数据
//	 * 
//	 * @param violationList 违规列表数据集合
//	 * @return 图表数据
//	 */
//	public List<AmChart> portWithUnit(List<ViolationBean> violationList);
//	
//	/**
//	 * 不同单位中违规终端的横向对比(柱状图)图表数据
//	 * 
//	 * @param violationList 违规列表数据集合
//	 * @return 图表数据
//	 */
//	public List<AmChart> terminalWithUnit(List<ViolationBean> violationList);
//}
