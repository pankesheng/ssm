package com.ssm.utils;

import java.util.HashMap;
import java.util.Map;

import com.ssm.entity.Article;
import com.ssm.entity.Unit;
import com.ssm.entity.User;

public class CodeWrite {
	public static String dbType = "oracle";	//数据库种类  目前sql支持mysql、sqlserver  mapper.xml仅支持mysql
	public static String entityPath = "com.ssm.entity";	//实体类所在的包名
	public static String mapperPath = "com.ssm.mapper"; //mapper所在的包名 默认mapper.xml 和.java存在1个包下
	public static String servicePath = "com.ssm.service";//service层所在的包名  service.impl默认存在service下的impl
 	public static String basicMapperPath = "com.ssm.common";//basicmapper所在的包名
	public static String basicServicePath = "com.ssm.common";//basicservice 所在的包名
	public static String basicServiceImplPath = "com.ssm.common";//basicserviceimpl所在的包名
	public static String basicRepositoryPath = "com.zcj.web.mybatis.mapper";//BasicRepository所在包名 
	public static String sqlPath = "F://ssm//sql//ssm.sql";	//sql文件指定哪个文件，如果没有将自动生成文件,如果这个目录有这个文件将会重新覆盖原来的文件
	public static String filepath = "F://ssm//";	//到文件夹目录
	public static Map<String, Class<?>> map = new HashMap<String, Class<?>>();
	public static Map<String, String> beanMap = new HashMap<String, String>();
	
	public static void main(String[] args) {
		map.put("t_user", User.class);
		beanMap.put("t_user", "User");
		map.put("t_unit", Unit.class);
		beanMap.put("t_unit", "Unit");
		
		
		GenSql.printCode(map, dbType, sqlPath);
		GenMapper.printCode(map, beanMap, dbType, filepath,entityPath,mapperPath, basicMapperPath,basicRepositoryPath);
		GenService.printCode(map, beanMap, filepath, entityPath, mapperPath, servicePath, basicServicePath, basicServiceImplPath);
		
		map = new HashMap<String, Class<?>>();
		beanMap = new HashMap<String, String>();
		map.put("t_article", Article.class);
		beanMap.put("t_article", "Article");
		sqlPath = "F://ssm//sql//article.sql";
		GenSql2.printCode(map, dbType, sqlPath);
		GenMapper2.printCode(map, beanMap, dbType, filepath, entityPath, mapperPath, basicMapperPath,basicRepositoryPath);
		GenService.printCode(map, beanMap, filepath, entityPath, mapperPath, servicePath, basicServicePath, basicServiceImplPath);
	}
}
