//package com.vrv.ieas.sync;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import org.springframework.stereotype.Component;
//
//import jxl.common.Logger;
//
//import com.vrv.ieas.domain.DbInfo;
//import com.vrv.ieas.utils.CoreUtil;
//import com.vrv.ieas.utils.IEASUtil;
//
///** 
// *         说      明：支持多个EDP库的数据自动同步(默认)实现类
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2013-3-25 上午09:34:21 
// */
//@Component("multiDBAutoSyncData")
//public class MultiDBAutoSyncDataImpl extends MultiDBSyncDataImpl implements MultiDBAutoSyncData {
//	private Logger log = Logger.getLogger(getClass());
//	/** 定时器 **/
//	private Timer timer = new Timer();
//	/** 定时任务 **/
//	private MyTimerTask myTimerTask;
//	/** 需要同步的总条数 **/
//	private static long syncTotal = -1;
//	/** 当前同步的条数 **/
//	private static long syncCount = 0;
//	/** 自动同步任务信息 **/
//	private static SyncTaskBean syncTaskBean;
//	
//	@Override
//	public void timerTask(List<DbInfo> dbInfoList) {
//		// TODO Auto-generated method stub
//		if (CoreUtil.notNull(myTimerTask)) {
//			myTimerTask.cancel();
//			timer.purge();
//		}
//		myTimerTask = new MyTimerTask(dbInfoList);
//		timer.scheduleAtFixedRate(this.myTimerTask , IEASUtil.getTaskFirstTime(), 24*60*60*1000);
//		//timer.scheduleAtFixedRate(myTimerTask, new Date(System.currentTimeMillis()+ 1000*120), 24*60*60*1000);
//	}
//	
//	@Override
//	public void timerTaskForMultiDB(List<DbInfo> dbInfoList) {
//		// TODO Auto-generated method stub
//		if (CoreUtil.notNull(myTimerTask)) {
//			myTimerTask.cancel();
//			timer.purge();
//		}
//		myTimerTask = new MyTimerTask(dbInfoList);
//		if (CoreUtil.isNull(syncTaskBean)) {
//			syncTaskBean = new SyncTaskBean();
//		}
//		switch (SyncTaskEnum.valueOf(syncTaskBean.getFrequency())) {
//		case NEVER:
//			break;
//		case EVERY_WEEK:
//			myTimerTask = new MyTimerTask(dbInfoList);
//			timer.scheduleAtFixedRate(this.myTimerTask , getEveryWeekTime(syncTaskBean), 7*24*60*60*1000);		
//			break;
//		case EVERY_DAY:
//			myTimerTask = new MyTimerTask(dbInfoList);
//			timer.scheduleAtFixedRate(this.myTimerTask , getEveryDayTime(syncTaskBean), 24*60*60*1000);
//			break;
//		case EVERE_MINUTE:
//			myTimerTask = new MyTimerTask(dbInfoList);
//			timer.schedule(this.myTimerTask , new Date(), Integer.valueOf(syncTaskBean.getValue())*60*1000);
//			break;
//		case REAL_TIME:
//			myTimerTask = new MyTimerTask(dbInfoList);
//			timer.schedule(this.myTimerTask , new Date(), 1000);
//			break;
//		case DEFAULT://使用默认
//		default: 
//			timerTask(dbInfoList);
//			break;
//		}
//	}
//
//	/**
//	 * 创建一个继承TimerTask的内部类
//	 */
//	class MyTimerTask extends TimerTask {
//		private List<DbInfo> dbInfoList;
//
//		public MyTimerTask() {
//			// TODO Auto-generated constructor stub
//		}
//		
//		public MyTimerTask(List<DbInfo> dbInfoList) {
//			this.dbInfoList = dbInfoList;
//		}
//		
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			log.info("开始数据自动同步");
//			long startFlag = System.currentTimeMillis();
//			DbInfo tempDbInfo = null;
//			try {
//				for (DbInfo dbInfo : dbInfoList) {
//					log.info("开始对EDP库["+ dbInfo.getUrl() +"]同步");
//					if (CoreUtil.isNull(tempDbInfo) || tempDbInfo != dbInfo) {
//						tempDbInfo = dbInfo;
//						syncTotal = -1;
//					}
//					long innerStartFlag = System.currentTimeMillis();
//					if (-1 == syncTotal) {
//						syncTotal = needSyncSum(SyncData.SyncType.PMDE_TO_VIOLATIONEVENT, dbInfo);
//					}
//					log.info("EDP库["+ dbInfo.getUrl() +"]共需同步"+ syncTotal +"条数据");
//					while (syncCount < syncTotal) {
//						syncCount += syncData(SyncData.SyncType.PMDE_TO_VIOLATIONEVENT, dbInfo).getSyncSum();
//					}
//					log.info("完成对EDP库["+ dbInfo.getUrl() +"]的同步，耗时："+ (System.currentTimeMillis() - innerStartFlag)/1000 +"s，条数："+ syncTotal);
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				log.error("对EDP库["+ tempDbInfo.getUrl() +"]同步时出现异常！", e);
//			}
//			log.info("数据自动同步完成，耗时："+ (System.currentTimeMillis() - startFlag)/1000 +"s");
//		}
//	}
//	
//	/**
//	 * 获得每天执行的时间
//	 * 
//	 * @param syncTaskBean 任务信息
//	 * @return Date
//	 */
//	private Date getEveryDayTime(SyncTaskBean syncTaskBean) {
//		String[] timeValue = syncTaskBean.getValue().split(":");
//		Calendar c = Calendar.getInstance();
//		if (2 == timeValue.length) {
//			c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeValue[0]));
//			c.set(Calendar.MINUTE, Integer.valueOf(timeValue[1]));
//			c.set(Calendar.SECOND, 0);
//		}
//		
//		return c.getTime();
//	}
//	
//	/**
//	 * 获得每周执行的时间
//	 * 
//	 * @param syncTaskBean 任务信息
//	 * @return Date
//	 */
//	private Date getEveryWeekTime(SyncTaskBean syncTaskBean) {
//		String[] timeValue = syncTaskBean.getValue().split(",");
//		Calendar c = Calendar.getInstance();
//		if (2 == timeValue.length) {
//			c.set(Calendar.DAY_OF_WEEK, Integer.valueOf(timeValue[0]));
//			timeValue = timeValue[1].split(":");
//			if (2 == timeValue.length) {
//				c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeValue[0]));
//				c.set(Calendar.MINUTE, Integer.valueOf(timeValue[1]));
//				c.set(Calendar.SECOND, 0);
//			}
//		}
//		
//		return c.getTime();
//	}
//
//	@Override
//	public void saveSyncSet(SyncTaskBean syncTaskBean) {
//		// TODO Auto-generated method stub
//		MultiDBAutoSyncDataImpl.syncTaskBean = syncTaskBean;
//	}
//	
//}
