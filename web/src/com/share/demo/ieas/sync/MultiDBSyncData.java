//package com.vrv.ieas.sync;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//
///** 
// *         说      明：支持多个EDP库的数据同步接口
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2013-3-22 下午05:23:13 
// */
//public interface MultiDBSyncData extends SyncData {
//	/**
//	 * 写入需要同步的数据
//	 * 		区别于writeData方法的地方：传入java.sql.Connection参数、有可能抛出SQLException
//	 * 
//	 * @param <T> 实体类型
//	 * @param syncData 要同步的数据
//	 * @param syncType 同步的类型
//	 * @param dbConn 数据库连接对象
//	 * @return int DB中影响的行数
//	 * @throws SQLException
//	 */
//	public <T> int multiDBWriteData(List<T> syncData, SyncType syncType, Connection dbConn) throws SQLException;
//}
