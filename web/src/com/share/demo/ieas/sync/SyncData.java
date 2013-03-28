//package com.vrv.ieas.sync;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import com.vrv.ieas.domain.DbInfo;
//
///** 
// *         说      明：数据同步接口
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2012-8-16 上午10:08:28 
// */
//public interface SyncData {
//
//	/**
//	 * 同步的类型：
//	 * 		PMoveableDiskEvent ---> ViolationEvent
//	 * 		PMoveableDiskEvent ---> Terminal
//	 * 		Class              ---> Area
//	 */
//	public enum SyncType {
//		PMDE_TO_VIOLATIONEVENT, //PMoveableDiskEvent ---> ViolationEvent
//		DEVICE_TO_TERMINAL, //Device ---> Terminal
//		CLASS_TO_AREA  //Class ---> Area
//	}
//	
//	/**
//	 * 涉及的表
//	 */
//	public enum TableEnum {
//		PMoveableDiskEvent,
//		Device,
//		Class,
//		ViolationEvent,
//		Terminal,
//		Area
//	}
//	
//	/**
//	 * 获取需要同步的总条数
//	 * 
//	 * @param syncType 同步的类型
//	 * @param dbInfo 源数据链接
//	 * @return int
//	 */
//	public int needSyncSum(SyncType syncType, DbInfo dbInfo)
//	 throws SQLException, Exception ;
//	
//	/**
//	 * 读取需要同步的数据
//	 * 
//	 * @param <T> 实体类型
//	 * @param syncType 同步的类型
//	 * @param dbInfo 源数据链接
//	 * @return List<T>
//	 */
//	public <T> List<T> readData(SyncType syncType, DbInfo dbInfo) 
//	throws SQLException, Exception;
//	
//	/**
//	 * 写入需要同步的数据
//	 * 
//	 * @param <T> 实体类型
//	 * @param syncData 要同步的数据
//	 * @param syncType 同步的类型
//	 * @return int DB中影响的行数
//	 */
//	public <T> int writeData(List<T> syncData, SyncType syncType);
//	
//	/**
//	 * 同步数据
//	 * 
//	 * @param syncType 同步类型
//	 * @param dbInfo 源数据连接
//	 * @return SyncProgress(同步进度)
//	 */
//	public SyncProgress syncData(SyncType syncType, DbInfo dbInfo)
//	throws SQLException, Exception;
//	
//	/**
//	 * 获得(目标数据中相应表)最大主键值
//	 * 
//	 * @param tableEnum 表
//	 * @return int
//	 */
//	public int maxId(TableEnum tableEnum);
//}
