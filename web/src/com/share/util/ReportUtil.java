package com.share.util;
//
//import java.util.Iterator;
//import java.util.List;

//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRField;
//import net.sf.jasperreports.engine.JasperRunManager;

/** 
 *         说      明：报表工具类
 *
 * @author 作      者：lac
 *		  E-mail: liangancai@vrvmail.com.cn 
 * @version V1.0
 *         创建时间：2012-8-1 下午05:55:21 
 */
public final class ReportUtil {
	
//	/**
//	 * HTML报表
//	 * @param sourceFileName 模板文件(.jasper)
//	 * @param fields         涉及的字段(数组)
//	 * @param list           List结果集
//	 * @throws JRException
//	 */
//	public static void html(String sourceFileName, String[] fields, List<?> list) throws JRException {
////		JasperRunManager.runReportToHtmlFile(sourceFileName,
////				null, new HibernateDataSource(fields, list) );
//	}
//	
//	/**
//	 * PDF报表
//	 * @param sourceFileName 模板文件(.jasper)
//	 * @param fields 涉及的字段(数组)
//	 * @param list List结果集
//	 * @throws JRException 
//	 */
//	public static byte[] pdf(String sourceFileName, String[] fields, List<?> list) throws JRException {
//		return JasperRunManager.runReportToPdf(sourceFileName,
//				null, new HibernateDataSource(fields, list));
//	}
//	
//}
///**
// * 使用Hibernate的查询结果，作为JasperReport的数据源
// */
//class HibernateDataSource implements JRDataSource {
//	private String[] fields;
//	private Iterator<?> iterator;
//	private Object currentValue;
//	
//	/**
//	 * 携带两个参数的构造方法
//	 * @param fields 字段数组
//	 * @param list 数据集合
//	 */
//	public HibernateDataSource(String[] fields, List<?> list) {
//		this.fields = fields;
//		this.iterator = list.iterator();
//	}
//	
//	@Override
//	public Object getFieldValue(JRField arg0) throws JRException {
//		// TODO Auto-generated method stub
//		int index = getFieldIndex(arg0.getName());
//		return (index > -1)?((Object[])currentValue)[index]:null;
//	}
//
//	@Override
//	public boolean next() throws JRException {
//		// TODO Auto-generated method stub
//		return this.iterator.hasNext();
//	}
//	
//	private int getFieldIndex(String filedName) {
//		int index = -1;
//		for (int i = 0; i < this.fields.length; i++) {
//			if (filedName.equals(this.fields[i])) {
//				index = i;
//			}
//		}
//		return index;
//	}
}
