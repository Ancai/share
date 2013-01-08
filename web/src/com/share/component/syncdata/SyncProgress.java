package com.share.component.syncdata;

/** 
 *         JavaBean：同步进度
 *
 * @author 作      者：lac
 *		  E-mail: liangancai@vrvmail.com.cn 
 * @version V1.0
 *         创建时间：2012-8-18 上午09:02:31 
 */
public final class SyncProgress {
	/** 同步的总条数 **/
	private int total;
	/**实际同步的条数**/
	private int syncSum;
	/**同步的进度(最小：0, 最大：100)**/
	private String progress;
	/**同步信息**/
	private String message;
	/**同步状态**/
	private String status;
	/**同步类型**/
	private String type;
	
	/**构造方法**/
	public SyncProgress() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SyncProgress(int total, int onceSum, String progress, String message,
			String status, String type) {
		this.total = total;
		this.syncSum = onceSum;
		this.progress = progress;
		this.message = message;
		this.status = status;
		this.type = type;
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSyncSum() {
		return syncSum;
	}
	public void setSyncSum(int syncSum) {
		this.syncSum = syncSum;
	}
}
