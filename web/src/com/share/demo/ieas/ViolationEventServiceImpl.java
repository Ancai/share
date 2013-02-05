//package com.vrv.ieas.service.impl;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.Map.Entry;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang.time.DateUtils;
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
//import com.vrv.ieas.utils.DateTypeConverterUtil;
//
///** 
// *         说      明：违规事件Service层实现类
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2012-8-28 下午02:21:59 
// */
//public class ViolationEventServiceImpl extends BaseServiceImpl<ViolationEvent>
//		implements ViolationEventService {
//
//	protected ViolationEventDao violationEventDao;
//	@Resource(name = "violationEventDao")
//	public void setBaseDao(ViolationEventDao violationEventDao) {
//		this.baseDao = violationEventDao;
//		this.violationEventDao = violationEventDao;
//	}
//	@Resource(name = "terminalDao")
//	private TerminalDao terminalDao;
//	@Resource(name = "areaDao")
//	private AreaDao areaDao;
//
//	/**
//	 * 初始化标识
//	 */
//	private static final int InitFlag = 10001;
//	/**
//	 * 日期格式
//	 */
//	private static final String DATE_FORMAT = "%tD";
//	private static final String DATE_YMD = "yyyyMMdd";
//	
//	/**
//	 * 查询的违规记录
//	 */
//	private static List<ViolationEvent> queryList = new ArrayList<ViolationEvent>(50000);
//	/**
//	 * 上次访问传递的日期参数
//	 */
//	private int lastDtParam = 0;
//	
//	/**
//	 * "违规事件"引用属性(Terminal,Area),首次查询出，
//	 * 			避免以后多次从数据库
//	 */
//	private static List<Terminal> terminalList;
//	private static List<Area> areaList;
//	
//
//	/* (non-Javadoc)
//	 * @see com.vrv.ieas.service.ViolationEventService#getViolationEvents(int)
//	 */
//	@Override
//	public List<ViolationEvent> getViolationEvents(int recentDays) {
//		// TODO Auto-generated method stub
//		areaList = (null != areaList) ? areaList : this.areaDao.queryAll();
//		terminalList = (null != terminalList) ? terminalList : this.terminalDao.queryAll();
//		if (this.lastDtParam == recentDays && queryList.size() > 0) {
//			return queryList;
//		} else {
//			int maxYMD = this.violationEventDao.getMaxYMD(); //数据库中最新的违规记录日期（yyyyMMdd的整数形式）
//			Date startDate = DateUtils.addDays(DateTypeConverterUtil.converterString2Util(String.valueOf(maxYMD), DATE_YMD), - recentDays);
//			int startYMD = Integer.valueOf(DateTypeConverterUtil.converterUtil2String(startDate, DATE_YMD));
//			queryList.clear();
//			queryList = violationEventDao.getListCache(Integer.valueOf(startYMD) , maxYMD);
//			this.lastDtParam = recentDays;
//			
//			return queryList;
//		}
//	}
//
//	@Override
//	public List<AmChart> portByTimeChart(int recentDays) {
//		// TODO Auto-generated method stub
//		List<AmChart> amChartList = new ArrayList<AmChart>();
//		List<ViolationEvent> violationEvents = getViolationEvents(recentDays);
//		
//		/*
//		 * 将List<ViolationEvent>里的数据，
//		 * 组织成"违规出口随时间的变化(曲线)图表"所需的数据格式
//		 */
//		int lastYMD = InitFlag;
//		Set<String> portSet = new HashSet<String>();
//		for (int i = 0; i < violationEvents.size(); i++) {
//			ViolationEvent violationEvent = violationEvents.get(i);
//			if (lastYMD == violationEvent.getYmd()) {
//				portSet.add(violationEvent.getViolationPort());
//			} else if (InitFlag != lastYMD) {
//				amChartList.add(new AmChart(String.format(DATE_FORMAT, violationEvent.getUpTime()), portSet.size(), AmChart.colors[0]));
//				portSet = new HashSet<String>();
//			} else {//第一项
//				portSet.add(violationEvent.getViolationPort());
//			}
//			lastYMD = violationEvent.getYmd();	
//		}
//		
//		return amChartList;
//	}
//
//	@Override
//	public List<AmChart> terminalByTimeChart(int recentDays) {
//		// TODO Auto-generated method stub
//		List<AmChart> amChartList = new ArrayList<AmChart>();
//		List<ViolationEvent> violationEvents = getViolationEvents(recentDays);
//		
//		/*
//		 * 将List<ViolationEvent>里的数据，
//		 * 组织成"违规终端随时间的变化(曲线)图表"所需的数据格式
//		 */
//		int lastYMD = InitFlag;
//		Set<String> terminalSet = new HashSet<String>();
//		for (int i = 0; i < violationEvents.size(); i++) {
//			ViolationEvent violationEvent = violationEvents.get(i);
//			if (lastYMD == violationEvent.getYmd()) {
//				terminalSet.add(violationEvent.getTerminal().getOnlyId());
//			} else if (InitFlag != lastYMD) {
//				amChartList.add(new AmChart(String.format(DATE_FORMAT, violationEvent.getUpTime()), terminalSet.size(), AmChart.colors[0]));
//				terminalSet = new HashSet<String>();
//			} else {//第一项
//				terminalSet.add(violationEvent.getTerminal().getOnlyId());
//			}
//			lastYMD = violationEvent.getYmd();	
//		}
//		
//		return amChartList;
//	}
//
//	@Override
//	public List<ViolationBean> portByTimeList(int recentDays) {
//		// TODO Auto-generated method stub
//		List<ViolationBean> violationList = new ArrayList<ViolationBean>();
//		List<ViolationEvent> violationEvents = getViolationEvents(recentDays);
//		
//		/*
//		 * 将List<ViolationEvent>里的数据，
//		 * 组织成"违规端口列表(出口，最后触发时间，违规次数，违规终端数)"所需的数据格式
//		 */
//		Map<String, ViolationBean> tempMap = new HashMap<String, ViolationBean>();
//		for (ViolationEvent violationEvent : violationEvents) {
//			String key = violationEvent.getViolationPort();
//			if (tempMap.containsKey(key)) {
//				ViolationBean laterBean = tempMap.get(key);
//				laterBean.setTriggerDt((laterBean.getTriggerDt().after(violationEvent.getClientTime())? laterBean.getTriggerDt() : violationEvent.getClientTime()));
//				laterBean.setViolationSum(laterBean.getViolationSum()+1);
//				laterBean.getTerminals().add(violationEvent.getTerminal().getId());
//				laterBean.setTerminalSum(laterBean.getTerminals().size());
//			} else {
//				ViolationBean initBean = new ViolationBean();
//				initBean.setPort(violationEvent.getViolationPort());
//				initBean.setTriggerDt(violationEvent.getClientTime());
//				initBean.setViolationSum(1);
//				initBean.getTerminals().add(violationEvent.getTerminal().getId());
//				initBean.setTerminalSum(1);
//				tempMap.put(key, initBean);
//			}
//		}
//		for (Entry<String, ViolationBean> entry : tempMap.entrySet()) {
//			violationList.add(entry.getValue());
//		}
//		
//		return violationList;
//	}
//
//	@Override
//	public List<ViolationBean> terminalByTimeList(int recentDays) {
//		// TODO Auto-generated method stub
//		List<ViolationBean> violationList = new ArrayList<ViolationBean>();
//		List<ViolationEvent> violationEvents = getViolationEvents(recentDays);
//		
//		/*
//		 * 将List<ViolationEvent>里的数据，
//		 * 组织成"违规终端列表(onlyID，IP，用户名，最后触发时间，违规次数，违规出口数，最后违规出口)"所需的数据格式
//		 */
//		Map<Long, ViolationBean> tempMap = new HashMap<Long, ViolationBean>();
//		for (ViolationEvent violationEvent : violationEvents) {
//			Long key = violationEvent.getTerminal().getId();
//			if (tempMap.containsKey(key)) {
//				ViolationBean laterBean = tempMap.get(key);
//				laterBean.setTriggerDt((laterBean.getTriggerDt().after(violationEvent.getClientTime())? laterBean.getTriggerDt() : violationEvent.getClientTime()));
//				laterBean.setViolationSum(laterBean.getViolationSum()+1);
//				laterBean.getPorts().add(violationEvent.getViolationPort());
//				laterBean.setPortSum(laterBean.getPorts().size());
//				laterBean.setTriggerPort((laterBean.getTriggerDt().after(violationEvent.getClientTime())? laterBean.getTriggerPort() : violationEvent.getViolationPort()));
//			} else {
//				ViolationBean initBean = new ViolationBean();
//				initBean.setOnlyID(violationEvent.getTerminal().getOnlyId());
//				initBean.setIp(violationEvent.getTerminal().getIpAddress());
//				initBean.setUsername(violationEvent.getTerminal().getUserName());
//				initBean.setTriggerDt(violationEvent.getClientTime());
//				initBean.setViolationSum(1);
//				initBean.getPorts().add(violationEvent.getViolationPort());
//				initBean.setPortSum(1);
//				initBean.setTriggerPort(violationEvent.getViolationPort());
//				
//				tempMap.put(key, initBean);
//			}
//		}
//		for (Entry<Long, ViolationBean> entry : tempMap.entrySet()) {
//			violationList.add(entry.getValue());
//		}
//		
//		return violationList;
//	}
//	
//	@Override
//	public List<ViolationBean> unitByTimeList(int recentDays) {
//		// TODO Auto-generated method stub
//		List<ViolationBean> violationList = new ArrayList<ViolationBean>();
//		List<ViolationEvent> violationEvents = getViolationEvents(recentDays);
//		
//		/*
//		 * 将List<ViolationEvent>里的数据，
//		 * 组织成"违规单位列表(单位，违规出口数，违规终端数，违规次数，最后触发时间，最后触发IP,最后违规出口)"所需的数据格式
//		 */
//		Map<Long, ViolationBean> tempMap = new HashMap<Long, ViolationBean>();
//		for (ViolationEvent violationEvent : violationEvents) {
//			Long key = violationEvent.getTerminal().getArea().getId();
//			if (tempMap.containsKey(key)) {
//				ViolationBean laterBean = tempMap.get(key);
//				laterBean.getPorts().add(violationEvent.getViolationPort());
//				laterBean.setPortSum(laterBean.getPorts().size());
//				laterBean.getTerminals().add(violationEvent.getTerminal().getId());
//				laterBean.setTerminalSum(laterBean.getTerminals().size());
//				laterBean.setViolationSum(laterBean.getViolationSum()+1);
//				laterBean.setTriggerDt((laterBean.getTriggerDt().after(violationEvent.getClientTime())? laterBean.getTriggerDt() : violationEvent.getClientTime()));
//				laterBean.setTriggerIP((laterBean.getTriggerDt().after(violationEvent.getClientTime()))? laterBean.getTriggerIP() : violationEvent.getTerminal().getIpAddress());
//				laterBean.setTriggerPort((laterBean.getTriggerDt().after(violationEvent.getClientTime())? laterBean.getTriggerPort() : violationEvent.getViolationPort()));
//			} else {
//				ViolationBean initBean = new ViolationBean();
//				initBean.setUnit(violationEvent.getTerminal().getArea().getName());
//				initBean.getPorts().add(violationEvent.getViolationPort());
//				initBean.setPortSum(1);
//				initBean.getTerminals().add(violationEvent.getTerminal().getId());
//				initBean.setTerminalSum(1);
//				initBean.setViolationSum(1);
//				initBean.setTriggerDt(violationEvent.getClientTime());
//				initBean.setTriggerIP(violationEvent.getTerminal().getIpAddress());
//				initBean.setTriggerPort(violationEvent.getViolationPort());
//				
//				tempMap.put(key, initBean);
//			}
//		}
//		for (Entry<Long, ViolationBean> entry : tempMap.entrySet()) {
//			violationList.add(entry.getValue());
//		}
//		
//		return violationList;
//	}
//
//	@Override
//	public List<AmChart> portWithUnit(List<ViolationBean> violationList) {
//		// TODO Auto-generated method stub
//		List<AmChart> amChartList = new ArrayList<AmChart>();
//		//List<ViolationBean> violationList = this.unitByTimeList(recentDays);
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
//		//List<ViolationBean> violationList = this.unitByTimeList(recentDays);
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
//
//}
