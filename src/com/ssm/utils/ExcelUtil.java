package com.ssm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 该类实现了一组将 对象转Excel表格 和 Excel表格转对象 的功能。<br/>
 * 使用该类的前提：<br/>
 * 1、导入commons-beanutils包。<br/>
 * 2、在相应的Bean上使用ExcelReources注解。<br/>
 * 
 */
public class ExcelUtil<T> {

	@SuppressWarnings("rawtypes")
	private static ExcelUtil eu = new ExcelUtil();

	private ExcelUtil() {

	}

	/** 单例 */
	@SuppressWarnings("rawtypes")
	public static ExcelUtil getInstance() {
		return eu;
	}

	/**
	 * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到流中
	 * 
	 * @param datas
	 *            模板中的替换的常量数据<br/>
	 *            例：<br/>
	 *            Map<String, String> datas = new HashMap<String, String>();<br/>
	 *            datas.put("title","测试用户信息");<br/>
	 *            datas.put("date","2012-06-02 12:33");<br/>
	 * @param template
	 *            模板路径。例："/com/zcj/poi/excel/template_user.xls" 或 "D:/test.xls"
	 * @param os
	 *            输出流
	 * @param objs
	 *            对象列表
	 * @param clz
	 *            对象的类型
	 * @param isClasspath
	 *            模板是否在classPath路径下(true：classPath路径下；false：硬盘绝对路径下)
	 */
	public void exportObj2ExcelByTemplate(Map<String, String> datas, String template, OutputStream os, List<?> objs, Class<?> clz, boolean isClasspath) {
		ExcelTemplate et = handlerObj2Excel(template, objs, clz, isClasspath);
		et.replaceFinalData(datas);
		et.wirteToStream(os);
	}
	

	/**
	 * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到流中
	 * 
	 * @param datas
	 *            模板中的替换的常量数据<br/>
	 *            例：<br/>
	 *            Map<String, String> datas = new HashMap<String, String>();<br/>
	 *            datas.put("title","测试用户信息");<br/>
	 *            datas.put("date","2012-06-02 12:33");<br/>
	 * @param template
	 *            模板路径。例："/com/zcj/poi/excel/template_user.xls" 或 "D:/test.xls"
	 * @param os
	 *            输出流
	 * @param objs
	 *            对象列表
	 * @param clz
	 *            对象的类型
	 * @param isClasspath
	 *            模板是否在classPath路径下(true：classPath路径下；false：硬盘绝对路径下)
	 * @param printHeaders
	 *          添加printHeaders 选择项 ，即是否需要打印表头信息 true or false）
	 */
	public void exportObj2ExcelByTemplate(Map<String, String> datas, String template, OutputStream os, List<?> objs, Class<?> clz, boolean isClasspath,boolean printHeaders) {
		ExcelTemplate et = handlerObj2Excel(template, objs, clz, isClasspath,printHeaders);
		et.replaceFinalData(datas);
		et.wirteToStream(os);
	}
	
	
	/**
	 * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到一个具体的路径中
	 * 
	 * @param datas
	 *            模板中的替换的常量数据<br/>
	 *            例：<br/>
	 *            Map<String, String> datas = new HashMap<String, String>();<br/>
	 *            datas.put("title","测试用户信息");<br/>
	 *            datas.put("date","2012-06-02 12:33");<br/>
	 * @param template
	 *            模板路径。例："/com/zcj/poi/excel/template_user.xls" 或 "D:/test.xls"
	 * @param outPath
	 *            输出路径。例："D:/test.xls"
	 * @param objs
	 *            对象列表
	 * @param clz
	 *            对象的类型
	 * @param isClasspath
	 *            模板是否在classPath路径下(true：classPath路径下；false：硬盘绝对路径下)
	 */
	public void exportObj2ExcelByTemplate(Map<String, String> datas, String template, String outPath, List<?> objs, Class<?> clz, boolean isClasspath) {
		ExcelTemplate et = handlerObj2Excel(template, objs, clz, isClasspath);
		et.replaceFinalData(datas);
		et.writeToFile(outPath);
	}

	/** 根据Bean对象初始化ExcelHeader对象集合 */
	private List<ExcelHeader> getHeaderList(Class<?> clz) {
		List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
		Method[] ms = clz.getDeclaredMethods();
		for (Method m : ms) {
			String mn = m.getName();
			if (mn.startsWith("get")) {
				if (m.isAnnotationPresent(ExcelResources.class)) {
					ExcelResources er = m.getAnnotation(ExcelResources.class);
					headers.add(new ExcelHeader(er.title(), er.order(), mn));
				}
			}
		}
		return headers;
	}

	/** 根据ExcelHeader对象获取相应的属性名称 */
	private String getMethodName(ExcelHeader eh) {
		String mn = eh.getMethodName().substring(3);
		mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
		return mn;
	}

	/**
	 * 根据模板文件往ExcelTemplate里加数据
	 * 
	 * @param template
	 * @param objs
	 * @param clz
	 * @param isClasspath
	 * @return
	 */
	private ExcelTemplate handlerObj2Excel(String template, List<?> objs, Class<?> clz, boolean isClasspath) {
		ExcelTemplate et = ExcelTemplate.getInstance();
		if (isClasspath) {
			et.readTemplateByClasspath(template);
		} else {
			et.readTemplateByPath(template);
		}
		List<ExcelHeader> headers = getHeaderList(clz);
		Collections.sort(headers);
		et.createNewRow();
		// 输出标题
		for (ExcelHeader eh : headers) {
			et.createCell(eh.getTitle());
		}
		// 输出值
		for (Object obj : objs) {
			et.createNewRow();
			for (ExcelHeader eh : headers) {
				try {
					Object value=PropertyUtils.getProperty(obj, getMethodName(eh));
					if(value instanceof Integer){
						et.createCell(((Integer) value).intValue());
					}else if(value instanceof String){
						if(((String) value).contains("\r\n")){
							et.createCellLn((String)value);
						}else{
							et.createCell((String)value);
						}
					}else if(value instanceof byte[]){
						et.createCell((byte[])value);
						//判断是否是文本超链接
					}else if(value instanceof TextLink){
						et.createCell((TextLink)value);
					}else if(value==null){
						et.createCell("");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("BeanUtils获取属性值失败");
				}
			}
		}
		return et;
	}
	
	
	private ExcelTemplate handlerObj2Excel(String template, List<?> objs, Class<?> clz, boolean isClasspath,boolean printHeaders) {
		ExcelTemplate et = ExcelTemplate.getInstance();
		if (isClasspath) {
			et.readTemplateByClasspath(template);
		} else {
			et.readTemplateByPath(template);
		}
		List<ExcelHeader> headers = getHeaderList(clz);
		Collections.sort(headers);
		if(printHeaders==true){
			et.createNewRow();
			// 输出标题
			for (ExcelHeader eh : headers) {
				et.createCell(eh.getTitle());
			}
		}
		// 输出值
		for (Object obj : objs) {
			et.createNewRow();
			for (ExcelHeader eh : headers) {
				try {
					Object value=PropertyUtils.getProperty(obj, getMethodName(eh));
					if(value instanceof Boolean){
						if(value==null||(Boolean)value==false){
							et.createCell("否");
						}else{
							et.createCell("是");
						}
					}else if(value instanceof Integer){
						et.createCell(((Integer) value).intValue());
					}else if(value instanceof String){
						if(((String) value).contains("\r\n")){
							et.createCellLn((String)value);
						}else{
							et.createCell((String)value);
						}
					}else if(value instanceof byte[]){
						et.createCell((byte[])value);
						//判断是否是文本超链接
					}else if(value instanceof TextLink){
						et.createCell((TextLink)value);
					}else if(value instanceof Float){
						et.createCell((float)value);
					}else if(value instanceof Double){
						et.createCell((double)value);
					}else if(value==null){
						et.createCell("");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("BeanUtils获取属性值失败");
				}
			}
		}
		return et;
	}

	/**
	 * 通过对象导出Excel到指定路径
	 * 
	 * @param outPath
	 *            导出路径。例：D:/test.xls
	 * @param objs
	 *            对象列表
	 * @param clz
	 *            对象类型
	 * @param isXssf
	 *            是否是2007版本(xlsx)
	 */
	public void exportObj2Excel(String outPath, List<?> objs, Class<?> clz, boolean isXssf) {
		Workbook wb = handleObj2Excel(objs, clz, isXssf);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outPath);
			wb.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(outPath + "文件路径不存在");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("文件写入失败");
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("输出流关闭失败");
			}
		}
	}

	/**
	 * 通过对象导出Excel到输出流
	 * 
	 * @param os
	 *            输出流
	 * @param objs
	 *            对象列表
	 * @param clz
	 *            对象类型
	 * @param isXssf
	 *            是否是2007版本(xlsx)
	 */
	public void exportObj2Excel(OutputStream os, List<?> objs, Class<?> clz, boolean isXssf) {
		try {
			Workbook wb = handleObj2Excel(objs, clz, isXssf);
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("文件写入失败");
		}
	}

	/** 通过对象创建Workbook */
	private Workbook handleObj2Excel(List<?> objs, Class<?> clz, boolean isXssf) {
		Workbook wb = null;
		if (isXssf) {
			wb = new XSSFWorkbook();
		} else {
			wb = new HSSFWorkbook();
		}
		Sheet sheet = wb.createSheet();
		Row r = sheet.createRow(0);
		List<ExcelHeader> headers = getHeaderList(clz);
		Collections.sort(headers);
		// 写标题
		for (int i = 0; i < headers.size(); i++) {
			r.createCell(i).setCellValue(headers.get(i).getTitle());
		}
		// 写数据
		Object obj = null;
		for (int i = 0; i < objs.size(); i++) {
			r = sheet.createRow(i + 1);
			obj = objs.get(i);
			for (int j = 0; j < headers.size(); j++) {
				try {
					r.createCell(j).setCellValue(BeanUtils.getProperty(obj, getMethodName(headers.get(j))));
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("BeanUtils获取属性值失败");
				}
			}
		}
		return wb;
	}

	/** 根据Excel里的标题行组装成一个Map（key：列数；value：此列的值对应的方法名） */
	private Map<Integer, String> getHeaderMap(Row titleRow, Class<?> clz) {
		List<ExcelHeader> headers = getHeaderList(clz);
		Map<Integer, String> maps = new HashMap<Integer, String>();
		for (Cell c : titleRow) {
			String title = c.getStringCellValue();
			for (ExcelHeader eh : headers) {
				if (eh.getTitle().equals(title.trim())) {
					maps.put(c.getColumnIndex(), eh.getMethodName().replace("get", "set"));
					break;
				}
			}
		}
		return maps;
	}

	/** 取得单元格的字符串值 */
	private String getCellValue(Cell c) {
		String o = null;
		switch (c.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			o = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			o = String.valueOf(c.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			o = String.valueOf(c.getCellFormula());
			break;
		case Cell.CELL_TYPE_NUMERIC:// 如果是数值，则取整返回
			o = String.format("%.0f", c.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			o = c.getStringCellValue();
			break;
		default:
			o = null;
			break;
		}
		return o;
	}

	/** 通过Workbook创建对象集合 */
	@SuppressWarnings({ "hiding", "unchecked" })
	private <T> List<T> handlerExcel2Objs(Workbook wb, Class<?> clz, int readLine, int tailLine) {
		Sheet sheet = wb.getSheetAt(0);
		List<T> objs = null;
		try {
			Row row = sheet.getRow(readLine);
			objs = new ArrayList<T>();
			Map<Integer, String> maps = getHeaderMap(row, clz);
			if (maps == null || maps.size() <= 0)
				throw new RuntimeException("Excel的标题行读取错误");
			Field[] fields = clz.getDeclaredFields();
			// List<String> fieldNameList = new
			// ArrayList<String>(fields.length);
			Map<String, Field> fieldMap = new HashMap<String, Field>();

			for (Field field : fields) {
				// System.out.println(field.getName());
				// fieldNameList.add(field.getName());
				field.setAccessible(true);
				fieldMap.put(field.getName().toLowerCase(), field);
			}

			for (int i = readLine + 1; i <= sheet.getLastRowNum() - tailLine; i++) {
				row = sheet.getRow(i);
				T obj = (T) clz.newInstance();
				for (Cell c : row) {
					int ci = c.getColumnIndex();
					String mn = maps.get(ci);
					if (mn == null)
						continue;
					mn = mn.substring(3);
					// 增加兼容性
					Field field = fieldMap.get(mn.toLowerCase());
					
					setFieldValue(field, obj, this.getCellValue(c));

				}
				objs.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("通过Workbook创建对象集合失败");
		}
		return objs;
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	private <T> Map<String, List<T>> handlerExcel2MultiObjs(Workbook wb, Map<String, Class<?>> clzMap, int readLine, int tailLine) {
		Map<String, List<T>> map = new HashMap<String, List<T>>();
		int sheetLength = wb.getNumberOfSheets();
		for (int si = 0; si < sheetLength; si++) {
			Sheet sheet = wb.getSheetAt(si);
			List<T> objs = null;
			try {
				Row row = sheet.getRow(readLine);
				objs = new ArrayList<T>();
				map.put(sheet.getSheetName(), objs);
				Class<?> clz = clzMap.get(sheet.getSheetName());
				if (null == clz)
					continue;
				Map<Integer, String> maps = getHeaderMap(row, clz);
				if (maps == null || maps.size() <= 0)
					throw new RuntimeException("Excel的标题行读取错误");
				Field[] fields = clz.getDeclaredFields();
				// List<String> fieldNameList = new
				// ArrayList<String>(fields.length);
				Map<String, Field> fieldMap = new HashMap<String, Field>();

				for (Field field : fields) {
					// System.out.println(field.getName());
					// fieldNameList.add(field.getName());
					field.setAccessible(true);
					fieldMap.put(field.getName().toLowerCase(), field);
				}

				for (int i = readLine + 1; i <= sheet.getLastRowNum() - tailLine; i++) {
					row = sheet.getRow(i);
					T obj = (T) clz.newInstance();
					for (Cell c : row) {
						int ci = c.getColumnIndex();
						String mn = maps.get(ci);
						if (null == mn)
							continue;
						mn = mn.substring(3);
						// 增加兼容性
						Field field = fieldMap.get(mn.toLowerCase());
						
						setFieldValue(field, obj, this.getCellValue(c));
					}
					objs.add(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("通过Workbook创建对象集合失败");
			}
		}
		// return objs;

		return map;
	}

	private void setFieldValue(Field field, Object obj, String value) throws IllegalArgumentException, IllegalAccessException {
		if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
			field.setInt(obj, Integer.parseInt(value));
		} else if (field.getType().equals(byte.class) || field.getType().equals(Byte.class)) {
			field.setByte(obj, Byte.parseByte(value));
		} else if (field.getType().equals(char.class) || field.getType().equals(Character.class)) {
			field.setChar(obj, (char) Byte.parseByte(value));
		} else if (field.getType().equals(short.class) || field.getType().equals(Short.class)) {
			field.setShort(obj, Short.parseShort(value));
		} else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
			field.setLong(obj, Long.parseLong(value));
		} else if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
			field.setFloat(obj, Float.parseFloat(value));
		} else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
			field.setDouble(obj, Double.parseDouble(value));
		} else {
			field.set(obj, value);
		}
	}

	/**
	 * 从类路径读取相应的Excel文件到对象列表
	 * 
	 * @param path
	 *            类路径下的path。例："/com/zcj/poi/excel/template_user.xls"
	 * @param clz
	 *            对象类型
	 * @param readLine
	 *            标题所在行（下标从0开始）
	 * @param tailLine
	 *            排除列表底部多少行
	 * @return
	 */
	public List<T> readExcel2ObjsByClasspath(String path, Class<T> clz, int readLine, int tailLine) {
		Workbook wb;
		try {
			wb = WorkbookFactory.create(ExcelUtil.class.getResourceAsStream(path));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件读取失败");
		}
		return handlerExcel2Objs(wb, clz, readLine, tailLine);
	}

	/**
	 * 从文件路径读取相应的Excel文件到对象列表
	 * 
	 * @param path
	 *            存储路径。例：D:/test.xls
	 * @param clz
	 *            对象类型
	 * @param readLine
	 *            标题所在行（下标从0开始）
	 * @param tailLine
	 *            排除列表底部多少行
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> List<T> readExcel2ObjsByPath(String path, Class<?> clz, int readLine, int tailLine) {
		Workbook wb;
		try {
			wb = WorkbookFactory.create(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件读取失败");
		}
		return handlerExcel2Objs(wb, clz, readLine, tailLine);
	}

	@SuppressWarnings("hiding")
	public <T> Map<String, List<T>> readExcel2MultiObjsByPath(String path, Map<String, Class<?>> clzMap) {
		Workbook wb;
		try {
			wb = WorkbookFactory.create(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件读取失败");
		}
		return handlerExcel2MultiObjs(wb, clzMap, 0, 0);
	}

	/**
	 * 从类路径读取相应的Excel文件到对象列表(必须为第一行是标题行，剩下的都是数据行的标准Excel文件)
	 * 
	 * @param path
	 *            类路径下的path。例："/com/zcj/poi/excel/template_user.xls"
	 * @param clz
	 * @return
	 */
	public List<T> readExcel2ObjsByClasspath(String path, Class<T> clz) {
		return this.readExcel2ObjsByClasspath(path, clz, 0, 0);
	}

	/**
	 * 从文件路径读取相应的Excel文件到对象列表(必须为第一行是标题行，剩下的都是数据行的标准Excel文件)
	 * 
	 * @param path
	 *            存储路径。例：D:/test.xls
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> List<T> readExcel2ObjsByPath(String path, Class<?> clz) {
		return this.readExcel2ObjsByPath(path, clz, 0, 0);
	}


}
