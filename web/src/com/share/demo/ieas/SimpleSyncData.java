//package com.share.demo.ieas;
//
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.dbutils.handlers.ArrayHandler;
//import org.apache.commons.dbutils.handlers.BeanListHandler;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.stereotype.Component;
//
//import com.vrv.ieas.bean.Classes;
//import com.vrv.ieas.bean.PMoveableDiskEvent;
//import com.vrv.ieas.domain.DbInfo;
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
//@Component("syncData")
//public class SimpleSyncData implements SyncData {
//	/**目标数据连接**/
//	@Resource(name="sessionFactory") SessionFactory sessionFactory;
//	
//	/**一次查询获取的条数**/
//	private static final int QUERY_COUNT = Integer.valueOf(ReadConfigFileUtil.getValue("queryCount"));
//	
//	/**上次同步的位置**/
//	private static int FLAG_PMO_VIO = Integer.valueOf(ReadConfigFileUtil.getValue("PMO_VIO")); //PMoveableDiskEvent ---> ViolationEvent
//	private static int FLAG_PMO_TER = Integer.valueOf(ReadConfigFileUtil.getValue("PMO_TER")); //PMoveableDiskEvent ---> Terminal
//	private static int FLAG_CLA_ARE = Integer.valueOf(ReadConfigFileUtil.getValue("CLA_ARE")); //Class              ---> Area
//	
//	/**需要同步的总条数**/
//	private static final String SQL_COUNT_PMO_VIO = " SELECT COUNT(1) FROM PMoveableDiskEvent WHERE PEventID > ? AND ExtNum = 3201 ";
//	private static final String SQL_COUNT_PMO_TER = " SELECT COUNT(1) FROM PMoveableDiskEvent WHERE PEventID > ? ";
//	private static final String SQL_COUNT_CLA_ARE = " SELECT COUNT(1) FROM Class WHERE ClassID > ?";
//	
//	/**一次读取数据的SQL**/
//	private static final String SQL_READ_PMO_VIO = " SELECT PEventID,Description, UpTime, ClientTime, AuthUsername, ExField1, OnlyID FROM PMoveableDiskEvent WHERE PEventID > ? AND PEventID <= ?  AND ExtNum = 3201 ";
//	private static final String SQL_READ_PMO_TER = " SELECT DeviceName, OnlyID, IPAddress, MacAddress, UserName, ClassId, Tel FROM PMoveableDiskEvent WHERE PEventID > ? AND PEventID <= ? ";
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
//	/**存放Terminal的主键，以便从PMoveableDiskEvent中过滤**/
//	private static Set<Integer> terminalIds = new HashSet<Integer>();
//	/**查询Terminal的主键值**/
//	private static final String SQL_ID_TER = " SELECT ID FROM VRV_Terminal ";
//	
//	@Override
//	public int needSyncSum(SyncType syncType, DbInfo dbInfo) throws SQLException, Exception {
//		int needSyncSum = 0;
//		switch(syncType){
//			case PMO_VIO:
//				FLAG_PMO_VIO = (FLAG_PMO_VIO > 0)?
//						FLAG_PMO_VIO : maxId(TableEnum.ViolationEvent);
//				needSyncSum = Integer.valueOf(new JdbcDbUtilsUtil(dbInfo).getQueryRunner().query(
//						SQL_COUNT_PMO_VIO, new ArrayHandler(), FLAG_PMO_VIO)[0].toString());
//				break;
//			case PMO_TER:
//				//和其他两种不同，必须扫描PMoveableDiskEvent全部数据，筛选Terminal
//				needSyncSum = Integer.valueOf(new JdbcDbUtilsUtil(dbInfo).getQueryRunner().query(
//						SQL_COUNT_PMO_TER, new ArrayHandler(), FLAG_PMO_TER)[0].toString());
//				break;
//			case CLA_ARE:
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
//			case PMO_VIO:
//				FLAG_PMO_VIO = (FLAG_PMO_VIO > 0)?
//						FLAG_PMO_VIO : maxId(TableEnum.ViolationEvent);
//				syncDataList = new JdbcDbUtilsUtil(dbInfo)
//					.getQueryRunner().query(SQL_READ_PMO_VIO, new BeanListHandler<PMoveableDiskEvent>(PMoveableDiskEvent.class), FLAG_PMO_VIO, FLAG_PMO_VIO + QUERY_COUNT);
//				break;
//			case PMO_TER:
//				//和其他两种不同，必须扫描PMoveableDiskEvent全部数据，筛选Terminal
//				syncDataList = new JdbcDbUtilsUtil(dbInfo)
//					.getQueryRunner().query(SQL_READ_PMO_TER, new BeanListHandler<PMoveableDiskEvent>(PMoveableDiskEvent.class), FLAG_PMO_TER, FLAG_PMO_TER + QUERY_COUNT);
//				break;
//			case CLA_ARE:
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
//			case PMO_VIO:
//				for (PMoveableDiskEvent item : (List<PMoveableDiskEvent>)syncData) {
//					currentSession.createSQLQuery(SQL_WRITE_VIO)
//					.setParameter(0, item.getpEventID())
//					.setParameter(1, item.getDescription())
//					.setParameter(2, item.getUpTime())
//					.setParameter(3, item.getClientTime())
//					.setParameter(4, Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(item.getUpTime())))
//					.setParameter(5, item.getAuthUsername())
//					.setParameter(6, item.getExField1())
//					//.setParameter(6, (int)(Math.random() * 4))//用随机数生成违规出口
//					.setParameter(7, item.getOnlyId())
//					.executeUpdate();
//				}
//				FLAG_PMO_VIO += QUERY_COUNT;
//				break;
//			case PMO_TER:
//				//针对从PMoveableDiskEvent表查询的结果集，做去重处理
//				if (terminalIds.size() == 0) {
//					List list = currentSession.createSQLQuery(SQL_ID_TER).list();
//					for (Object object : list) {
//						terminalIds.add(Integer.valueOf(object.toString()));
//					}
//				}
//				for (PMoveableDiskEvent item : (List<PMoveableDiskEvent>)syncData) {
//					if (terminalIds.contains(item.getOnlyId())) {
//						continue;
//					} else {
//						 currentSession.createSQLQuery(SQL_WRITE_TER)
//							.setParameter(0, item.getOnlyId())
//							.setParameter(1, item.getDeviceName())
//							.setParameter(2, item.getOnlyId())
//							.setParameter(3, item.getIpAddress())
//							.setParameter(4, item.getMacAddress())
//							.setParameter(5, item.getUserName())
//							.setParameter(6, item.getTel())
//							.setParameter(7, new Timestamp(new Date().getTime()))
//							.setParameter(8, item.getClassId())
//							.executeUpdate();
//						 
//						 terminalIds.add(item.getOnlyId());
//					}
//				}
//				FLAG_PMO_TER += QUERY_COUNT;
//				break;
//			case CLA_ARE:
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
//		
//		return syncData.size();
//		//return QUERY_COUNT;	
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
//			case PMO_VIO:
//				List<PMoveableDiskEvent> violationEvent = readData(SyncData.SyncType.PMO_VIO, dbInfo);
//				onceSum = writeData(violationEvent, SyncData.SyncType.PMO_VIO);
//				break;
//			case PMO_TER:
//				List<PMoveableDiskEvent> terminal = readData(SyncData.SyncType.PMO_TER, dbInfo);
//				onceSum = writeData(terminal, SyncData.SyncType.PMO_TER);
//				break;
//			case CLA_ARE:
//				List<Classes> area = readData(SyncData.SyncType.CLA_ARE, dbInfo);
//				onceSum = writeData(area, SyncData.SyncType.CLA_ARE);
//				break;
//		}
//
//		return new SyncProgress(0, onceSum, "", "", "", syncType.toString());
//	}
//
//
//}
