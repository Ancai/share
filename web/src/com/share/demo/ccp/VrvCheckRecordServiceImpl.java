package com.share.demo.ccp;
//package com.vrv.ccp.service.impl;
//
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//import org.springframework.stereotype.Service;
//
//import com.vrv.ccp.aop.Constant;
//import com.vrv.ccp.base.BaseServiceImpl;
//import com.vrv.ccp.bean.AmChart;
//import com.vrv.ccp.bean.CheckItem;
//import com.vrv.ccp.bean.ComplexRecord;
//import com.vrv.ccp.bean.DataBean;
//import com.vrv.ccp.bean.GridData;
//import com.vrv.ccp.bean.Schedule;
//import com.vrv.ccp.bean.Task;
//import com.vrv.ccp.dao.PolicyDao;
//import com.vrv.ccp.dao.VrvBaseDao;
//import com.vrv.ccp.dao.VrvCheckRecordDao;
//import com.vrv.ccp.dao.VrvCommonDao;
//import com.vrv.ccp.dao.VrvFileDao;
//import com.vrv.ccp.dao.VrvProcPortDao;
//import com.vrv.ccp.dao.VrvServiceDao;
//import com.vrv.ccp.dao.VrvSoftWareDao;
//import com.vrv.ccp.dao.VrvTraceDao;
//import com.vrv.ccp.dao.VrvUdiskDao;
//import com.vrv.ccp.dao.VrvUserDao;
//import com.vrv.ccp.domain.Policy;
//import com.vrv.ccp.domain.VrvBase;
//import com.vrv.ccp.domain.VrvCheckRecord;
//import com.vrv.ccp.domain.VrvCommon;
//import com.vrv.ccp.domain.VrvFile;
//import com.vrv.ccp.domain.VrvProcPort;
//import com.vrv.ccp.domain.VrvService;
//import com.vrv.ccp.domain.VrvSoftWare;
//import com.vrv.ccp.domain.VrvTrace;
//import com.vrv.ccp.domain.VrvUdisk;
//import com.vrv.ccp.domain.VrvUser;
//import com.vrv.ccp.enums.CheckType;
//import com.vrv.ccp.service.PolicyService;
//import com.vrv.ccp.service.VrvCheckRecordService;
//import com.vrv.ccp.utils.CoreUtil;
//import com.vrv.ccp.utils.HQLBuilderUtil;
//import com.vrv.ccp.utils.POIUtil;
//
///** 
// *         Service层实现类：客户端上报记录
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2012-12-28 上午10:48:19 
// */
//@Service("vrvCheckRecordService")
//public class VrvCheckRecordServiceImpl extends BaseServiceImpl<VrvCheckRecord>
//		implements VrvCheckRecordService {
//
//	protected VrvCheckRecordDao vrvCheckRecordDao;
//	@Resource(name="vrvCheckRecordDao")
//	public void setBaseDao(VrvCheckRecordDao vrvCheckRecordDao) {
//		this.baseDao = vrvCheckRecordDao;
//		this.vrvCheckRecordDao = vrvCheckRecordDao;
//	}
//	@Resource
//	private PolicyDao policyDao;
//	@Resource
//	private PolicyService policyService;
//	@Resource
//	private VrvBaseDao vrvBaseDao;
//	@Resource
//	private VrvUserDao vrvUserDao;
//	@Resource
//	private VrvCommonDao vrvCommonDao;
//	@Resource
//	private VrvFileDao vrvFileDao;
//	@Resource
//	private VrvProcPortDao vrvProcPortDao;
//	@Resource
//	private VrvServiceDao vrvServiceDao;
//	@Resource
//	private VrvSoftWareDao vrvSoftWareDao;
//	@Resource
//	private VrvTraceDao vrvTraceDao;
//	@Resource
//	private VrvUdiskDao vrvUdiskDao;
//	
//	/** 星期的大写 **/
//	private static final String[] WEEK = {"日", "一", "二", "三", "四", "五", "六"};
//	/** 字符串的布尔值：true **/
//	private static final String TRUE = "true";
//	/** 字符串的布尔值：false **/
//	private static final String FALSE = "false";
//	/** 选中值：1 **/
//	private static final String YES = "1";
//	@Override
//	public GridData<DataBean> reportList(VrvCheckRecord vrvCheckRecord, int pageNum, int pageSize) {
//		// TODO Auto-generated method stub
//		GridData<DataBean> gridData = new GridData<DataBean>();
//		
//		HQLBuilderUtil hqlBuilder = new HQLBuilderUtil(VrvCheckRecord.class);
//		hqlBuilder.addSelectClause(" this.policyId, max(this.planTime) as maxplanTime, max(this.id) as maxId ");
//		hqlBuilder.addGroupByClause(" this.policyId ");
//		Map<Integer, Policy> policyMap = policyDao.allPolicyInMap();
//		if (StringUtils.isNotBlank(vrvCheckRecord.getPolicyName())) {
//			hqlBuilder.addWhereClause(" this.policyName like ? ", "%"+ vrvCheckRecord.getPolicyName() +"%");
//		} 
//		List<Object[]> groupList = vrvCheckRecordDao.queryByHql(hqlBuilder);
//		gridData.setTotal(groupList.size());
//		groupList = groupList.subList(CoreUtil.getFromIndex(pageNum, pageSize), 
//				CoreUtil.getToIndex(pageNum, pageSize, groupList.size()));
//		List<DataBean> rowList = new ArrayList<DataBean>();
//		for (int i = 0; i < groupList.size(); i++) {
//			Object[] objs = groupList.get(i);
//			Integer policyId = CoreUtil.parseInt(objs[0]); //策略ID
//			String maxUpTime = objs[1].toString(); //最新的上报时间
//			Integer maxId = CoreUtil.parseInt(objs[2]); //最大的上报ID
//			// 策略名称           执行周期          最近计划时间           策略ID(隐藏)
//			rowList.add(getDataBean(policyMap, policyId, maxUpTime, maxId));
//		}
//		gridData.setRows(rowList);
//		
//		return gridData;
//	}
//	
//	/**
//	 * 获得报表行数据
//	 * 
//	 * @param policyMap <策略ID, 策略>
//	 * @param policyId 策略ID
//	 * @param maxUpTime 最新的上报时间
//	 * @param maxId 最大ID(VrvCheckRecord)
//	 * @return DataBean 报表行数据
//	 */
//	private DataBean getDataBean(Map<Integer, Policy> policyMap, Integer policyId, String maxUpTime,
//			Integer maxId) {
//		DataBean dataBean = null;
//		if (policyMap.containsKey(policyId)) {
//			Policy policy = policyMap.get(policyId);
//			dataBean = new DataBean(policy.getName(), termText(policyService.getSchedule(policyId)),
//					maxUpTime, String.valueOf(policyId), String.valueOf(maxId));
//		} else {
//			VrvCheckRecord vrvCheckRecord = queryById(maxId);
//			dataBean = new DataBean(vrvCheckRecord.getPolicyName(), termText(getSchedule(vrvCheckRecord.getPolicyContent())),
//					maxUpTime, String.valueOf(policyId), String.valueOf(maxId));
//		}
//		
//		return dataBean;
//	}
//	
//	/**
//	 * 通过schedule执行计划，获得执行周期标签
//	 * 
//	 * @param schedule 策略执行计划
//	 * @return String "每周-周一"
//	 */
//	private String termText(Schedule schedule) {
//		String text = "";
//		//Schedule schedule = policyService.getSchedule(policyId);
//		switch (Integer.valueOf(schedule.getMode())) {
//			case 1: 
//				text = "仅执行一次";
//				break;
//			case 2:
//				text = "定时～" + schedule.getVal2();			
//				break;
//			case 3:
//				text = "每天～" + schedule.getVal3();
//				break;
//			case 4:
//				String[] tempArray = schedule.getVal4().split(",");
//				StringBuffer sbuff = new StringBuffer();
//				for (int i = 0; i < (tempArray.length - 1); i++) {
//					sbuff.append(WEEK[Integer.valueOf(tempArray[i].trim())]);
//					if (i != (tempArray.length - 2)) {
//						sbuff.append(",");
//					}
//				}
//				text = "每周～" + sbuff.toString() + "～" + tempArray[tempArray.length-1];
//				break;
//			case 5:
//				text = "每月～" + schedule.getVal5().split(",")[0] + "~" + schedule.getVal5().split(",")[1];
//				break;
//		}
//		
//		return text;
//	}
//	
//	
//	
//	/**
//	 * 通过policyContent获得，策略执行计划
//	 * 
//	 * @param policyContent 策略内容
//	 * @return Schedule
//	 */
//	private Schedule getSchedule(String policyContent) {
//		// TODO Auto-generated method stub
//		Schedule schedule = new Schedule();
//		SAXReader saxReader = new SAXReader();
//		Document tempDoc = null; 
//		try {
//			tempDoc = saxReader.read(new StringReader(policyContent));
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//		}
//		Element rootEle = tempDoc.getRootElement();
//		schedule.setMode(rootEle.attributeValue("mode"));
//		if("2".equals(rootEle.attributeValue("mode"))){
//			schedule.setVal2(rootEle.attributeValue("time"));
//		}
//		if("3".equals(rootEle.attributeValue("mode"))){
//			schedule.setVal3(rootEle.attributeValue("time"));
//			}
//		if("4".equals(rootEle.attributeValue("mode"))){
//			schedule.setVal4(rootEle.attributeValue("frequency")+","+rootEle.attributeValue("time"));
//			}
//		if("5".equals(rootEle.attributeValue("mode"))){
//			schedule.setVal5(rootEle.attributeValue("frequency")+","+rootEle.attributeValue("time"));
//			}
//		return schedule;
//	}
//
//	@Override
//	public GridData<DataBean> reportList2(VrvCheckRecord checkRecord,
//			int pageNum, int pageSize) {
//		// TODO Auto-generated method stub
//		GridData<DataBean> gridData = new GridData<DataBean>();
//		
//		HQLBuilderUtil hqlBuilder = new HQLBuilderUtil(VrvCheckRecord.class);
//		hqlBuilder.addSelectClause(" this.planTime, max(this.upTime) as maxUpTime, max(id) as maxId ");
//		hqlBuilder.addGroupByClause(" this.planTime ");
//		hqlBuilder.addWhereClause(" this.policyId = ? ", checkRecord.getPolicyId());
//		if (CoreUtil.notNull(checkRecord.getPlanTime())) {
//			hqlBuilder.addWhereClause(" this.planTime = ? ", checkRecord.getPlanTime());
//		} 
//		List<Object[]> groupList = vrvCheckRecordDao.queryByHql(hqlBuilder);
//		gridData.setTotal(groupList.size());
//		groupList = groupList.subList(CoreUtil.getFromIndex(pageNum, pageSize), 
//				CoreUtil.getToIndex(pageNum, pageSize, groupList.size()));
//		List<DataBean> rowList = new ArrayList<DataBean>();
//		for (int i = 0; i < groupList.size(); i++) {
//			Object[] objs = groupList.get(i);
//			// 计划时间    最后上报时间   操作
//			rowList.add(new DataBean(objs[0].toString(), objs[1].toString(), 
//					"policyId=" + checkRecord.getPolicyId() + "&checkRecordId="+ objs[2] + "&planTime=" + objs[0].toString().substring(0, 19)));
//			
//		}
//		gridData.setRows(rowList);
//		
//		return gridData;
//	}
//
//	@Override
//	public List<AmChart> reportList3(VrvCheckRecord vrvCheckRecord,
//			int pageNum, int pageSize) {
//		// TODO Auto-generated method stub
//		List<AmChart> chartList = new ArrayList<AmChart>();		
//		List<Object[]> resultList = vrvCheckRecordDao.queryBySql(vrvCheckRecord);
//		CheckItem checkItem = getCheckItemByCheckRecord(vrvCheckRecord);
//		Long chartVal = 0l;
//		String conditionHql = jointHqlCondition(vrvCheckRecord); //hql 的条件部分
//		if (Boolean.valueOf(checkItem.getBaseInfo())) {//基本信息
//			chartList.add(new AmChart(Constant.CHECK_ITEM[0], resultList.size()+"", AmChart.colors[0]));
//		}
//		if (Boolean.valueOf(checkItem.getUserList())) {//用户信息
//			chartVal = vrvUserDao.countByHql(
//					new HQLBuilderUtil(VrvUser.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql)
//					.addWhereClause(" this.type = ? ", Constant.TYPE_USER));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[1], chartVal.toString(), AmChart.colors[1]));
//		}
//		if (Boolean.valueOf(checkItem.getProcess())) {//进程信息
//			chartVal = vrvProcPortDao.countByHql(
//					new HQLBuilderUtil(VrvProcPort.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql)
//					.addWhereClause(" this.type = ? ", Constant.TYPE_PROCESS));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[2], chartVal.toString(), AmChart.colors[2]));
//		}
//		if (Boolean.valueOf(checkItem.getNetPort())) {//网络端口连接
//			chartVal = vrvProcPortDao.countByHql(
//					new HQLBuilderUtil(VrvProcPort.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql)
//					.addWhereClause(" this.type = ? ", Constant.TYPE_PORT));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[3], chartVal.toString(), AmChart.colors[3]));
//		}
//		if (Boolean.valueOf(checkItem.getUnPatch())) {//未打补丁
//			chartVal = vrvSoftWareDao.countByHql(
//					new HQLBuilderUtil(VrvSoftWare.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql)
//					.addWhereClause(" this.type = ? ", Constant.TYPE_UNPATCH));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[4], chartVal.toString(), AmChart.colors[4]));
//		}
//		if (Boolean.valueOf(checkItem.getUdiskRecord())) {//U盘使用记录
//			chartVal = vrvUdiskDao.countByHql(
//					new HQLBuilderUtil(VrvUdisk.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[5], chartVal.toString(), AmChart.colors[5]));
//		}
//		if (Boolean.valueOf(checkItem.getSoftList())) {//已安装软件
//			chartVal = vrvSoftWareDao.countByHql(
//					new HQLBuilderUtil(VrvSoftWare.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql)
//					.addWhereClause(" this.type = ? ", Constant.TYPE_SOFTWARE));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[6], chartVal.toString(), AmChart.colors[6]));
//		}
//		if (Boolean.valueOf(checkItem.getServiceList())) {//服务列表
//			chartVal = vrvServiceDao.countByHql(
//					new HQLBuilderUtil(VrvService.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[7], chartVal.toString(), AmChart.colors[7]));
//		}
//		if (Boolean.valueOf(checkItem.getMutiOS())) {//多操作系统 
//			chartVal = vrvUserDao.countByHql(
//					new HQLBuilderUtil(VrvUser.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql)
//					.addWhereClause(" this.type = ? ", Constant.TYPE_MUTI_OS));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[8], chartVal.toString(), AmChart.colors[8]));
//		}
//		if (Boolean.valueOf(checkItem.getNetRecord().getCheck())) {//上网痕迹
//			chartVal = vrvTraceDao.countByHql(
//					new HQLBuilderUtil(VrvTrace.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[9], chartVal.toString(), AmChart.colors[9]));
//		}
//		if (Boolean.valueOf(checkItem.getNetDevice().getCheck())) {//无线电设备
//			chartVal = vrvCommonDao.countByHql(
//					new HQLBuilderUtil(VrvCommon.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[10], chartVal.toString(), AmChart.colors[10]));
//		} 
//		if (Boolean.valueOf(checkItem.getFileCheck().getCheck())) {//文件内容
//			chartVal = vrvFileDao.countByHql(
//					new HQLBuilderUtil(VrvFile.class).addSelectClause(" count(*) ").addWhereClause(" this.checkId in " + conditionHql));
//			chartList.add(new AmChart(Constant.CHECK_ITEM[11], chartVal.toString(), AmChart.colors[11]));
//		}
//		
//		return chartList;
//	}
//	
//	/**
//	 * 拼接Hql语句的查询条件
//	 * 
//	 * @param resultList reportList3方法中返回的一个结果集
//	 * @return String
//	 */
//	public String jointHqlCondition(VrvCheckRecord vrvCheckRecord) {
//		StringBuffer sbuff = new StringBuffer("(");
//		List<Object[]> resultList = vrvCheckRecordDao.queryBySql(vrvCheckRecord);
//		for (int i = 0; i < resultList.size(); i++) {
//			sbuff.append(resultList.get(i));
//			sbuff.append(i == resultList.size()-1 ? ")" : ",");
//		}
//		
//		return sbuff.toString();
//	}
//	
//
//	
//	/**
//	 * 根据策略内容，获得检查项
//	 * 
//	 * @param checkRecord 上报记录
//	 * @return CheckItem 检查项
//	 */
//	private CheckItem getCheckItemByCheckRecord(VrvCheckRecord checkRecord) {
//		CheckItem checkItem = new CheckItem();
//		Policy policy = policyService.getPolicyById(checkRecord.getPolicyId());
//		if (CoreUtil.notNull(policy)) {
//			checkItem = policyService.getCheckItem(policy.getId());
//		} else {
//			String policyCnt = vrvCheckRecordDao.queryById(checkRecord.getId()).getPolicyContent();
//			checkItem = getCheckItemByPolicyCnt(policyCnt);
//		}
//		
//		return checkItem;
//	}
//	
//	/**
//	 * 根据策略内容，获得检查项
//	 * 
//	 * @param policyCnt 策略内容
//	 * @return CheckItem 检查项
//	 */
//	@SuppressWarnings("unchecked")
//	private CheckItem getCheckItemByPolicyCnt(String policyCnt) {
//		CheckItem checkItem = new CheckItem();
//		SAXReader saxReader = new SAXReader();
//		Document tempDoc = null; 
//		try {
//			tempDoc = saxReader.read(new StringReader(policyCnt));
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//		}
//		List eleList = tempDoc.getRootElement().elements();
//		for (int i = 0; i < eleList.size(); i++) {
//			Element ele = (Element) eleList.get(i);
//			switch (CheckType.valueOf(ele.attributeValue("type"))) {
//				case BASEINFO: //基本信息检查	
//					checkItem.setBaseInfo(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					break;
//				case USERLIST: //用户信息检查
//					checkItem.setUserList(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					break;
//				case PROCESS: //进程检查
//					checkItem.setProcess(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					break;
//				case NETPORT: //网络端口检查
//					checkItem.setNetPort(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					break;
//				case UNPATCH: //未打补丁检查
//					checkItem.setUnPatch(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					break;
//				case NETRECORD: //上网痕迹检查(含子项)
//					CheckItem.NetRecord netRecord = checkItem.new NetRecord();
//					netRecord.setCheck(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					netRecord.setCheckType(ele.attributeValue("CheckType"));
//					netRecord.setMode(ele.attributeValue("Mode").equals(YES) ? TRUE : FALSE);
//					netRecord.setBlackList(ele.attributeValue("BlackList"));
//					netRecord.setWhiteList(ele.attributeValue("WhiteList"));
//					checkItem.setNetRecord(netRecord);
//					break;
//				case UDISKRECORD: //U盘使用记录检查
//					checkItem.setUdiskRecord(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					break;
//				case NETDEVICE: //无线设备检查(含子项)
//					CheckItem.NetDevice netDevice = checkItem.new NetDevice();
//					netDevice.setCheck(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					netDevice.setCheckType(ele.attributeValue("CheckType"));
//					checkItem.setNetDevice(netDevice);
//					break;
//				case SOFTLIST: //安装软件检查
//					checkItem.setSoftList(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					break;
//				case SERVERLIST: //服务列表
//					checkItem.setServiceList(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					break;
//				case MUTIOS: //多操作系统检查
//					checkItem.setMutiOS(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					break;
//				case FILECHECK: //文件内容检查(含子项)
//					CheckItem.FileCheck fileCheck = checkItem.new FileCheck();
//					fileCheck.setCheck(ele.attributeValue("Check").equals(YES) ? TRUE : FALSE);
//					fileCheck.setCheckFolder(ele.attributeValue("CheckFolder"));
//					fileCheck.setExtName(ele.attributeValue("ExtName"));
//					fileCheck.setCombineMode(ele.attributeValue("CombineMode").equals(YES) ? TRUE : FALSE);
//					fileCheck.setCompareWithSpace(ele.attributeValue("CompareWithSpace").equals(YES) ? TRUE : FALSE);
//					//fileCheck.setOnlyCheckName(ele.attributeValue("OnlyCheckName").equals(YES) ? TRUE : FALSE);
//					fileCheck.setCreateFileTime1(ele.attributeValue("CreateFileTime1"));
//					fileCheck.setCreateFileTime2(ele.attributeValue("CreateFileTime2"));
//					fileCheck.setModifyFileTime1(ele.attributeValue("ModifyFileTime1"));
//					fileCheck.setModifyFileTime2(ele.attributeValue("ModifyFileTime2"));
//					fileCheck.setCheckByteCount(ele.attributeValue("CheckByteCount"));
//					fileCheck.setContent(ele.attributeValue("Content"));
//					fileCheck.setDelFileCheck(ele.attributeValue("DelFileCheck").equals(YES) ? TRUE : FALSE);
//					checkItem.setFileCheck(fileCheck);
//					break;
//				}
//		}
//		
//		return checkItem;
//	}
//
//	@Override
//	public GridData<DataBean> reportList4(VrvCheckRecord vrvCheckRecord,
//			int pageNum, int pageSize) {
//		// TODO Auto-generated method stub
//		GridData<DataBean> gridData = new GridData<DataBean>();
//		
//		HQLBuilderUtil hqlBuilder = new HQLBuilderUtil(VrvCheckRecord.class);
//		hqlBuilder.addSelectClause(" this.id, this.policyName, this.planTime, this.upTime, this.policyId ");
//		hqlBuilder.addWhereClause(" this.ipAddress = ? ", vrvCheckRecord.getIpAddress());
//		if (StringUtils.isNotBlank(vrvCheckRecord.getPolicyName())) {
//			hqlBuilder.addWhereClause(" this.policyName like ? ", "%" + vrvCheckRecord.getPolicyName() + "%");
//		} 
//		List<Object[]> resultList = vrvCheckRecordDao.queryByHql(hqlBuilder);
//		gridData.setTotal(resultList.size());
//		resultList = resultList.subList(CoreUtil.getFromIndex(pageNum, pageSize), 
//				CoreUtil.getToIndex(pageNum, pageSize, resultList.size()));
//		List<DataBean> rowList = new ArrayList<DataBean>();
//		for (int i = 0; i < resultList.size(); i++) {
//			Object[] objs = resultList.get(i);
//			//上报编号    策略名称      计划时间    上报时间   操作
//			rowList.add(new DataBean(objs[0].toString(), objs[1].toString(), CoreUtil.formatDt((Date)objs[2]),
//					CoreUtil.formatDt((Date)objs[3]), "?checkRecordId="+objs[0].toString()+"&policyId="+objs[4]));
//		}
//		gridData.setRows(rowList);
//		
//		return gridData;
//	}
//
//	@Override
//	public ComplexRecord reportDetails(VrvCheckRecord checkRecord) {
//		// TODO Auto-generated method stub
//		ComplexRecord complexRecord = new ComplexRecord();
//		CheckItem checkItem = getCheckItemByCheckRecord(checkRecord);
//		String conditionHql = " this.checkId = ? "; //hql 的条件部分
//		if (Boolean.valueOf(checkItem.getBaseInfo())) //基本信息
//			complexRecord.setVrvBaseList(vrvBaseDao.queryByHQLBuilder(new HQLBuilderUtil(VrvBase.class).addWhereClause(conditionHql, checkRecord.getId())));
//		if (Boolean.valueOf(checkItem.getUserList())) //用户信息
//			complexRecord.setVrvUserList(vrvUserDao.queryByHQLBuilder(
//					new HQLBuilderUtil(VrvUser.class).addWhereClause(conditionHql, checkRecord.getId()).addWhereClause(" this.type = ? ", Constant.TYPE_USER)));
//		if (Boolean.valueOf(checkItem.getProcess())) //进程信息
//			complexRecord.setVrvProcList(vrvProcPortDao.queryByHQLBuilder(
//					new HQLBuilderUtil(VrvProcPort.class).addWhereClause(conditionHql, checkRecord.getId()).addWhereClause(" this.type = ? ", Constant.TYPE_PROCESS)));
//		if (Boolean.valueOf(checkItem.getNetPort())) //网络端口连接
//			complexRecord.setVrvPortList(vrvProcPortDao.queryByHQLBuilder(
//					new HQLBuilderUtil(VrvProcPort.class).addWhereClause(conditionHql, checkRecord.getId()).addWhereClause(" this.type = ? ", Constant.TYPE_PORT)));
//		if (Boolean.valueOf(checkItem.getUnPatch())) //未打补丁
//			complexRecord.setVrvPatchList(vrvSoftWareDao.queryByHQLBuilder(
//					new HQLBuilderUtil(VrvSoftWare.class).addWhereClause(conditionHql, checkRecord.getId()).addWhereClause(" this.type = ? ", Constant.TYPE_UNPATCH)));
//		if (Boolean.valueOf(checkItem.getUdiskRecord())) //U盘使用记录
//			complexRecord.setVrvUdiskList(vrvUdiskDao.queryByHQLBuilder(new HQLBuilderUtil(VrvUdisk.class).addWhereClause(conditionHql, checkRecord.getId())));
//		if (Boolean.valueOf(checkItem.getSoftList())) //已安装软件
//			complexRecord.setVrvSoftWareList(vrvSoftWareDao.queryByHQLBuilder(
//					new HQLBuilderUtil(VrvSoftWare.class).addWhereClause(conditionHql, checkRecord.getId()).addWhereClause(" this.type = ? ", Constant.TYPE_SOFTWARE)));
//		if (Boolean.valueOf(checkItem.getServiceList())) //服务列表
//			complexRecord.setVrvServiceList(vrvServiceDao.queryByHQLBuilder(new HQLBuilderUtil(VrvService.class).addWhereClause(conditionHql, checkRecord.getId())));
//		if (Boolean.valueOf(checkItem.getMutiOS())) //多操作系统 
//			complexRecord.setVrvMultiOSList(vrvUserDao.queryByHQLBuilder(
//					new HQLBuilderUtil(VrvUser.class).addWhereClause(conditionHql, checkRecord.getId()).addWhereClause(" this.type = ? ", Constant.TYPE_MUTI_OS)));
//		if (Boolean.valueOf(checkItem.getNetRecord().getCheck())) //上网痕迹
//			complexRecord.setVrvTraceList(vrvTraceDao.queryByHQLBuilder(new HQLBuilderUtil(VrvTrace.class).addWhereClause(conditionHql, checkRecord.getId())));
//		if (Boolean.valueOf(checkItem.getNetDevice().getCheck())) //无线电设备
//			complexRecord.setVrvCommonList(vrvCommonDao.queryByHQLBuilder(new HQLBuilderUtil(VrvCommon.class).addWhereClause(conditionHql, checkRecord.getId())));
//		if (Boolean.valueOf(checkItem.getFileCheck().getCheck())) //文件内容
//			complexRecord.setVrvFileList(vrvFileDao.queryByHQLBuilder(new HQLBuilderUtil(VrvFile.class).addWhereClause(conditionHql, checkRecord.getId())));
//		
//		return complexRecord;
//	}
//
//	@Override
//	public int deleteByPolicy(String plicyIds) {
//		// TODO Auto-generated method stub
//		String sql = " SELECT id from "+ Constant.TABLE_CLIENT_CHECK_RECORD +" WHERE  policyId IN ("+ plicyIds +")";
//		List<Object[]> results = vrvCheckRecordDao.queryBySql(sql);
//		StringBuffer tempBuff = new StringBuffer();
//		for (int i = 0; i < results.size(); i++) {
//			tempBuff.append(results.get(i));
//			if (i != results.size()-1 ) {
//				tempBuff.append(",");
//			}
//		}
//		String checkIds = tempBuff.toString();
//		this.vrvBaseDao.deleteByCheckId(checkIds);
//		this.vrvCommonDao.deleteByCheckId(checkIds);
//		this.vrvFileDao.deleteByCheckId(checkIds);
//		this.vrvProcPortDao.deleteByCheckId(checkIds);
//		this.vrvServiceDao.deleteByCheckId(checkIds);
//		this.vrvSoftWareDao.deleteByCheckId(checkIds);
//		this.vrvTraceDao.deleteByCheckId(checkIds);
//		this.vrvUdiskDao.deleteByCheckId(checkIds);
//		this.vrvUserDao.deleteByCheckId(checkIds);
//		
//		return this.vrvCheckRecordDao.deleteBatch(plicyIds);
//	}
//
//	@Override
//	public Task getTask(VrvCheckRecord checkRecord) {
//		// TODO Auto-generated method stub
//		Task task = new Task();
//		Policy policy = policyService.getPolicyById(checkRecord.getPolicyId());
//		if (CoreUtil.notNull(policy)) {
//			task.setName(policy.getName());
//			task.setTerm(termText(getSchedule(policy.getContent())));
//			task.setAssign(getAssignText(policy.getObj()));
//		} else if (CoreUtil.notNull(checkRecord.getId())) {
//			VrvCheckRecord checkRecord2 = queryById(checkRecord.getId());
//			task.setName(checkRecord2.getPolicyName());
//			task.setTerm(termText(getSchedule(checkRecord2.getPolicyContent())));
//			task.setAssign("该任务已被删除");
//		}
//		
//		return task;
//	}
//	
//	/**
//	 * 将数据库中存储的策略下发对象，
//	 * 			转换成比较友好的文本形式
//	 * 
//	 * @param policy
//	 * @return String
//	 */
//	private String getAssignText(String policyObj) {
//		String assignText = "";
//		if(StringUtils.isNotBlank(policyObj)) {
//			if(policyObj.contains(PolicyService.TAG_ALL_DEVICE)) {
//				assignText += " 所有设备";
//			}
//			if(policyObj.contains(PolicyService.TAG_ROAM_DEVICE)) {
//				assignText += " 漫游设备";
//			}
//			if(policyObj.contains(PolicyService.TAG_TEMP_CLIENT)) {
//				assignText += " 临时设备";
//			}
//			if(policyObj.contains(PolicyService.TAG_OS)) {
//				Matcher OldvalMatcher = Pattern.compile(PolicyService.TAG_OS + ">(.*?)</" + PolicyService.TAG_OS).matcher(policyObj);
//				if (OldvalMatcher.find()) {
//					assignText += " 装有："+OldvalMatcher.group(1)+"设备";
//				} 
//				
//			}
//			if(policyObj.contains(PolicyService.TAG_IP)) {
//				Matcher OldvalMatcher = Pattern.compile(PolicyService.TAG_IP + ">(.*?)</" + PolicyService.TAG_IP).matcher(policyObj);
//				if (OldvalMatcher.find()) {
//					assignText += OldvalMatcher.group(1);
//				} 
//			}
//			if(policyObj.contains(PolicyService.TAG_IP_NO)) {
//				Matcher OldvalMatcher = Pattern.compile(PolicyService.TAG_IP_NO + ">(.*?)</" + PolicyService.TAG_IP_NO).matcher(policyObj);
//				if (OldvalMatcher.find()) {
//					assignText += OldvalMatcher.group(1);
//				} 
//			}
//			if(policyObj.contains(PolicyService.TAG_AREA)) {
//				Matcher OldvalMatcher = Pattern.compile(PolicyService.TAG_AREA + ">(.*?)</" + PolicyService.TAG_AREA).matcher(policyObj);
//				if (OldvalMatcher.find()) {
//					assignText += "区域管理器ID："+OldvalMatcher.group(1);
//				} 
//			}
//		}
//		
//		return assignText;
//	}
//
//	@Override
//	public HSSFWorkbook exportByPolicy(String policyIds) {
//		// TODO Auto-generated method stub
//		Map<String, List<Object[]>> dataRow = new HashMap<String, List<Object[]>>();
//		Map<String, String[]> titleRow = new HashMap<String, String[]>();
//		String sql = " SELECT id from "+ Constant.TABLE_CLIENT_CHECK_RECORD +" WHERE  policyId IN ("+ policyIds +")";
//		List<Object[]> results = vrvCheckRecordDao.queryBySql(sql);
//		StringBuffer tempBuff = new StringBuffer();
//		for (int i = 0; i < results.size(); i++) {
//			tempBuff.append(results.get(i));
//			if (i != results.size()-1 ) {
//				tempBuff.append(",");
//			}
//		}
//		String checkIds = tempBuff.toString();
//		//上报记录
//		sql = Constant.SQL_QUERY_CLIENT_CHECK_RECORD + " WHERE id in ("+ checkIds +")";
//		dataRow.put("上报记录", vrvCheckRecordDao.queryBySql(sql));
//		titleRow.put("上报记录", new String[]{"ID", "设备ID", "设备名称", "IP地址", "数字IP地址", "Mac地址", "区域ID", "区域名称", "单位名称", 
//				"部门名称", "用户名", "电话 ", "策略ID", "策略名称", "策略内容", "计划执行时间 ", "上报时间", "客户端时间", "未知", "未知", "未知", "未知", "未知",
//				"保留字段1", "保留字段2", "保留字段3", "保留字段4"});
//		//检查项_基本信息
//		sql = Constant.SQL_QUERY_CLIENT_BASE +" WHERE checkId in ("+ checkIds +")";
//		dataRow.put("基本信息", vrvBaseDao.queryBySql(sql));
//		titleRow.put("基本信息", new String[] {"ID", "检查ID", "计算机名称", "内存大小", "Mac地址", "IP地址", "操作系统名称", "系统安装时间", "复合信息", "保留字段1", "保留字段2", "保留字段3", "保留字段4"});
//		//无线设备
//		sql = Constant.SQL_QUERY_CLIENT_COMMON +" WHERE checkId in ("+ checkIds +")";
//		dataRow.put("无线设备", vrvCommonDao.queryBySql(sql));
//		titleRow.put("无线设备", new String[]{"ID", "检查ID", "设备类型", "设备型号", "首次使用时间", "上次使用时间", "保留字段1", "保留字段2", "保留字段3", "保留字段4"});
//		//文件内容
//		sql = Constant.SQL_QUERY_CLIENT_FILE +" WHERE checkId in ("+ checkIds +")";
//		dataRow.put("文件内容", vrvFileDao.queryBySql(sql));
//		titleRow.put("文件内容", new String[]{"ID", "检查ID", "文件名称 ", "文件类型", "敏感关键字", "文件路径","保留字段1", "保留字段2", "保留字段3", "保留字段4"});
//		//进程和端口
//		sql = Constant.SQL_QUERY_CLIENT_PROC_PORT +" WHERE checkId in ("+ checkIds +")";
//		dataRow.put("进程和端口", vrvProcPortDao.queryBySql(sql));
//		titleRow.put("进程和端口", new String[] {"ID", "检查ID", "进程名", "进程ID", "映像全路径", "命令行", "用户名", "映像文件是否签名", "进程或端口", "端口通信的协议", "本地IP地址", "目的IP地址", "本地端口", "目的端口", "端口的监听状态", "保留字段1", "保留字段2", "保留字段3", "保留字段4" });
//		//服务
//		sql = Constant.SQL_QUERY_CLIENT_SERVICE +" WHERE checkId in ("+ checkIds +")";
//		dataRow.put("服务", vrvServiceDao.queryBySql(sql));
//		titleRow.put("服务", new String[]{"ID", "检查ID", "服务名", "服务状态", "启动类型", "公司信息", "命令行", "描述", "保留字段1", "保留字段2", "保留字段3", "保留字段4" });
//		//软件和补丁
//		sql = Constant.SQL_QUERY_CLIENT_SOFTWARE +" WHERE checkId in ("+ checkIds +")";
//		dataRow.put("软件和补丁", vrvSoftWareDao.queryBySql(sql));
//		titleRow.put("软件和补丁", new String[]{"ID", "检查ID", "类型", "补丁号", "补丁描述", "卸载命令行", "软件名称", "版本信息", "软件安装时间", "保留字段1", "保留字段2", "保留字段3", "保留字段4" });
//		//上网痕迹
//		sql = Constant.SQL_QUERY_CLIENT_TRACE +" WHERE checkId in ("+ checkIds +")";
//		dataRow.put("上网痕迹", vrvTraceDao.queryBySql(sql));
//		titleRow.put("上网痕迹", new String[] {"ID", "检查ID", "网页地址", "访问时间", "用户名", "文件名", "文件路径 ", "检查的位置", "浏览器类型", "保留字段1", "保留字段2", "保留字段3", "保留字段4"});
//		//U盘使用
//		sql = Constant.SQL_QUERY_CLIENT_UDISK +" WHERE checkId in ("+ checkIds +")";
//		dataRow.put("U盘使用", vrvUdiskDao.queryBySql(sql));
//		titleRow.put("U盘使用", new String[]{"ID", "检查ID", "U盘型号", "U盘序列号", "首次使用", "上次使用", "保留字段1", "保留字段2", "保留字段3", "保留字段4"});
//		//计算机用户和多操作系统
//		sql = Constant.SQL_QUERY_CLIENT_USER +" WHERE checkId in ("+ checkIds +")";
//		dataRow.put("计算机用户和多操作系统", vrvUserDao.queryBySql(sql));
//		titleRow.put("计算机用户和多操作系统", new String[] {"ID", "检查ID", "用户名", "全名 ", "描述", "类型", "系统名称", "安装路径", "安装时间 ", "保留字段1", "保留字段2", "保留字段3", "保留字段4"});
//		
//		return POIUtil.excel(dataRow, titleRow);
//	}
//	
//}
