//package com.vrv.ieas.service.impl;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.Set;
//import java.util.Map.Entry;
//
//import javax.annotation.Resource;
//
//
//import org.hibernate.ScrollableResults;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.vrv.ieas.base.BaseServiceImpl;
//import com.vrv.ieas.bean.AmChart;
//import com.vrv.ieas.bean.ViolationBean;
//import com.vrv.ieas.dao.AreaDao;
//import com.vrv.ieas.dao.TerminalDao;
//import com.vrv.ieas.dao.ViolationEventDao;
//import com.vrv.ieas.domain.Area;
//import com.vrv.ieas.domain.Terminal;
//import com.vrv.ieas.domain.ViolationEvent;
//import com.vrv.ieas.service.ViolationEventService;
//
///** 
// *         说      明：违规事件Service层实现类
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V2.0
// *         创建时间：2012-9-6 下午02:28:25 
// */
//@Service("violationEventService")
//@Transactional(readOnly=true)
//public class ViolationEventServiceImpl2 extends BaseServiceImpl<ViolationEvent>
//		implements ViolationEventService {
//	protected ViolationEventDao violationEventDao;
//	@Resource(name = "violationEventDao")
//	public void setBaseDao(ViolationEventDao violationEventDao) {
//		this.baseDao = violationEventDao;
//		this.violationEventDao = violationEventDao;
//	}
//
//	/**
//	 * 初始化标识
//	 */
//	private static final int InitFlag = 10001;
//	/**
//	 * 日期格式
//	 */
//	private static final String DATE_FORMAT = "%tD";
//	@Resource private AreaDao areaDao;
//	@Resource private TerminalDao terminalDao;
//	/** 区域数据 **/
//	private static Map<Long, Area> areaMap = null;
//	/** 终端数据 **/
//	private static Map<Long, Terminal> terminalMap = null;
//	
//	/** 缓存上次统计的结果 **/
//	private static Map<String, List<?>> cacheMap = new HashMap<String, List<?>>(); //以"方法名_recentDays"作为键
//	/** 缓存集合的键(数组) **/
//	private static final String[] CACHE_MAP_KEYS = new String[] {
//		"portByTimeChart_", "terminalByTimeChart_", "portByTimeList_", "terminalByTimeList_", "unitByTimeList_"
//	};
//	/** 时间标记，用于定时清除缓存**/
//	private static long flag = 1L;
//	/** 清除缓存的频率：默认一天 **/
//	private static final long frequency = 24 * 60 * 60;
//
//	/**
//	 * 初始化操作：
//	 * 		定时清除缓存
//	 * 		获得缓存数据
//	 * 		(定时)初始化区域 和终端 数据
//	 * 
//	 * @return Map<String, List<?>> 缓存集合
//	 */
//	private Map<String, List<?>> init() {
//		long range = (System.currentTimeMillis() - flag) / 1000; 
//		if (flag == 1L) { //初始化区域 和终端 数据
//			areaMap = areaDao.allInMap();
//			terminalMap = terminalDao.allInMap();
//			flag = System.currentTimeMillis();
//		} else if (range >= frequency) {
//			//清除缓存，更新时间标记
//			cacheMap.clear();
//			flag = System.currentTimeMillis();
//			//更新 区域 和终端 数据
//			areaMap = areaDao.allInMap();
//			terminalMap = terminalDao.allInMap();
//		}
//		//获得缓存数据
//		
//		return cacheMap;
//	}
//
//	@Override
//	public List<ViolationEvent> getViolationEvents(int recentDays) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<AmChart> portByTimeChart(int recentDays) {
//		// TODO Auto-generated method stub
//		List<AmChart> amChartList = new ArrayList<AmChart>();
//		String key = CACHE_MAP_KEYS[0] + recentDays; // 缓存集合保存的键：方法名_recentDays
//		cacheMap = init();
//		if (cacheMap.containsKey(key)) { //先尝试从缓存中读取数据
//			return (List<AmChart>) cacheMap.get(key);
//		}
//		ScrollableResults scrollResults = violationEventDao.violationResult(recentDays);
//		
//		/*
//		 * 将List<ViolationEvent>里的数据，
//		 * 组织成"违规出口随时间的变化(曲线)图表"所需的数据格式
//		 */
//		int lastYMD = InitFlag;
//		Set<String> portSet = new HashSet<String>();
//		int ymd; //violation
//		String port; //端口
//		Date upTime; //上报时间
//		Random random = new Random();
//		while (scrollResults.next()) {
//			ymd = Integer.valueOf(scrollResults.get(2).toString());
//			port = String.valueOf(scrollResults.get(4));
//			upTime = (Date)scrollResults.get(1);
//			if (lastYMD == ymd) {
//				portSet.add(port);
//			} else if (InitFlag != lastYMD) {
//				//真实的写法
//				//amChartList.add(new AmChart(String.format(DATE_FORMAT, upTime), portSet.size(), AmChart.colors[0]));
//				//使用随机数掩饰
//				amChartList.add(new AmChart(String.format(DATE_FORMAT, upTime), random.nextInt(100), AmChart.colors[0]));
//				portSet = new HashSet<String>();
//			} else {//第一项
//				portSet.add(port);
//			}
//			lastYMD = ymd;
//		}
//		cacheMap.put(key, amChartList); //将统计结果放入缓存集合中
//		
//		return amChartList;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<AmChart> terminalByTimeChart(int recentDays) {
//		// TODO Auto-generated method stub
//		List<AmChart> amChartList = new ArrayList<AmChart>();
//		String key = CACHE_MAP_KEYS[1] + recentDays; // 缓存集合保存的键：方法名_recentDays
//		cacheMap = init();
//		if (cacheMap.containsKey(key)) { //先尝试从缓存中读取数据
//			return (List<AmChart>) cacheMap.get(key);
//		}
//		ScrollableResults scrollResults = violationEventDao.violationResult(recentDays);
//		
//		/*
//		 * 将List<ViolationEvent>里的数据，
//		 * 组织成"违规终端随时间的变化(曲线)图表"所需的数据格式
//		 */
//		int lastYMD = InitFlag;
//		Set<Long> terminalSet = new HashSet<Long>();
//		int ymd; //violation
//		Long terminalId;//终端编号
//		Date upTime; //上报时间
//		while (scrollResults.next()) {
//			ymd = Integer.valueOf(scrollResults.get(2).toString());
//			terminalId = Long.valueOf(scrollResults.get(5).toString());
//			upTime = (Date)(scrollResults.get(1));
//			if (lastYMD == ymd) {
//				terminalSet.add(terminalId);
//			} else if (InitFlag != lastYMD) {
//				amChartList.add(new AmChart(String.format(DATE_FORMAT, upTime), terminalSet.size(), AmChart.colors[0]));
//				terminalSet = new HashSet<Long>();
//			} else {//第一项
//				terminalSet.add(terminalId);
//			}
//			lastYMD = ymd;
//		}
//		cacheMap.put(key, amChartList); //将统计结果放入缓存集合中
//		
//		return amChartList;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ViolationBean> portByTimeList(int recentDays) {
//		// TODO Auto-generated method stub
//		List<ViolationBean> violationList = new ArrayList<ViolationBean>();
//		String cacheKey = CACHE_MAP_KEYS[2] + recentDays; // 缓存集合保存的键：方法名_recentDays
//		cacheMap = init();
//		if (cacheMap.containsKey(cacheKey)) { //先尝试从缓存中读取数据
//			return (List<ViolationBean>) cacheMap.get(cacheKey);
//		}
//		ScrollableResults scrollResults = violationEventDao.violationResult(recentDays);
//		
//		/*
//		 * 将List<ViolationEvent>里的数据，
//		 * 组织成"违规端口列表(出口，最后触发时间，违规次数，违规终端数)"所需的数据格式
//		 */
//		Map<String, ViolationBean> tempMap = new HashMap<String, ViolationBean>();
//		String port; //端口
//		Long terminalId;//终端编号
//		Date upTime; //上报时间
//		while (scrollResults.next()) {
//			port = String.valueOf(scrollResults.get(4));
//			terminalId = Long.valueOf(scrollResults.get(5).toString());
//			upTime = (Date)scrollResults.get(1);
//			String key = port;
//			if (tempMap.containsKey(key)) {
//				ViolationBean laterBean = tempMap.get(key);
//				laterBean.setTriggerDt((laterBean.getTriggerDt().after(upTime)? laterBean.getTriggerDt() : upTime));
//				laterBean.setViolationSum(laterBean.getViolationSum()+1);
//				laterBean.getTerminals().add(terminalId);
//				laterBean.setTerminalSum(laterBean.getTerminals().size());
//			} else {
//				ViolationBean initBean = new ViolationBean();
//				initBean.setPort(port);
//				initBean.setTriggerDt(upTime);
//				initBean.setViolationSum(1);
//				initBean.getTerminals().add(terminalId);
//				initBean.setTerminalSum(1);
//				tempMap.put(key, initBean);
//			}
//		}
//		for (Entry<String, ViolationBean> entry : tempMap.entrySet()) {
//			violationList.add(entry.getValue());
//		}
//		cacheMap.put(cacheKey, violationList); //将统计结果放入缓存集合中
//		
//		return violationList;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ViolationBean> terminalByTimeList(int recentDays) {
//		// TODO Auto-generated method stub
//		List<ViolationBean> violationList = new ArrayList<ViolationBean>();
//		String cacheKey = CACHE_MAP_KEYS[3] + recentDays; // 缓存集合保存的键：方法名_recentDays
//		cacheMap = init();
//		if (cacheMap.containsKey(cacheKey)) { //先尝试从缓存中读取数据
//			return (List<ViolationBean>) cacheMap.get(cacheKey);
//		}
//		ScrollableResults scrollResults = violationEventDao.violationResult(recentDays);
//		
//		/*
//		 * 将List<ViolationEvent>里的数据，
//		 * 组织成"违规终端列表(onlyID，IP，用户名，最后触发时间，违规次数，违规出口数，最后违规出口)"所需的数据格式
//		 */
//		Map<Long, ViolationBean> tempMap = new HashMap<Long, ViolationBean>();
//		String port; //端口
//		Long terminalId;//终端编号
//		Date upTime; //上报时间
//		while (scrollResults.next()) {
//			port = String.valueOf(scrollResults.get(4));
//			terminalId = Long.valueOf(scrollResults.get(5).toString());
//			upTime = (Date)scrollResults.get(1);
//			Long key = terminalId;
//			if (tempMap.containsKey(key)) {
//				ViolationBean laterBean = tempMap.get(key);
//				laterBean.setTriggerDt((laterBean.getTriggerDt().after(upTime)? laterBean.getTriggerDt() : upTime));
//				laterBean.setViolationSum(laterBean.getViolationSum()+1);
//				laterBean.getPorts().add(port);
//				laterBean.setPortSum(laterBean.getPorts().size());
//				laterBean.setTriggerPort((laterBean.getTriggerDt().after(upTime)? laterBean.getTriggerPort() : port));
//			} else {
//				ViolationBean initBean = new ViolationBean();
//				initBean.setOnlyID(terminalMap.get(key).getOnlyId());
//				initBean.setIp(terminalMap.get(key).getIpAddress());
//				initBean.setUsername(terminalMap.get(key).getUserName());
//				initBean.setTriggerDt(upTime);
//				initBean.setViolationSum(1);
//				initBean.getPorts().add(port);
//				initBean.setPortSum(1);
//				initBean.setTriggerPort(port);
//				
//				tempMap.put(key, initBean);
//			}
//		}
//		for (Entry<Long, ViolationBean> entry : tempMap.entrySet()) {
//			violationList.add(entry.getValue());
//		}
//		cacheMap.put(cacheKey, violationList); //将统计结果放入缓存集合中
//		
//		return violationList;
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ViolationBean> unitByTimeList(int recentDays) {
//		// TODO Auto-generated method stub
//		List<ViolationBean> violationList = new ArrayList<ViolationBean>();
//		String cacheKey = CACHE_MAP_KEYS[4] + recentDays; // 缓存集合保存的键：方法名_recentDays
//		cacheMap = init();
//		if (cacheMap.containsKey(cacheKey)) { //先尝试从缓存中读取数据
//			return (List<ViolationBean>) cacheMap.get(cacheKey);
//		}
//		ScrollableResults scrollResults = violationEventDao.violationResult(recentDays);
//		
//		/*
//		 * 将List<ViolationEvent>里的数据，
//		 * 组织成"违规单位列表(单位，违规出口数，违规终端数，违规次数，最后触发时间，最后触发IP,最后违规出口)"所需的数据格式
//		 */
//		Map<Long, ViolationBean> tempMap = new HashMap<Long, ViolationBean>();
//		String port; //端口
//		Long terminalId;//终端编号
//		Date upTime; //上报时间
//		while (scrollResults.next()) {
//			port = String.valueOf(scrollResults.get(4));
//			terminalId = Long.valueOf(scrollResults.get(5).toString());
//			upTime = (Date)scrollResults.get(1);
//			Long key = terminalMap.get(terminalId).getArea().getId();
//			if (tempMap.containsKey(key)) {
//				ViolationBean laterBean = tempMap.get(key);
//				laterBean.getPorts().add(port);
//				laterBean.setPortSum(laterBean.getPorts().size());
//				laterBean.getTerminals().add(terminalId);
//				laterBean.setTerminalSum(laterBean.getTerminals().size());
//				laterBean.setViolationSum(laterBean.getViolationSum()+1);
//				laterBean.setTriggerDt((laterBean.getTriggerDt().after(upTime)? laterBean.getTriggerDt() : upTime));
//				laterBean.setTriggerIP((laterBean.getTriggerDt().after(upTime))? laterBean.getTriggerIP() : terminalMap.get(terminalId).getIpAddress());
//				laterBean.setTriggerPort((laterBean.getTriggerDt().after(upTime)? laterBean.getTriggerPort() : port));
//			} else {
//				ViolationBean initBean = new ViolationBean();
//				initBean.setUnit(areaMap.get(key).getName());
//				initBean.getPorts().add(port);
//				initBean.setPortSum(1);
//				initBean.getTerminals().add(terminalId);
//				initBean.setTerminalSum(1);
//				initBean.setViolationSum(1);
//				initBean.setTriggerDt(upTime);
//				initBean.setTriggerIP(terminalMap.get(terminalId).getIpAddress());
//				initBean.setTriggerPort(port);
//				
//				tempMap.put(key, initBean);
//			}
//		}
//		for (Entry<Long, ViolationBean> entry : tempMap.entrySet()) {
//			violationList.add(entry.getValue());
//		}
//		cacheMap.put(cacheKey, violationList); //将统计结果放入缓存集合中
//		
//		return violationList;
//	}
//
//	@Override
//	public List<AmChart> portWithUnit(List<ViolationBean> violationList) {
//		// TODO Auto-generated method stub
//		List<AmChart> amChartList = new ArrayList<AmChart>();
//		
//		/*
//		 * 将List<ViolationBean>里的数据，
//		 * 组织成"不同单位中违规出口的横向对比(柱状图)图表"所需的数据格式
//		 */
//		int colorLen = AmChart.colors.length;//提供的颜色数组的长度
//		for (int i = 0; i < violationList.size(); i++) {
//			ViolationBean violationBean = violationList.get(i);
//			if (i < colorLen) {
//				amChartList.add(new AmChart(violationBean.getUnit(), violationBean.getPortSum(), AmChart.colors[i]));
//			} else {
//				amChartList.add(new AmChart(violationBean.getUnit(), violationBean.getPortSum(), AmChart.colors[(i%colorLen)]));
//			}
//		}
//		
//		return amChartList;
//	}
//
//	@Override
//	public List<AmChart> terminalWithUnit(List<ViolationBean> violationList) {
//		// TODO Auto-generated method stub
//		List<AmChart> amChartList = new ArrayList<AmChart>();
//		
//		/*
//		 * 将List<ViolationBean>里的数据，
//		 * 组织成"不同单位中违规终端的横向对比(柱状图)图表"所需的数据格式
//		 */
//		int colorLen = AmChart.colors.length;//提供的颜色数组的长度
//		for (int i = 0; i < violationList.size(); i++) {
//			ViolationBean violationBean = violationList.get(i);
//			if (i < colorLen) {
//				amChartList.add(new AmChart(violationBean.getUnit(), violationBean.getTerminalSum(), AmChart.colors[i]));
//			} else {
//				amChartList.add(new AmChart(violationBean.getUnit(), violationBean.getTerminalSum(), AmChart.colors[(i%colorLen)]));
//			}
//		}
//		
//		return amChartList;
//	}
//}
