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
// *         说      明：违规统计Service层接口
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2013-3-7 上午07:49:14 
// */
//public interface StatisticsService extends BaseService<ViolationEvent> {
//	/**
//	 * 获得最早的违规时间
//	 * 
//	 * @return int 年月日的整数形式
//	 */
//	int getMinYMD();
//	
//	/**
//	 * 获得最晚的违规时间
//	 * 
//	 * @return int 年月日的整数形式
//	 */
//	int getMaxYMD();
//	
//	/**
//	 * startYMD ～  endYMD 时间段内，违规端口随时间的变化
//	 * 
//	 * @param startYMD 开始时间点
//	 * @param endYMD 结束时间点
//	 * @return List<AmChart> AmChart图表数据
//	 */
//	List<AmChart> portByTime(int startYMD, int endYMD);
//	
//	/**
//	 * startYMD ～  endYMD 时间段内，违规终端随时间的变化
//	 * 
//	 * @param startYMD 开始时间点
//	 * @param endYMD 结束时间点
//	 * @return List<AmChart> AmChart图表数据
//	 */
//	List<AmChart> deviceByTime(int startYMD, int endYMD);
//	
//	/**
//	 * startYMD ～  endYMD 时间段内，不同单位之间违规端口数的横向对比
//	 * 
//	 * @param startYMD 开始时间点
//	 * @param endYMD 结束时间点
//	 * @return List<AmChart> AmChart图表数据
//	 */
//	List<AmChart> portByDepartment(int startYMD, int endYMD);
//	
//	/**
//	 * startYMD ～  endYMD 时间段内，不同单位之间违规设备数的横向对比
//	 * 
//	 * @param startYMD 开始时间点
//	 * @param endYMD 结束时间点
//	 * @return List<AmChart> AmChart图表数据
//	 */
//	List<AmChart> deviceByDepartment(int startYMD, int endYMD);
//	
//	/**
//	 * startYMD ～  endYMD 时间段内，违规端口随时间的变化
//	 * 
//	 * @param startYMD 开始时间点
//	 * @param endYMD 结束时间点
//	 * @return List<ViolationBean> 列表数据
//	 */
//	List<ViolationBean> portByTimeData(int startYMD, int endYMD);
//	
//	/**
//	 * startYMD ～  endYMD 时间段内，违规终端随时间的变化
//	 * 
//	 * @param startYMD 开始时间点
//	 * @param endYMD 结束时间点
//	 * @return List<ViolationBean> 列表数据
//	 */
//	List<ViolationBean> deviceByTimeData(int startYMD, int endYMD);
//	
//	/**
//	 * startYMD ～  endYMD 时间段内，不同单位之间违规设备数和违规端口数的横向对比
//	 * 
//	 * @param startYMD 开始时间点
//	 * @param endYMD 结束时间点
//	 * @return List<ViolationBean> 列表数据
//	 */
//	List<ViolationBean> departmentByTimeData(int startYMD, int endYMD);
//}
