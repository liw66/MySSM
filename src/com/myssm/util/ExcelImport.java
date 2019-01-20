package com.myssm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 将excel数据装换成数据模型
 * 
 * @author
 * 
 */
public class ExcelImport {
	private static final int HEADER = 0;

	private static final int START = 1;

	private BeanStorage storage = new BeanStorage();

	private Workbook book;
	/**
	 * key:excel对应标题 ,value:对象属性
	 */
	private Map<String, String> associations;
	/**
	 * 装换失败的数据信息，记录行数
	 */
	private StringBuffer error = new StringBuffer(0);

	private Map<Integer, String> header;
	/**
	 * 默认的日期格式
	 */
	private String date_format = "yyyy-MM-dd";
	/**
	 * 类属性，存储bean中其他javabean类
	 */
	private Map<String, Object> beanPro = new HashMap<String, Object>();

	private SimpleDateFormat format;

	/**
	 * 使用给定的文件输入流初始化excel文件
	 * 
	 * @param in
	 *            excel文件输入流
	 */
	public void init(InputStream in) throws Exception {
		try {
			book = WorkbookFactory.create(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用指定文件，初始化工作簿
	 * 
	 * @param file
	 */
	public void init(File file) throws Exception {
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			book = WorkbookFactory.create(in);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ExcelImport(Map<String, String> associations) {
		this.associations = associations;
		format = new SimpleDateFormat(date_format);

	}

	public ExcelImport(Map<String, String> associations, String date_format) {
		this.associations = associations;
		this.date_format = date_format;
		format = new SimpleDateFormat(date_format);
	}

	/**
	 * 
	 * @return true 存在错误，false 不存在错误
	 */
	public boolean hasError() {
		return error.capacity() > 0;
	}

	public StringBuffer getError() {
		return error;
	}

	/**
	 * 获取第一行标题栏数据
	 * 
	 * @param sheet
	 * @return map key：标题栏列下标（0开始） value 标题栏值
	 */
	private void loadHeader(Sheet sheet) {
		this.header = new HashMap<Integer, String>();
		Row row = sheet.getRow(HEADER);
		int columns = row.getLastCellNum();
		for (int i = 0; i < columns; i++) {
			String value = row.getCell(i).getStringCellValue();
			if (null == value) {
				throw new RuntimeException("标题栏不能为空！");
			}
			header.put(i, value);
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param required
	 *            是否每个属性都是必须的
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public <T> List<T> bindToModels(Class clazz, boolean required)
			throws Exception {
		// 获取第一页
		Sheet sheet = this.book.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();// 获取行数

		if (rowNum < 1) {
			return new ArrayList<T>();
		}
		// 加载标题栏数据
		this.loadHeader(sheet);
		List<T> result = new ArrayList<T>();

		for (int i = START; i < rowNum; i++) {
			Row row = sheet.getRow(i);
			int cellNum = row.getLastCellNum();
			T instance = (T) clazz.newInstance();
			int columns = 0;
			try {
				for (; columns < cellNum; columns++) {
					Cell cell = row.getCell(columns);
					// 判断单元格的数据类型
					String value = loadCellType(cell);

					// 获取单元格的值
					if (null == value) {
						// 如果为必填的则将错误信息记录
						if (required) {
							this.error.append(
									"第" + (i + 1) + "行，" + header.get(columns)
											+ "字段，数据为空，跳过！").append("\n");
						}
					} else {
						String key = header.get(columns);
						// 加载实际值
						this.loadValue(clazz, instance,
								this.associations.get(key), value);
					}
				}
				result.add(instance);
			} catch (Exception e) {
				this.error.append(
						"第" + (i + 1) + "行，" + header.get(columns)
								+ "字段，数据错误，跳过！" + e.getMessage()).append("\n");
			}

		}

		return result;
	}

	/**
	 * 将单元格数据转换成string类型
	 * 
	 * @param cellType
	 * @param cell
	 * @return
	 */
	private String loadCellType(Cell cell) {
		String value = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			// 判断当前的cell是否为Date
			if (HSSFDateUtil.isCellDateFormatted(cell))
			{
				value = this.formateDate(cell.getDateCellValue());
			} else {
				value = String.valueOf((long) cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			//value = cell.getCellFormula();
			value = String.valueOf((long) cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			value = String.valueOf((Byte) cell.getErrorCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			value = "";
			break;
		}
		return value;
	}

	/**
	 * 注入属性值
	 * 
	 * @param instance
	 * @param pro
	 *            属性对象
	 * @param value
	 *            属性值
	 */
	@SuppressWarnings("unchecked")
	private <T> void loadValue(Class clazz, T instance, String pro, String value)
			throws SecurityException, NoSuchMethodException, Exception {
		String innerPro = null;
		if (pro.contains(".")) {
			String[] pros = pro.split("\\.");
			pro = pros[0];
			innerPro = pros[1];
			// 将成员变量的类型存储到仓库中
			storage.storeClass(instance.hashCode() + "", clazz
					.getDeclaredMethod(this.initGetMethod(pro), null)
					.getReturnType());
		}

		String getMethod = this.initGetMethod(pro);

		Class type = clazz.getDeclaredMethod(getMethod, null).getReturnType();

		Method method = clazz.getMethod(this.initSetMethod(pro), type);

		if (type == String.class) {
			method.invoke(instance, value);
		} else if (type == int.class || type == Integer.class) {
			method.invoke(instance, Integer.parseInt(value));

		} else if (type == long.class || type == Long.class) {
			method.invoke(instance, Long.parseLong(value));

		} else if (type == float.class || type == Float.class) {
			method.invoke(instance, Float.parseFloat(value));

		} else if (type == double.class || type == Double.class) {
			method.invoke(instance, Double.parseDouble(value));

		} else if (type == Date.class) {
			method.invoke(instance, this.parseDate(value));
		} else {
			// 引用类型数据
			Object ins = storage.getInstance(instance.hashCode() + "");
			this.loadValue(ins.getClass(), ins, innerPro, value);
			method.invoke(instance, ins);
		}
	}

	private Date parseDate(String value) throws ParseException {
		return format.parse(value);
	}

	private String formateDate(Date date) {
		return format.format(date);
	}

	public String initSetMethod(String field) {
		return "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}

	public String initGetMethod(String field) {
		return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}

	public String getDate_format() {
		return date_format;
	}

	public void setDate_format(String date_format) {
		this.date_format = date_format;
	}

	/**
	 * 存储bean中的bean成员变量
	 * 
	 * 
	 */
	private class BeanStorage {
		private Map<String, Class> proClass = new HashMap<String, Class>();
		private Map<String, Object> instances = new HashMap<String, Object>();

		public void storeClass(String key, Class clazz) throws Exception {
			if (!instances.containsKey(key)) {
				instances.put(key, clazz.newInstance());
				proClass.put(key, clazz);
			}
		}

		public Class loadClass(String key) {
			return proClass.get(key);
		}

		public Object getInstance(String key) {
			return instances.get(key);
		}

	}
}