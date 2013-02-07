//package com.vrv.ccp.service;
//
//import java.util.List;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//
//import com.vrv.ccp.base.BaseService;
//import com.vrv.ccp.bean.AmChart;
//import com.vrv.ccp.bean.CheckItem;
//import com.vrv.ccp.bean.ComplexRecord;
//import com.vrv.ccp.bean.DataBean;
//import com.vrv.ccp.bean.GridData;
//import com.vrv.ccp.bean.Task;
//import com.vrv.ccp.domain.VrvCheckRecord;
//
///** 
// *         Service层接口：客户端上报记录
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2012-12-28 上午10:47:02 
// */
//public interface VrvCheckRecordService extends BaseService<VrvCheckRecord> {
//	/**
//	 * 报表：检查报告
//	 * 
//	 * @param pageNum 第多少页
//	 * @param pageSize 每页显示条数
//	 * @param checkRecord 上报记录
//	 * @return GridData<DataBean>
//	 */
//	public GridData<DataBean> reportList(VrvCheckRecord checkRecord, int pageNum, int pageSize);
//	
//	/**
//	 * 报表：检查报告 > 历史报告
//	 * 
//	 * @param pageNum 第多少页
//	 * @param pageSize 每页显示条数
//	 * @param checkRecord 上报记录
//	 * @return GridData<DataBean>
//	 */
//	public GridData<DataBean> reportList2(VrvCheckRecord checkRecord, int pageNum, int pageSize); 
//	
//	/**
//	 * 报表：检查报告>历史报告 > 结果汇总
//	 * 
//	 * @param pageNum 第多少页
//	 * @param pageSize 每页显示条数
//	 * @param checkRecord 上报记录
//	 * @return GridData<DataBean>
//	 */
//	public List<AmChart> reportList3(VrvCheckRecord checkRecord, int pageNum, int pageSize); 
//	
//	/**
//	 * 根据策略内容，获得检查项
//	 * 
//	 * @param policyCnt 策略内容
//	 * @return CheckItem 检查项
//	 */
//	public CheckItem getCheckItemByPolicyCnt(String policyCnt);
//	
//	/**
//	 * 报表：结果查询 > 上报记录
//	 * 
//	 * @param pageNum 第多少页
//	 * @param pageSize 每页显示条数
//	 * @param checkRecord 上报记录
//	 * @return GridData<DataBean>
//	 */
//	public GridData<DataBean> reportList4(VrvCheckRecord checkRecord, int pageNum, int pageSize); 
//	
//	/**
//	 * 报表：设备检查的详情信息
//	 * 
//	 * @return ComplexRecord
//	 */
//	public ComplexRecord reportDetails(VrvCheckRecord checkRecord);
//	
//	/**
//	 * 根据策略ID批量删除
//	 * 
//	 * @param plicyIds
//	 * @return int 删除的条数
//	 */
//	public int deleteByPolicy(String plicyIds);
//	
//	/**
//	 * 获得任务(策略)信息
//	 * 
//	 * @param checkRecord 上报记录
//	 * @return Task
//	 */
//	public Task getTask(VrvCheckRecord checkRecord);
//	
//	/**
//	 * 根据策略ID批量导出(Excel)
//	 * 
//	 * @param policyIds
//	 * @return HSSFWorkbook POI工作簿
//	 */
//	public HSSFWorkbook exportByPolicy(String policyIds);
//	
//	/**
//	 * 根据检查ID批量删除
//	 * 
//	 * @param checkIds
//	 * @return int 删除的条数
//	 */
//	public int deleteByCheckId(String checkIds);
//	
//	/**
//	 * 根据检查ID批量导出(Excel)
//	 * 
//	 * @param checkIds
//	 * @return HSSFWorkbook POI工作簿
//	 */
//	public HSSFWorkbook exportByCheckId(String checkIds);
//	
//	/**
//	 * 关于检查项的详情数据
//	 * 
//	 * @param checkRecord 上报记录
//	 * @param checkType 检查类型
//	 * @param pageNum  当前页码
//	 * @param pageSize 每页大小
//	 * @return GridData<?>
//	 */
//	public GridData<?> detailData(VrvCheckRecord checkRecord, String checkType, int pageNum, int pageSize);
//}
