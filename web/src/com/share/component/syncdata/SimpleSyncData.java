/**
 * 
 */
package com.share.component.syncdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.share.common.SystemConfig;


/**
 * 数据同步组件：单线程JDBC同步的简单实现类
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-9-1 下午5:43:06
 * @version 1.0
 */
public class SimpleSyncData implements SyncData{
	/** 本地数据源 **/
	@Resource(name = "dataSource")private DataSource localSourc;
	/** 远程服务器数据源 **/
	@Resource(name = "dataSource")private DataSource remoteSourc;
	
	/** 默认10000条数据，提交一次 **/
	private static final int DEFAULT_BATCH_COUNT = 10000;
	/** 查询SQL语句的前缀 **/
	private static final String PREFIX_SQL_READ = "sql_read_";
	/** 插入SQL语句的前缀 **/
	private static final String PREFIX_SQL_WRITE = "sql_write_";
	
	
	/**
	 * 关闭访问数据库占用的资源
	 * 
	 * @param writeConn 写入的连接对象
	 * @param pst 预处理器
	 * @param readData 读取的结果集
	 * @throws SQLException 
	 */
	private void closeResource(Connection writeConn, PreparedStatement pst, ResultSet readData) throws SQLException {
		readData.close();
		pst.close();
		writeConn.close();
	} 
	
	public ResultSet readData(DataSource readSource, String readSql) throws SQLException {
		PreparedStatement pst = readSource.getConnection().prepareStatement(readSql);
		
		return pst.executeQuery();
	}

	@Override
	public int writeData(DataSource writeSource, String writeSql,
			ResultSet readData) throws SQLException {
		// TODO Auto-generated method stub
		int batchCount = 0;
		Connection writeConn = writeSource.getConnection();
		writeConn.setAutoCommit(false);
		PreparedStatement pst = writeConn.prepareStatement(writeSql);
		
		int columnCount = readData.getMetaData().getColumnCount(); //结果集包含的列数
		while (readData.next()) {
			for (int i = 1; i <= columnCount; i++) {
				pst.setObject(i, readData.getObject(i));
			}
			pst.addBatch();
			batchCount ++;
			if (batchCount % DEFAULT_BATCH_COUNT == 0) { //每  DEFAULT_BATCH_COUNT 条数据 提交一次
				pst.executeBatch();
				pst.clearBatch();
				writeConn.commit();
			}
		} // 接下来，提交剩余的批处理
		if (batchCount % DEFAULT_BATCH_COUNT != 0) {
			pst.executeBatch();
			pst.clearBatch();
			writeConn.commit();
		}
		closeResource(writeConn, pst, readData);
		
		return batchCount;
	}

	@Override
	public SyncProgress sync(SyncType type, String table) throws SQLException {
		// TODO Auto-generated method stub
		int executeCount = 0; //同步的数目
		String readSql = SystemConfig.getConfig(PREFIX_SQL_READ + table);
		String writeSql = SystemConfig.getConfig(PREFIX_SQL_WRITE + table);
		switch (type) {
			case LOCALE_REOMTE:
				executeCount = writeData(remoteSourc, writeSql, readData(localSourc, readSql));
				break;
			case REMOTE_LOCALE:
				executeCount = writeData(localSourc, writeSql, readData(remoteSourc, readSql));		
				break;
		}
		
		return new SyncProgress(0, executeCount, "", "", "", "");
	}
}
