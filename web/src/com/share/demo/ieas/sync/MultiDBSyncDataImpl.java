//package com.vrv.ieas.sync;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
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
//import com.vrv.ieas.dao.DbInfoDao;
//import com.vrv.ieas.domain.DbInfo;
//import com.vrv.ieas.utils.CoreUtil;
//import com.vrv.ieas.utils.IEASUtil;
//import com.vrv.ieas.utils.JdbcDbUtilsUtil;
//import com.vrv.ieas.utils.ReadConfigFileUtil;
//
///** 
// *         说      明：支持多个EDP库的数据同步(默认)实现类
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2013-3-22 下午08:12:27 
// */
//@Component("multiDBSyncData")
//public class MultiDBSyncDataImpl extends SyncDataBatchImpl implements
//		MultiDBSyncData {
//	private Logger log = Logger.getLogger(getClass());
//	/**目标数据源**/
//	@Resource(name="dataSource")private DataSource dataSource;
//	/** 同步的连接信息 **/
//	@Resource(name="dbInfoDao")private DbInfoDao dbInfoDao;
//	/** 数据库连接信息 **/
//	private DbInfo dbInfoMemberVar;
//	
//	
//	/**一次查询获取的条数**/
//	private static final int QUERY_COUNT = Integer.valueOf(ReadConfigFileUtil.getValue("queryCount"));
//	/**需要同步的总条数**/
//	private static final String SQL_COUNT_PMO_VIO = " SELECT COUNT(1) FROM PMoveableDiskEvent WHERE PEventID > ? AND ExtNum = 3201 ";
//	/**一次读取数据的SQL**/
//	private static final String SQL_READ_PMO_VIO = " SELECT PEventID,Description, UpTime, ClientTime, AuthUsername, ExField1, OnlyID, DeviceName, ClassID, ClassName, ipAddress, ipNum, macAddress, tel, userName FROM PMoveableDiskEvent WHERE PEventID > ? AND PEventID <= ?  AND ExtNum = 3201 ";
//	/**一次写入数据的SQL**/
//	private static final String SQL_WRITE_VIO = " INSERT INTO VRV_ViolationEvent (PEVENTID, DESCRIPTION, UPTIME, CLIENTTIME, YMD, AUTHUSERNAME, VIOLATIONPORT, DEVICEID, DEVICENAME, AREAID, AREANAME, IP, IPNUM, MAC, TEL, USERNAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
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
//				needSyncSum = Integer.valueOf(new JdbcDbUtilsUtil(dbInfo).getQueryRunner().query(
//						SQL_COUNT_PMO_VIO, new ArrayHandler(), CoreUtil.notNull(dbInfo.getFlag())?dbInfo.getFlag():0)[0].toString());
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
//		int tempFlag = CoreUtil.notNull(dbInfo.getFlag())?dbInfo.getFlag() : 0;
//		switch(syncType){
//			case PMDE_TO_VIOLATIONEVENT:
//				syncDataList = new JdbcDbUtilsUtil(dbInfo)
//					.getQueryRunner().query(SQL_READ_PMO_VIO, new BeanListHandler<PMoveableDiskEvent>(PMoveableDiskEvent.class), tempFlag, tempFlag + QUERY_COUNT);
//				break;
//		}
//		dbInfo.setFlag(tempFlag + QUERY_COUNT);
//		dbInfoMemberVar = dbInfo;
//		
//		return (List<T>) syncDataList;
//	}
//
//	/**
//	 * @see com.vrv.ieas.sync.MultiDBSyncData#multiDBWriteData(java.util.List, com.vrv.ieas.sync.SyncData.SyncType, java.sql.Connection)
//	 * liangancai @ 2013-3-13 上午10:57:56
//	 * @throws SQLException 
//	 */
//	@SuppressWarnings("unchecked")
//	public <T> int multiDBWriteData(List<T> syncData, SyncType syncType, Connection dbConn) throws SQLException {
//		// TODO Auto-generated method stub
//		PreparedStatement pst = null;
//		dbConn.setAutoCommit(false);
//		switch (syncType) {
//			case PMDE_TO_VIOLATIONEVENT:
//				pst = dbConn.prepareStatement(SQL_WRITE_VIO);
//				for (PMoveableDiskEvent item : (List<PMoveableDiskEvent>)syncData) {
//					pst.setInt(1, item.getpEventID());
//					pst.setString(2, item.getDescription());
//					pst.setTimestamp(3, item.getUpTime());
//					pst.setTimestamp(4, item.getClientTime());
//					pst.setInt(5, Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(item.getUpTime())));
//					pst.setString(6, item.getAuthUsername());
//					pst.setString(7, IEASUtil.getViolationsPort(item.getExField1(), item.getDescription()));
//					pst.setInt(8, item.getOnlyId());
//					pst.setString(9, item.getDeviceName());
//					pst.setInt(10, item.getClassId());
//					pst.setString(11, item.getClassName());
//					pst.setString(12, item.getIpAddress());
//					pst.setLong(13, item.getIpNum());
//					pst.setString(14, item.getMacAddress());
//					pst.setString(15, item.getTel());
//					pst.setString(16, item.getUserName());
//					pst.addBatch();
//				}
//				pst.executeBatch();
//				dbConn.commit();
//				break;
//		}
//		IEASUtil.attemptClose(pst);
//		IEASUtil.attemptClose(dbConn);
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
//		Connection dbConn = dataSource.getConnection();
//		try {
//			switch (syncType) {
//				case PMDE_TO_VIOLATIONEVENT:
//					List<PMoveableDiskEvent> violationEvent = readData(SyncData.SyncType.PMDE_TO_VIOLATIONEVENT, dbInfo);
//					onceSum = multiDBWriteData(violationEvent, SyncData.SyncType.PMDE_TO_VIOLATIONEVENT, dbConn);
//					break;
//			}
//			this.dbInfoDao.updateFlagUseSQL(dbInfoMemberVar);//更新同步标记位
//		} catch (Exception e) {
//			// TODO: handle exception
//			dbConn.rollback();
//			dbInfoMemberVar.setFlag(dbInfoMemberVar.getFlag()- QUERY_COUNT);
//			this.dbInfoDao.updateFlagUseSQL(dbInfoMemberVar);
//			log.error("数据同步的过程中出现异常，将回滚数据并重置标记位！", e);
//		}
//		log.info("完成一批数据的同步["+ dbInfo.getId() +"]，耗时："+ (System.currentTimeMillis() - startFlag)/1000 +"s，条数："+onceSum +"，同步标记位："+ dbInfoMemberVar.getFlag());
//
//		return new SyncProgress(0, onceSum, "", "", "", syncType.toString());
//	}
//}
