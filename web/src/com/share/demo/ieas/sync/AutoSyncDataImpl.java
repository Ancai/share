//package com.vrv.ieas.sync;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import jxl.common.Logger;
//
//import org.apache.commons.lang.ArrayUtils;
//
//import com.vrv.ieas.domain.DbInfo;
//import com.vrv.ieas.utils.IEASUtil;
//
///** 
// *         说      明：数据自动同步的实现类
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2013-3-12 下午05:01:44 
// */
//public class AutoSyncDataImpl extends SyncDataBatchImpl implements AutoSyncData {
//	private Logger log = Logger.getLogger(getClass());
//	/** 定时器 **/
//	private Timer timer = new Timer();
//	/** 存放定时任务 **/
//	private Map<String, MyTimerTask> timerTaskMap = new HashMap<String, MyTimerTask>();
//	/** 需要同步的总条数 **/
//	private static long syncTotal = -1;
//	/** 当前同步的条数 **/
//	private static long syncCount = -1;
//
//	/**
//	 * @see com.vrv.ieas.sync.AutoSyncData#timerTask(List<DbInfo>)
//	 * liangancai @ 2013-3-12 下午05:01:44
//	 */
//	@Override
//	public void timerTask(List<DbInfo> dbInfoList) {
//		// TODO Auto-generated method stub
//		for (DbInfo dbInfo : dbInfoList) {
//			if (!timerTaskMap.containsKey(dbInfo.getId())) {
//				MyTimerTask myTimerTask = new MyTimerTask(dbInfo);
//				timer.scheduleAtFixedRate(myTimerTask , IEASUtil.getTaskFirstTime(), 24*60*60*1000);
//				//timer.scheduleAtFixedRate(myTimerTask, new Date(System.currentTimeMillis()+ 1000*120), 24*60*60*1000);
//				timerTaskMap.put(dbInfo.getId(), myTimerTask);
//			}
//		}
//		String[] dbInfoIds = getIdsList(dbInfoList);
//		for (String key : timerTaskMap.keySet()) {
//			if (!ArrayUtils.contains(dbInfoIds, key)) {
//				MyTimerTask myTimerTask = timerTaskMap.get(key);
//				myTimerTask.cancel();
//				timerTaskMap.remove(key);
//			}
//		}
//		timer.purge();
//	}
//	
//	/**
//	 * 创建一个继承TimerTask的内部类
//	 */
//	class MyTimerTask extends TimerTask {
//		private DbInfo dbInfo;
//
//		public MyTimerTask() {
//			// TODO Auto-generated constructor stub
//		}
//		
//		public MyTimerTask(DbInfo dbInfo) {
//			this.dbInfo = dbInfo;
//		}
//		
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			log.info("开始数据自动同步["+ dbInfo.getId() +"]");
//			long startFlag = System.currentTimeMillis();
//			try {
//				if (-1 == syncTotal) {
//					syncTotal = needSyncSum(SyncData.SyncType.PMDE_TO_VIOLATIONEVENT, dbInfo);
//				}
//				while (syncCount < syncTotal) {
//					syncCount += syncData(SyncData.SyncType.PMDE_TO_VIOLATIONEVENT, dbInfo).getSyncSum();
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				log.error("自动同步的过程中出现异常！", e);
//			}
//			log.info("数据自动同步["+ dbInfo.getId() +"]完成，耗时："+ (System.currentTimeMillis() - startFlag)/1000 +"s，条数："+syncTotal);
//		}
//	}
//	
//	
//	
//	/**
//	 * 从DbInfoList集合中存放的对象中，抽取出ID，重新组装成一个数组
//	 * 
//	 * @param dbInfoList 
//	 * @return String[]
//	 */
//	private String[] getIdsList(List<DbInfo> dbInfoList) {
//		String[] tempArray = new String[dbInfoList.size()];
//		for (int i = 0; i < dbInfoList.size(); i++) {
//			tempArray[i] = dbInfoList.get(i).getId();
//		}
//		
//		return tempArray;
//	}
//	
//}
