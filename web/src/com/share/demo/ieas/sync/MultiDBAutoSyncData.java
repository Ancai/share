//package com.vrv.ieas.sync;
//
//import java.util.List;
//
//import com.vrv.ieas.domain.DbInfo;
//
///** 
// *         说      明：支持多个EDP库的数据自动同步接口
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2013-3-25 上午09:32:35 
// */
//public interface MultiDBAutoSyncData extends AutoSyncData {
//	/**
//	 * 根据设定的配置[syncTaskBean]，开始启动“数据自动同步”任务，支持多个EDP库
//	 * 
//	 * @param dbInfoList 存放“数据连接对象”的集合
//	 */
//	void timerTaskForMultiDB(List<DbInfo> dbInfoList);
//	
//	/**
//	 * 保存同步设置
//	 * 
//	 * @param syncTaskBean
//	 */
//	void saveSyncSet(SyncTaskBean syncTaskBean);
//}
