package com.ssm.utils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * 不依赖DBFiled 生成数据库建表语句
 * @author pks
 *
 */
public class NoDBfieldCodeMySql {

	//实体属性必须要加上 dbfield注解
	public static void printCode(Map<String, Class<?>> map,String dbType,String sqlPath) {
		StringBuilder sb = new StringBuilder();
		if(dbType.equals("mysql")){
			sb = printMysql(map);
		}else if(dbType.equals("sqlserver")){
	        sb = printSqlserver(map);
		}else if(dbType.equals("oracle")){
			sb = printOracle(map);
		}
		if(StringUtils.isNotBlank(sqlPath)){
			WriteFile.contentToTxt(sqlPath,sb.toString());
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static StringBuilder printOracle(Map<String, Class<?>> map) {
		Set key = map.keySet();
		StringBuilder sb = new StringBuilder();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String tableName = (String) it.next();
            Class<?> className = map.get(tableName);
            sb.append("\r\ndeclare");
            sb.append("\r\n	num number;");
            sb.append("\r\nbegin");
            sb.append("\r\n select count(*) into num from all_tables where TABLE_NAME = '"+tableName.toUpperCase()+"';");
            sb.append("\r\n	if num =1 then ");
            sb.append("\r\n		execute immediate 'drop table "+tableName.toUpperCase()+"';");
            sb.append("\r\n	end if;");
            sb.append("\r\nend;");
            sb.append("\r\n/");
    		sb.append("\r\nCREATE TABLE "+tableName+" (");
    		sb.append("\r\nid number(38) primary key  not null,");
    		Field[] fields = className.getDeclaredFields();
    		Field[] ffields = className.getSuperclass().getDeclaredFields(); 
    		//获得父类属性进行输出sql
    		for (Field field : ffields) {
    			if(field.getName().equals("id")){
					continue;
				}
				if ("class java.lang.Integer".equals(field.getType().toString())) {
    				sb.append("\r\n  "+field.getName()+" number(10) ,");
    			} else if ("class java.lang.Long".equals(field.getType().toString())) {
    				sb.append("\r\n  "+field.getName()+" number(38) ,");
    			} else if ("class java.lang.String".equals(field.getType().toString())) {
					sb.append("\r\n  "+field.getName()+" nvarchar2(100) ,");
    			} else if ("class java.util.Date".equals(field.getType().toString())) {
    				sb.append("\r\n  "+field.getName()+" date ,");
    			}
    		}
    		//获得类属性输出sql
    		for (Field field : fields) {
				if(field.getName().equals("id")){
					continue;
				}
				if ("class java.lang.Integer".equals(field.getType().toString())) {
    				sb.append("\r\n  "+field.getName()+" number(10) ,");
    			} else if ("class java.lang.Long".equals(field.getType().toString())) {
    				sb.append("\r\n  "+field.getName()+" number(38) ,");
    			} else if ("class java.lang.String".equals(field.getType().toString())) {
					sb.append("\r\n  "+field.getName()+" nvarchar2(100) ,");
    			} else if ("class java.util.Date".equals(field.getType().toString())) {
    				sb.append("\r\n  "+field.getName()+" date ,");
    			}
    		}
    		String str = sb.toString();
    		sb = new StringBuilder(str.substring(0, str.lastIndexOf(",")));
    		sb.append("\r\n)");
    		sb.append("\r\n/");
    		sb.append("\r\n");
        }
        return sb;
	}

	/**
	 * 组织sqlserver数据库mapper文件内容
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static StringBuilder printSqlserver(Map<String, Class<?>> map) {
		Set key = map.keySet();
		StringBuilder sb = new StringBuilder();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String tableName = (String) it.next();
            Class<?> className = map.get(tableName);
    		sb.append("\r\nif exists (select * from dbo.sysobjects where id = object_id(N'[dbo].["+tableName+"]') and OBJECTPROPERTY(id, N'IsUserTable') = 1) ");
    		sb.append("\r\ndrop table [dbo].["+tableName+"]");
    		sb.append("\r\nGO");
    		sb.append("\r\nCREATE TABLE [dbo].["+tableName+"] (");
    		Field[] fields = className.getDeclaredFields();
    		Field[] ffields = className.getSuperclass().getDeclaredFields(); 
    		//获得父类属性进行输出sql
    		for (Field field : ffields) {
				if ("class java.lang.Integer".equals(field.getType().toString())) {
    				sb.append("\r\n  ["+field.getName()+"] int ,");
    			} else if ("class java.lang.Long".equals(field.getType().toString())) {
    				sb.append("\r\n  ["+field.getName()+"] bigint ,");
    			} else if ("class java.lang.String".equals(field.getType().toString())) {
					sb.append("\r\n  ["+field.getName()+"] nvarchar(100) ,");
    			} else if ("class java.util.Date".equals(field.getType().toString())) {
    				sb.append("\r\n  ["+field.getName()+"] datetime,");
    			}
    		}
    		/*sb.append("\r\n  `id` bigint(20) not null,");*/
    		//获得类属性输出sql
    		for (Field field : fields) {
				if ("class java.lang.Integer".equals(field.getType().toString())) {
    				sb.append("\r\n  ["+field.getName()+"] int ,");
    			} else if ("class java.lang.Long".equals(field.getType().toString())) {
    				sb.append("\r\n  ["+field.getName()+"] bigint ,");
    			} else if ("class java.lang.String".equals(field.getType().toString())) {
					sb.append("\r\n  ["+field.getName()+"] nvarchar(100) ,");
    			} else if ("class java.util.Date".equals(field.getType().toString())) {
    				sb.append("\r\n  ["+field.getName()+"] datetime,");
    			}
    		}
    		sb.append("\r\n  PRIMARY KEY ([id])");
    		sb.append("\r\n)");
    		sb.append("\r\nGO");
    		sb.append("\r\n");
        }
        return sb;
	}

	/**
	 * 组织mysql数据库mapper文件内容
	 * @param map
	 * @param sqlPath
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static StringBuilder printMysql(Map<String, Class<?>> map) {
		Set key = map.keySet();
		StringBuilder sb = new StringBuilder();
		for (Iterator it = key.iterator(); it.hasNext();) {
            String tableName = (String) it.next();
            Class<?> className = map.get(tableName);
    		sb.append("\r\nDROP TABLE IF EXISTS `"+tableName+"`;");
    		sb.append("\r\nCREATE TABLE `"+tableName+"` (");
    		Field[] fields = className.getDeclaredFields();
    		Field[] ffields = className.getSuperclass().getDeclaredFields(); 
    		//获得父类属性进行输出sql
    		for (Field field : ffields) {
				if ("class java.lang.Integer".equals(field.getType().toString())) {
    				sb.append("\r\n  `"+field.getName()+"` int(3) ,");
    			} else if ("class java.lang.Long".equals(field.getType().toString())) {
    				sb.append("\r\n  `"+field.getName()+"` bigint(20) ,");
    			} else if ("class java.lang.String".equals(field.getType().toString())) {
					sb.append("\r\n  `"+field.getName()+"` varchar(100) ,");
    			} else if ("class java.util.Date".equals(field.getType().toString())) {
    				sb.append("\r\n  `"+field.getName()+"` datetime ,");
    			}
    		}
    		for (Field field : fields) {
				if ("class java.lang.Integer".equals(field.getType().toString())) {
    				sb.append("\r\n  `"+field.getName()+"` int(3) ,");
    			} else if ("class java.lang.Long".equals(field.getType().toString())) {
    				sb.append("\r\n  `"+field.getName()+"` bigint(20) ,");
    			} else if ("class java.lang.String".equals(field.getType().toString())) {
					sb.append("\r\n  `"+field.getName()+"` varchar(100) ,");
    			} else if ("class java.util.Date".equals(field.getType().toString())) {
    				sb.append("\r\n  `"+field.getName()+"` datetime ,");
    			}
    		}
    		sb.append("\r\n  PRIMARY KEY (`id`)");
    		sb.append("\r\n) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
    		sb.append("\r\n\r\n");
        }
		return sb;
	}
}
