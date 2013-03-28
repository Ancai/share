//package com.vrv.ieas.sync;
//
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import jxl.common.Logger;
//
//import org.apache.commons.dbutils.handlers.ArrayHandler;
//import org.apache.commons.dbutils.handlers.BeanListHandler;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//
//import com.vrv.ieas.bean.PMoveableDiskEvent;
//import com.vrv.ieas.domain.DbInfo;
//import com.vrv.ieas.utils.IEASUtil;
//import com.vrv.ieas.utils.JdbcDbUtilsUtil;
//import com.vrv.ieas.utils.ReadConfigFileUtil;
//
///** 
// *         说      明：数据同步的实现类
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V2.0
// *         创建时间：2013-3-7 上午10:37:26 
// */
//public class SyncDataImpl implements SyncData {
//	private final Logger log = Logger.getLogger(getClass());
//	
//	/**目标数据连接**/
//	@Resource(name="sessionFactory") private SessionFactory sessionFactory;
//	
//	/**一次查询获取的条数**/
//	private static final int QUERY_COUNT = Integer.valueOf(ReadConfigFileUtil.getValue("queryCount"));
//	/**上次同步的位置**/
//	private static int FLAG_PMO_VIO = 0; //PMoveableDiskEvent ---> ViolationEvent
//	/**需要同步的总条数**/
//	private static final String SQL_COUNT_PMO_VIO = " SELECT COUNT(1) FROM PMoveableDiskEvent WHERE PEventID > ? AND ExtNum = 3201 ";
//	/**一次读取数据的SQL**/
//	private static final String SQL_READ_PMO_VIO = " SELECT PEventID,Description, UpTime, ClientTime, AuthUsername, ExField1, OnlyID, DeviceName, ClassID, ClassName, ipAddress, ipNum, macAddress, tel, userName FROM PMoveableDiskEvent WHERE PEventID > ? AND PEventID <= ?  AND ExtNum = 3201 ";
//	/**一次写入数据的SQL**/
//	private static final String SQL_WRITE_VIO = " INSERT INTO VRV_ViolationEvent (ID, Description, UpTime, ClientTime, Ymd, AuthUsername, ViolationPort, deviceId, deviceName, areaId, areaName, ip, ipNum, mac, tel, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
//	/**目标数据库中的最大主键(ID)值**/
//	private static final String SQL_MAX_VIO = " SELECT MAX(ID) FROM VRV_ViolationEvent ";
//	/**
//	 * @see com.vrv.ieas.sync.SyncData#maxId(com.vrv.ieas.sync.SyncData.TableEnum)
//	 */
//	@Override
//	public int maxId(TableEnum tableEnum) {
//		// TODO Auto-generated method stub
//		int maxId = 0;
//		SQLQuery sqlQuery = null;
//		switch(tableEnum){
//			case ViolationEvent:
//				sqlQuery = this.sessionFactory.getCurrentSession().createSQLQuery(SQL_MAX_VIO);
//				if (null != sqlQuery && null != sqlQuery.uniqueResult() ) {
//					maxId = Integer.valueOf(sqlQuery.uniqueResult().toString());
//				} else {
//					maxId = 0;
//				}
//				break;
//		}
//		
//		return maxId;
//	}
//
//	/**
//	 * @see com.vrv.ieas.sync.SyncData#needSyncSum(com.vrv.ieas.sync.SyncData.SyncType, com.vrv.ieas.domain.DbInfo)
//	 */
//	@Override
//	public int needSyncSum(SyncType syncType, DbInfo dbInfo)
//			throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		int needSyncSum = 0;
//		switch(syncType){
//			case PMDE_TO_VIOLATIONEVENT:
//				FLAG_PMO_VIO = (FLAG_PMO_VIO > 0)?
//						FLAG_PMO_VIO : maxId(TableEnum.ViolationEvent);
//				needSyncSum = Integer.valueOf(new JdbcDbUtilsUtil(dbInfo).getQueryRunner().query(
//						SQL_COUNT_PMO_VIO, new ArrayHandler(), FLAG_PMO_VIO)[0].toString());
//				break;
//		}
//		
//		return needSyncSum;
//	}
//
//	/**
//	 * @see com.vrv.ieas.sync.SyncData#readData(com.vrv.ieas.sync.SyncData.SyncType, com.vrv.ieas.domain.DbInfo)
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T> List<T> readData(SyncType syncType, DbInfo dbInfo)
//			throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		List<?> syncDataList = null;
//		switch(syncType){
//			case PMDE_TO_VIOLATIONEVENT:
//				FLAG_PMO_VIO = (FLAG_PMO_VIO > 0)?
//						FLAG_PMO_VIO : maxId(TableEnum.ViolationEvent);
//				while (true) {
//					syncDataList = new JdbcDbUtilsUtil(dbInfo)
//						.getQueryRunner().query(SQL_READ_PMO_VIO, new BeanListHandler<PMoveableDiskEvent>(PMoveableDiskEvent.class), FLAG_PMO_VIO, FLAG_PMO_VIO + QUERY_COUNT);
//					if (syncDataList.size() > 0) {
//						break;
//					} else {
//						FLAG_PMO_VIO += QUERY_COUNT;
//						log.debug("没有获取到数据，此时同步的位置："+ FLAG_PMO_VIO);
//					}
//				}
//				break;
//		}
//		return (List<T>) syncDataList;
//	}
//
//	/**
//	 * @see com.vrv.ieas.sync.SyncData#writeData(java.util.List, com.vrv.ieas.sync.SyncData.SyncType)
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T> int writeData(List<T> syncData, SyncType syncType) {
//		// TODO Auto-generated method stub
//		Session currentSession = this.sessionFactory.getCurrentSession();
//		currentSession.beginTransaction();
//		switch(syncType){
//			case PMDE_TO_VIOLATIONEVENT:
//				for (PMoveableDiskEvent item : (List<PMoveableDiskEvent>)syncData) {
//					currentSession.createSQLQuery(SQL_WRITE_VIO)
//					.setParameter(0, item.getpEventID())
//					.setParameter(1, item.getDescription())
//					.setParameter(2, item.getUpTime())
//					.setParameter(3, item.getClientTime())
//					.setParameter(4, Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(item.getUpTime())))
//					.setParameter(5, item.getAuthUsername())
//					.setParameter(6, IEASUtil.getViolationsPort(item.getExField1(), item.getDescription()))
//					.setParameter(7, item.getOnlyId())
//					.setParameter(8, item.getDeviceName())
//					.setParameter(9, item.getClassId())
//					.setParameter(10, item.getClassName())
//					.setParameter(11, item.getIpAddress())
//					.setParameter(12, item.getIpNum())
//					.setParameter(13, item.getMacAddress())
//					.setParameter(14, item.getTel())
//					.setParameter(15, item.getUserName())
//					.executeUpdate();
//				}
//				FLAG_PMO_VIO += QUERY_COUNT;
//				break;
//		}
//		currentSession.getTransaction().commit();
//		currentSession.close();
//		return syncData.size();
//	}
//
//	/**
//	 * @see com.vrv.ieas.sync.SyncData#syncData(com.vrv.ieas.sync.SyncData.SyncType, com.vrv.ieas.domain.DbInfo)
//	 */
//	@Override
//	public SyncProgress syncData(SyncType syncType, DbInfo dbInfo)
//			throws SQLException, Exception {
//		// TODO Auto-generated method stub
//		int onceSum = 0; //一次同步的条数
//		switch (syncType) {
//			case PMDE_TO_VIOLATIONEVENT:
//				List<PMoveableDiskEvent> violationEvent = readData(SyncData.SyncType.PMDE_TO_VIOLATIONEVENT, dbInfo);
//				onceSum = writeData(violationEvent, SyncData.SyncType.PMDE_TO_VIOLATIONEVENT);
//				break;
//		}
//
//		return new SyncProgress(0, onceSum, "", "", "", syncType.toString());
//	}
//
//}
