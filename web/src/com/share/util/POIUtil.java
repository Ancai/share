/**
 * 
 */
package com.share.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 工具类：利用Apache开放组件POI，操纵文档
 *
 * @author liangancai email：ancai0729@gmail.com
 * @since 2013-1-24 下午12:17:54
 * @version 1.0
 */
public final class POIUtil {
	//	HSSF － 提供读写Microsoft Excel XLS格式档案的功能。
	//	XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能。
	//	HWPF － 提供读写Microsoft Word DOC格式档案的功能。
	//	HSLF － 提供读写Microsoft PowerPoint格式档案的功能。
	//	HDGF － 提供读Microsoft Visio格式档案的功能。
	//	HPBF － 提供读Microsoft Publisher格式档案的功能。
	//	HSMF － 提供读Microsoft Outlook格式档案的功能。
	/**
	 * 生成Excel表格
	 * 
	 * @param title 文件名称
	 * @param data 包含多个sheet的数据
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook excel(String title, Map<String, List<Object[]>> data) {
		//创建一个空白的WorkBook
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		int colLength = 0;
		for (Entry<String, List<Object[]>> entry : data.entrySet()) {
			//基于上面的WorkBook创建属于此WorkBook的Sheet，
			sheet = workBook.createSheet(entry.getKey());
			List<Object[]> sheetData = entry.getValue();
			for (Object[] objs : sheetData) {
				//创建属于上面Sheet的Row，参数0可以是0～65535之间的任何一个，
				//注意，尽管参数是Int类型，但是Excel最多支持65536行
				row = sheet.createRow(sheetData.size());
				colLength = objs.length;
				for (int i = 0; i < colLength; i++) {
					cell = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(String.valueOf(objs[i]));
				}
			}
			
		}
		
		return workBook;
	}
}
