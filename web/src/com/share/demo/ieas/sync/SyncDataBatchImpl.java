//package com.vrv.ieas.sync;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import jxl.common.Logger;
//
//import org.apache.commons.dbutils.handlers.ArrayHandler;
//import org.apache.commons.dbutils.handlers.BeanListHandler;
//import org.springframework.stereotype.Component;
//
//import com.vrv.ieas.bean.PMoveableDiskEvent;
//import com.vrv.ieas.domain.DbInfo;
//import com.vrv.ieas.utils.IEASUtil;
//import com.vrv.ieas.utils.JdbcDbUtilsUtil;
//import com.vrv.ieas.utils.ReadConfigFileUtil;
//
///** 
// *         说      明：数据同步的批量插入实现类,
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V3.0
// *         创建时间：2013-3-13 上午10:57:55 
// */
//@Component("syncData")
//public class SyncDataBatchImpl implements SyncData {
//	private Logger log = Logger.getLogger(getClass());
//	/**目标数据源**/
//	@Resource(name="dataSource")private DataSource dataSource;
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
//
//	/**
//	 * @see com.vrv.ieas.sync.SyncData#maxId(com.vrv.ieas.sync.SyncData.TableEnum)
//	 * liangancai @ 2013-3-13 上午10:57:56
//	 */
//	@Override
//	public int maxId(TableEnum tableEnum){
//		// TODO Auto-generated method stub
//		int maxId = 0;
//		Connection dbConn = null;
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//		try {
//			dbConn = dataSource.getConnection();
//			switch (tableEnum) {
//				case ViolationEvent:
//					pst = dbConn.prepareStatement(SQL_MAX_VIO);
//					rs = pst.executeQuery();
//					if (rs.next()) {
//						maxId = rs.getInt(1);
//					} else {
//						maxId = 0;
//					}
//					break;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			log.error("查询VRV_ViolationEvent违规事件表的最大ID时报异常！", e);
//		} finally {
//			IEASUtil.attemptClose(rs);
//			IEASUtil.attemptClose(pst);
//			IEASUtil.attemptClose(dbConn);
//		}
//		
//		return maxId;
//	}
//
//	/**
//	 * @see com.vrv.ieas.sync.SyncData#needSyncSum(com.vrv.ieas.sync.SyncData.SyncType, com.vrv.ieas.domain.DbInfo)
//	 * liangancai @ 2013-3-13 上午10:57:56
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
//	 * liangancai @ 2013-3-13 上午10:57:56
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
//	 * liangancai @ 2013-3-13 上午10:57:56
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T> int writeData(List<T> syncData, SyncType syncType) {
//		// TODO Auto-generated method stub
//		Connection dbConn = null;
//		PreparedStatement pst = null;
//		try {
//			dbConn = dataSource.getConnection();
//			dbConn.setAutoCommit(false);
//			switch (syncType) {
//				case PMDE_TO_VIOLATIONEVENT:
//					pst = dbConn.prepareStatement(SQL_WRITE_VIO);
//					for (PMoveableDiskEvent item : (List<PMoveableDiskEvent>)syncData) {
//						pst.setInt(1, item.getpEventID());
//						pst.setString(2, item.getDescription());
//						pst.setTimestamp(3, item.getUpTime());
//						pst.setTimestamp(4, item.getClientTime());
//						pst.setInt(5, Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(item.getUpTime())));
//						pst.setString(6, item.getAuthUsername());
//						pst.setString(7, IEASUtil.getViolationsPort(item.getExField1(), item.getDescription()));
//						pst.setInt(8, item.getOnlyId());
//						pst.setString(9, item.getDeviceName());
//						pst.setInt(10, item.getClassId());
//						pst.setString(11, item.getClassName());
//						pst.setString(12, item.getIpAddress());
//						pst.setLong(13, item.getIpNum());
//						pst.setString(14, item.getMacAddress());
//						pst.setString(15, item.getTel());
//						pst.setString(16, item.getUserName());
//						pst.addBatch();
//					}
//					pst.executeBatch();
//					dbConn.commit();
//					FLAG_PMO_VIO += QUERY_COUNT;
//					break;
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			log.error("向VRV_ViolationEvent违规事件表插入数据时报异常！", e);
//		} finally {
//			IEASUtil.attemptClose(pst);
//			IEASUtil.attemptClose(dbConn);
//		}
//		
//		return syncData.size();
//	}
//	
//	
//
//	/**
//	 * @see com.vrv.ieas.sync.SyncData#syncData(com.vrv.ieas.sync.SyncData.SyncType, com.vrv.ieas.domain.DbInfo)
//	 * liangancai @ 2013-3-13 上午10:57:56
//	 * @throws SQLException 
//	 */
//	@Override
//	public SyncProgress syncData(SyncType syncType, DbInfo dbInfo) throws SQLException {
//		// TODO Auto-generated method stub
//		int onceSum = 0; //一次同步的条数
//		long startFlag = System.currentTimeMillis();
//		try {
//			switch (syncType) {
//				case PMDE_TO_VIOLATIONEVENT:
//					List<PMoveableDiskEvent> violationEvent = readData(SyncData.SyncType.PMDE_TO_VIOLATIONEVENT, dbInfo);
//					onceSum = writeData(violationEvent, SyncData.SyncType.PMDE_TO_VIOLATIONEVENT);
//					break;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			log.error("数据同步的过程中出现异常，将重置标记位！", e);
//			FLAG_PMO_VIO = 0; //重置同步的标标记位
//		}
//		log.info("完成一批数据的同步["+ dbInfo.getId() +"]，耗时："+ (System.currentTimeMillis() - startFlag)/1000 +"s，条数："+onceSum);
//
//		return new SyncProgress(0, onceSum, "", "", "", syncType.toString());
//	}
//	
//}
