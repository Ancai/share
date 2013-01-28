/**
 * 
 */
package com.share.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

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
	 * @param dataRow 包含多个sheet的数据
	 * @param titleRow 标题行
	 * @return HSSFWorkbook 工作薄
	 */
	public static HSSFWorkbook excel(Map<String, List<Object[]>> dataRow, Map<String, String[]> titleRow) {
		//创建一个空白的WorkBook
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle style = getStyle(workBook);
		HSSFCellStyle style2 = getStyle2(workBook);
		int colLength = 0;
		for (Entry<String, String[]> entry : titleRow.entrySet()) {
			//基于上面的WorkBook创建属于此WorkBook的Sheet，
			sheet = workBook.createSheet(entry.getKey());
			sheet.setDefaultColumnWidth(15);
			List<Object[]> sheetData = dataRow.get(entry.getKey());
			//创建属于上面Sheet的Row，参数0可以是0～65535之间的任何一个，
			//注意，尽管参数是Int类型，但是Excel最多支持65536行
			row = sheet.createRow(0); //标题行
			String[] titleCells = titleRow.get(entry.getKey());
			for (int i = 0; i < titleCells.length; i++) {
				cell = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style); //设置样式
				HSSFRichTextString richText = new HSSFRichTextString(titleCells[i]);
				cell.setCellValue(richText);
			}
			for (int i = 0; i < sheetData.size(); i++) {
				Object[] objs = sheetData.get(i);
				row = sheet.createRow(i+1); //数据行
				colLength = objs.length;
				for (int j = 0; j < colLength; j++) {
					cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(style2); //设置样式
				    HSSFRichTextString richText = new HSSFRichTextString(null == objs[j] ? "" : objs[j].toString());
					cell.setCellValue(richText);
				}
			}
		}
		
		return workBook;
	}
	
	/**
	 * 设置EXCEL 单元格的样式
	 * 
	 * @param workBook EXCEL工作簿
	 * @return HSSFCellStyle
	 */
	private static HSSFCellStyle getStyle(HSSFWorkbook workBook) {
		HSSFCellStyle style = workBook.createCellStyle();
		// 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workBook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        
        return style;
	}
	
	/**
	 * 设置EXCEL 单元格的样式
	 * 
	 * @param workBook EXCEL工作簿
	 * @return HSSFCellStyle
	 */
	private static HSSFCellStyle getStyle2(HSSFWorkbook workBook) {
		HSSFCellStyle style2 = workBook.createCellStyle();
		// 生成并设置另一个样式
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workBook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        
        return style2;
	}
}
