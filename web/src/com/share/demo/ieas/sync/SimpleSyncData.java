//package com.vrv.ieas.sync;
//
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.dbutils.handlers.ArrayHandler;
//import org.apache.commons.dbutils.handlers.BeanListHandler;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//
//import com.vrv.ieas.bean.Classes;
//import com.vrv.ieas.bean.Device;
//import com.vrv.ieas.bean.PMoveableDiskEvent;
//import com.vrv.ieas.domain.DbInfo;
//import com.vrv.ieas.utils.IEASUtil;
//import com.vrv.ieas.utils.JdbcDbUtilsUtil;
//import com.vrv.ieas.utils.ReadConfigFileUtil;
//
///** 
// *         说      明：数据同步的简单实现类
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2012-8-16 上午10:47:46 
// */
//public class SimpleSyncData implements SyncData {
//	/**目标数据连接**/
//	@Resource(name="sessionFactory")private SessionFactory sessionFactory;
//	
//	/**一次查询获取的条数**/
//	private static final int QUERY_COUNT = Integer.valueOf(ReadConfigFileUtil.getValue("queryCount"));
//	
//	/**上次同步的位置**/
//	private static int FLAG_PMO_VIO = Integer.valueOf(ReadConfigFileUtil.getValue("PMO_VIO")); //PMoveableDiskEvent ---> ViolationEvent
//	private static int FLAG_DEV_TER = Integer.valueOf(ReadConfigFileUtil.getValue("DEV_TER")); //Device ---> Terminal
//	private static int FLAG_CLA_ARE = Integer.valueOf(ReadConfigFileUtil.getValue("CLA_ARE")); //Class              ---> Area
//	
//	/**需要同步的总条数**/
//	private static final String SQL_COUNT_PMO_VIO = " SELECT COUNT(1) FROM PMoveableDiskEvent WHERE PEventID > ? AND ExtNum = 3201 ";
//	private static final String SQL_COUNT_DEV_TER = " SELECT COUNT(1) FROM DEVICE WHERE DeviceID > ? ";
//	private static final String SQL_COUNT_CLA_ARE = " SELECT COUNT(1) FROM Class WHERE ClassID > ?";
//	
//	/**一次读取数据的SQL**/
//	private static final String SQL_READ_PMO_VIO = " SELECT PEventID,Description, UpTime, ClientTime, AuthUsername, ExField1, OnlyID FROM PMoveableDiskEvent WHERE PEventID > ? AND PEventID <= ?  AND ExtNum = 3201 ";
//	private static final String SQL_READ_DEV_TER = " SELECT DeviceID, DevOnlyID, DeviceName, IPAddres, MacAddress, UserName, ClassId, Tel FROM DEVICE WHERE DeviceID > ? AND DeviceID <= ? ";
//	private static final String SQL_READ_CLA_ARE = " SELECT ClassID, ClassName, UpID, ClassIDLevel  FROM Class WHERE ClassID > ? AND ClassID <= ? ";
//
//	/**一次写入数据的SQL**/
//	private static final String SQL_WRITE_VIO = " INSERT INTO VRV_ViolationEvent (ID, Description, UpTime, ClientTime, Ymd, AuthUsername, ViolationPort, Terminal) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
//	private static final String SQL_WRITE_TER = " INSERT INTO VRV_Terminal (ID, Name, OnlyID, IPAddress, MacAddress, UserName, Tel, RegisterTime, Area) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
//	private static final String SQL_WRITE_ARE = " INSERT INTO VRV_Area (ID, Name, PId, Level) VALUES (?, ?, ?, ?) ";
//	
//	/**目标数据库中的最大主键(ID)值**/
//	private static final String SQL_MAX_VIO = " SELECT MAX(ID) FROM VRV_ViolationEvent ";
//	private static final String SQL_MAX_TER = " SELECT MAX(ID) FROM VRV_Terminal ";
//	private static final String SQL_MAX_ARE = " SELECT MAX(ID) FROM VRV_Area ";
//	
//	@Override
//	public int needSyncSum(SyncType syncType, DbInfo dbInfo) throws SQLException, Exception {
//		int needSyncSum = 0;
//		switch(syncType){
//			case PMDE_TO_VIOLATIONEVENT:
//				FLAG_PMO_VIO = (FLAG_PMO_VIO > 0)?
//						FLAG_PMO_VIO : maxId(TableEnum.ViolationEvent);
//				needSyncSum = Integer.valueOf(new JdbcDbUtilsUtil(dbInfo).getQueryRunner().query(
//						SQL_COUNT_PMO_VIO, new ArrayHandler(), FLAG_PMO_VIO)[0].toString());
//				break;
//			case DEVICE_TO_TERMINAL:
//				FLAG_DEV_TER = (FLAG_DEV_TER > 0)?
//						FLAG_DEV_TER : maxId(TableEnum.Terminal);
//				needSyncSum = Integer.valueOf(new JdbcDbUtilsUtil(dbInfo).getQueryRunner().query(
//						SQL_COUNT_DEV_TER, new ArrayHandler(), FLAG_DEV_TER)[0].toString());
//				break;
//			case CLASS_TO_AREA:
//				FLAG_CLA_ARE = (FLAG_CLA_ARE > 0)?
//						FLAG_CLA_ARE : maxId(TableEnum.Area );
//				needSyncSum = Integer.valueOf(new JdbcDbUtilsUtil(dbInfo).getQueryRunner().query(
//						SQL_COUNT_CLA_ARE, new ArrayHandler(), FLAG_CLA_ARE)[0].toString());
//				break;	
//		}
//		
//		return needSyncSum;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T> List<T> readData(SyncType syncType, DbInfo dbInfo) throws SQLException, Exception {
//		List<?> syncDataList = null;
//		switch(syncType){
//			case PMDE_TO_VIOLATIONEVENT:
//				FLAG_PMO_VIO = (FLAG_PMO_VIO > 0)?
//						FLAG_PMO_VIO : maxId(TableEnum.ViolationEvent);
//				syncDataList = new JdbcDbUtilsUtil(dbInfo)
//					.getQueryRunner().query(SQL_READ_PMO_VIO, new BeanListHandler<PMoveableDiskEvent>(PMoveableDiskEvent.class), FLAG_PMO_VIO, FLAG_PMO_VIO + QUERY_COUNT);
//				break;
//			case DEVICE_TO_TERMINAL:
//				//和其他两种不同，必须扫描PMoveableDiskEvent全部数据，筛选Terminal
//				syncDataList = new JdbcDbUtilsUtil(dbInfo)
//					.getQueryRunner().query(SQL_READ_DEV_TER, new BeanListHandler<Device>(Device.class), FLAG_DEV_TER, FLAG_DEV_TER + QUERY_COUNT);
//				break;
//			case CLASS_TO_AREA:
//				FLAG_CLA_ARE = (FLAG_CLA_ARE > 0) ? FLAG_CLA_ARE : maxId(TableEnum.Area);
//				syncDataList = new JdbcDbUtilsUtil(dbInfo)
//					.getQueryRunner().query(SQL_READ_CLA_ARE, new BeanListHandler<Classes>(Classes.class), FLAG_CLA_ARE, FLAG_CLA_ARE + QUERY_COUNT);
//				break;	
//		}
//		return (List<T>) syncDataList;
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T> int writeData(List<T> syncData, SyncType syncType) {
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
//					.executeUpdate();
//				}
//				FLAG_PMO_VIO += QUERY_COUNT;
//				break;
//			case DEVICE_TO_TERMINAL:
//				for (Device item : (List<Device>)syncData) {
//					currentSession.createSQLQuery(SQL_WRITE_TER)
//						.setParameter(0, item.getDeviceId())
//						.setParameter(1, item.getDeviceName())
//						.setParameter(2, item.getDevOnlyID())
//						.setParameter(3, item.getIpAddres())
//						.setParameter(4, item.getMacAddress())
//						.setParameter(5, item.getUsername())
//						.setParameter(6, item.getTel())
//						.setParameter(7, new Timestamp(new Date().getTime()))
//						.setParameter(8, item.getClassId())
//						.executeUpdate();
//				}
//				FLAG_DEV_TER += QUERY_COUNT;
//				break;
//			case CLASS_TO_AREA:
//				for (Classes item : (List<Classes>)syncData) {
//			
//					currentSession.createSQLQuery(SQL_WRITE_ARE)
//					.setParameter(0,item.getClassId())
//					.setParameter(1,item.getClassName())
//					.setParameter(2,item.getUpId())
//					.setParameter(3,item.getClassIDLevel())
//					.executeUpdate();
//				
//				}
//				FLAG_CLA_ARE += QUERY_COUNT;
//				break;
//		}
//		currentSession.getTransaction().commit();
//		currentSession.close();
//		System.out.println(syncData.size());
//		return syncData.size();
//	}
//
//	@Override
//	public int maxId(TableEnum tableEnum) {
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
//			case Terminal:
//				sqlQuery = this.sessionFactory.getCurrentSession().createSQLQuery(SQL_MAX_TER);
//				if (null != sqlQuery && null != sqlQuery.uniqueResult() ) {
//					maxId = Integer.valueOf(sqlQuery.uniqueResult().toString());
//				} else {
//					maxId = 0;
//				}
//				break;
//			case Area:
//				sqlQuery = this.sessionFactory.getCurrentSession().createSQLQuery(SQL_MAX_ARE);
//				if (null != sqlQuery && null != sqlQuery.uniqueResult() ) {
//					maxId = Integer.valueOf(sqlQuery.uniqueResult().toString());
//				} else {
//					maxId = 0;
//				}
//				break;
//		}
//		return maxId;
//	}
//	
//	
//	@Override
//	public SyncProgress syncData(SyncType syncType, DbInfo dbInfo) throws SQLException, Exception {
//		int onceSum = 0; //一次同步的条数
//		switch (syncType) {
//			case PMDE_TO_VIOLATIONEVENT:
//				List<PMoveableDiskEvent> violationEvent = readData(SyncData.SyncType.PMDE_TO_VIOLATIONEVENT, dbInfo);
//				onceSum = writeData(violationEvent, SyncData.SyncType.PMDE_TO_VIOLATIONEVENT);
//				break;
//			case DEVICE_TO_TERMINAL:
//				List<PMoveableDiskEvent> terminal = readData(SyncData.SyncType.DEVICE_TO_TERMINAL, dbInfo);
//				onceSum = writeData(terminal, SyncData.SyncType.DEVICE_TO_TERMINAL);
//				break;
//			case CLASS_TO_AREA:
//				List<Classes> area = readData(SyncData.SyncType.CLASS_TO_AREA, dbInfo);
//				onceSum = writeData(area, SyncData.SyncType.CLASS_TO_AREA);
//				break;
//		}
//
//		return new SyncProgress(0, onceSum, "", "", "", syncType.toString());
//	}
//
//
//}
