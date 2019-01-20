package com.myssm.util;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelExport<T> {

	public void export(int fileType, String fileName, String[] headers,
			Collection<T> dataset, HttpServletResponse res) throws Exception {
		// 判断是2003 type=0 还是2007 type=1 的版本创建不同的工作簿
		Workbook workbook = null;
		if (fileType == 0) {
			workbook = new HSSFWorkbook();
		} else {
			workbook = new XSSFWorkbook();
		}
		export(workbook, fileName, headers, dataset, res, "yyyy-MM-dd");
	}

	/**
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd"
	 * 
	 */
	private void export(Workbook workbook, String title, String[] headers,
			Collection<T> dataset, HttpServletResponse res, String pattern)
			throws Exception {
		// 创建一个sheet页
		Sheet sheet = workbook.createSheet(title);

		// 产生表格标题行
		Row row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				Cell cell = row.createCell(i);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				Class<? extends Object> tCls = t.getClass();
				Method getMethod = tCls
						.getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(t, new Object[] {});
				// 判断值的类型后进行强制类型转换
				String textValue = null;
				if (null == value) {
					textValue = null;
				} else {
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else {
						textValue = value.toString();
					}
				}
				if (textValue != null) {
					cell.setCellValue(textValue);
				}
			}
		}
		OutputStream out = res.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();
	}	

}