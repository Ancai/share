//package com.vrv.ieas.service.impl;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.vrv.ieas.base.BaseServiceImpl;
//import com.vrv.ieas.bean.AmChart;
//import com.vrv.ieas.bean.ViolationBean;
//import com.vrv.ieas.dao.ViolationEventDao;
//import com.vrv.ieas.domain.ViolationEvent;
//import com.vrv.ieas.service.StatisticsService;
//import com.vrv.ieas.utils.CoreUtil;
//import com.vrv.ieas.utils.IEASUtil;
//
///** 
// *         说      明：违规统计Service层实现类
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2013-3-7 上午07:53:38 
// */
//@Service("statisticsService")
//public class StaticsServiceImpl extends BaseServiceImpl<ViolationEvent>
//		implements StatisticsService {
//	private ViolationEventDao violationEventDao;
//	@Resource
//	public void setBaseDao(ViolationEventDao violationEventDao) {
//		this.violationEventDao = violationEventDao;
//		this.baseDao = violationEventDao;
//	}
//
//	@Override
//	public int getMinYMD() {
//		// TODO Auto-generated method stub
//		int minYMD = IEASUtil.getDefaultYMD();
//		String sql = " SELECT MIN(YMD) AS minYmd FROM vrv_violationevent ";
//		List<Object[]> resultList = this.violationEventDao.queryBySql(sql);
//		if (CoreUtil.notNull(resultList) && !resultList.isEmpty()) {
//			if (CoreUtil.notNull(resultList.get(0))) {
//				minYMD = CoreUtil.parseInt(resultList.get(0));
//			}
//		}
//		
//		return minYMD;
//	}
//
//	@Override
//	public int getMaxYMD() {
//		// TODO Auto-generated method stub
//		int maxYMD = IEASUtil.getDefaultYMD();
//		String sql = " SELECT MAX(YMD) AS maxYmd FROM vrv_violationevent ";
//		List<Object[]> resultList = this.violationEventDao.queryBySql(sql);
//		if (CoreUtil.notNull(resultList) && !resultList.isEmpty()) {
//			if (CoreUtil.notNull(resultList.get(0))) {
//				maxYMD = CoreUtil.parseInt(resultList.get(0));
//			}
//		}
//		
//		return maxYMD;
//	}
//
//	@Override
//	public List<AmChart> portByTime(int startYMD, int endYMD) {
//		// TODO Auto-generated method stub
//		List<AmChart> amchartList = new ArrayList<AmChart>();
//		String sql = " SELECT COUNT(DISTINCT VIOLATIONPORT) AS portNums, YMD FROM vrv_violationevent WHERE YMD BETWEEN ? AND ? GROUP BY YMD ";
//		List<Object[]> resultList = this.violationEventDao.queryBySql(sql, startYMD, endYMD);
//		for (Object[] objects : resultList) {
//			amchartList.add(new AmChart(IEASUtil.ymd2TimeLabel(objects[1]), CoreUtil.parseInt(objects[0]), ""));
//		}
//		
//		return amchartList;
//	}
//
//	@Override
//	public List<AmChart> deviceByTime(int startYMD, int endYMD) {
//		// TODO Auto-generated method stub
//		List<AmChart> amchartList = new ArrayList<AmChart>();
//		String sql = " SELECT COUNT(DISTINCT DEVICEID) AS deviceNums, YMD FROM vrv_violationevent WHERE YMD BETWEEN ? AND ? GROUP BY  YMD ";
//		List<Object[]> resultList = this.violationEventDao.queryBySql(sql, startYMD, endYMD);
//		for (Object[] objects : resultList) {
//			amchartList.add(new AmChart(IEASUtil.ymd2TimeLabel(objects[1]), CoreUtil.parseInt(objects[0]), ""));
//		}
//		
//		return amchartList;
//	}
//
//	@Override
//	public List<AmChart> portByDepartment(int startYMD, int endYMD) {
//		// TODO Auto-generated method stub
//		List<AmChart> amchartList = new ArrayList<AmChart>();
//		String sql = " SELECT COUNT(DISTINCT VIOLATIONPORT) AS portNums, MIN(AREANAME), AREAID FROM vrv_violationevent WHERE YMD BETWEEN ? AND ? GROUP BY  AREAID ";
//		List<Object[]> resultList = this.violationEventDao.queryBySql(sql, startYMD, endYMD);
//		for (int i = 0; i < resultList.size(); i++) {
//			Object[] objects = resultList.get(i);
//			amchartList.add(new AmChart(objects[1].toString(), CoreUtil.parseInt(objects[0]),
//					i < AmChart.colors.length ? AmChart.colors[i] : AmChart.colors[i%AmChart.colors.length], objects[2].toString()));
//		}
//		
//		return amchartList;
//	}
//
//	@Override
//	public List<AmChart> deviceByDepartment(int startYMD, int endYMD) {
//		// TODO Auto-generated method stub
//		List<AmChart> amchartList = new ArrayList<AmChart>();
//		String sql = " SELECT COUNT(DISTINCT DEVICEID) AS deviceNums, MIN(AREANAME), AREAID FROM vrv_violationevent WHERE YMD BETWEEN ? AND ? GROUP BY  AREAID ";
//		List<Object[]> resultList = this.violationEventDao.queryBySql(sql, startYMD, endYMD);
//		for (int i = 0; i < resultList.size(); i++) {
//			Object[] objects = resultList.get(i);
//			amchartList.add(new AmChart(objects[1].toString(), CoreUtil.parseInt(objects[0]),
//					i < AmChart.colors.length ? AmChart.colors[i] : AmChart.colors[i%AmChart.colors.length], objects[2].toString()));
//		}
//		
//		return amchartList;
//	}
//
//	@Override
//	public List<ViolationBean> portByTimeData(int startYMD, int endYMD) {
//		// TODO Auto-generated method stub
//		List<ViolationBean> violationBeanList = new ArrayList<ViolationBean>();
//		String sql = " SELECT VIOLATIONPORT, MAX(UPTIME) AS maxUPTIME, COUNT(1) AS violationNums, COUNT(DISTINCT DEVICEID) AS deviceNums FROM vrv_violationevent WHERE YMD BETWEEN ? AND ? GROUP BY VIOLATIONPORT ";
//		List<Object[]> resultList = this.violationEventDao.queryBySql(sql, startYMD, endYMD);
//		for (Object[] objects : resultList) {
//			ViolationBean violationBean = new ViolationBean();
//			violationBean.setPort(CoreUtil.stringValue(objects[0]));
//			violationBean.setTriggerDt((Date) objects[1]);
//			violationBean.setViolationSum(IEASUtil.sqlConutValue(objects[2]));
//			violationBean.setTerminalSum(CoreUtil.parseInt(objects[3]));
//			violationBeanList.add(violationBean);
//		}
//		
//		return violationBeanList;
//	}
//
//	@Override
//	public List<ViolationBean> deviceByTimeData(int startYMD, int endYMD) {
//		// TODO Auto-generated method stub
//		List<ViolationBean> violationBeanList = new ArrayList<ViolationBean>();
//		String sql = " SELECT MIN(DEVICENAME) AS minDeviceName, MIN(ip) AS minIP, MIN(USERNAME) AS minUsername, MAX(UPTIME) AS maxUptime, COUNT(1) AS violationNums, COUNT(DISTINCT VIOLATIONPORT) portNums FROM vrv_violationevent WHERE YMD BETWEEN ? AND ? GROUP BY DEVICEID ";
//		List<Object[]> resultList = this.violationEventDao.queryBySql(sql, startYMD, endYMD);
//		for (Object[] objects : resultList) {
//			ViolationBean violationBean = new ViolationBean();
//			violationBean.setTerminalName(CoreUtil.stringValue(objects[0]));
//			violationBean.setIp(CoreUtil.stringValue(objects[1]));
//			violationBean.setUsername(CoreUtil.stringValue(objects[2]));
//			violationBean.setTriggerDt((Date) objects[3]);
//			violationBean.setViolationSum(IEASUtil.sqlConutValue(objects[4]));
//			violationBean.setPortSum(CoreUtil.parseInt(objects[5]));
//			//violationBean.setTriggerPort(CoreUtil.stringValue(objects[6]));
//			violationBeanList.add(violationBean);
//		}
//		
//		return violationBeanList;
//	}
//
//	@Override
//	public List<ViolationBean> departmentByTimeData(int startYMD, int endYMD) {
//		// TODO Auto-generated method stub
//		List<ViolationBean> violationBeanList = new ArrayList<ViolationBean>();
//		String sql = " SELECT MIN(AREANAME) AS minAreaName, COUNT(DISTINCT VIOLATIONPORT) AS portNums, COUNT(DISTINCT DEVICEID) AS deviceNums, COUNT(1) AS violationNums, MAX(UPTIME) AS maxUptime FROM vrv_violationevent WHERE YMD BETWEEN ? AND ? GROUP BY  AREAID ";
//		List<Object[]> resultList = this.violationEventDao.queryBySql(sql, startYMD, endYMD);
//		for (Object[] objects : resultList) {
//			ViolationBean violationBean = new ViolationBean();
//			violationBean.setUnit(CoreUtil.stringValue(objects[0]));
//			violationBean.setPortSum(CoreUtil.parseInt(objects[1]));
//			violationBean.setTerminalSum(CoreUtil.parseInt(objects[2]));
//			violationBean.setViolationSum(IEASUtil.sqlConutValue(objects[3]));
//			violationBean.setTriggerDt((Date) objects[4]);
//			violationBeanList.add(violationBean);
//		}
//		
//		return violationBeanList;
//	}
//
//}
