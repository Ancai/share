/**
 * 
 */
package com.share.component.syncdata;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 数据同步组件：单线程JDBC同步接口
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-8-31 下午5:53:38
 * @version 1.0
 */
public interface SyncData {
	
	/**
	 * 同步的类型：
	 * 		LOCALE_REOMTE //从本地向远程写入数据
	 * 		REMOTE_LOCALE //从远程向本地写入数据
	 */
	public enum SyncType {
		LOCALE_REOMTE,
		REMOTE_LOCALE
	}
	
	/**
	 * 读取数据
	 * 
	 * @param readSource 数据源
	 * @param readSql 执行的sql
	 * @return ResultSet 结果集
	 * @throws SQLException 
	 */
	public ResultSet readData(DataSource readSource, String readSql) throws SQLException;
	
	/**
	 * 写入数据
	 * 
	 * @param writeSource 数据源
	 * @param writeSql 执行的sql
	 * @param readData (从源数据)读取的结果集
	 * @return int 写入的行数
	 * @throws SQLException 
	 */
	public int writeData(DataSource writeSource, String writeSql, ResultSet readData) throws SQLException;
	
	/**
	 * 数据同步
	 * 
	 * @param type 同步的类型
	 * @param table 同步的表
	 * @return SyncProgress 同步的进度
	 * @throws SQLException 
	 */
	public SyncProgress sync(SyncType type, String table) throws SQLException;
}
