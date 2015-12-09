package com.ssm.utils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class CodeMyMapperAndXml {
	
	public static void printCode(Map<String, Class<?>> map,Map<String, String> beanMap,String dbType,String filepath,String entityPath,String mapperPath,String basicMapperPath, String basicRepositoryPath) {
		if(dbType.equals("mysql")){
//			sysoMysqlMapper(map, beanMap, basicMapperPath);
			printMysqlMapper(map, beanMap, entityPath, mapperPath, basicMapperPath,basicRepositoryPath, filepath);
		}else if(dbType.equals("sqlserver")){
			printSqlserverMapper(map, beanMap, entityPath, mapperPath, basicMapperPath,basicRepositoryPath, filepath);
		}else{
			printOracleMapper(map, beanMap, entityPath, mapperPath, basicMapperPath,basicRepositoryPath, filepath);
		}
	}
	
	/**
	 * 生成oracle数据库mapper文件
	 * @param map
	 * @param beanMap
	 * @param entityPath
	 * @param mapperPath
	 * @param basicMapperPath
	 * @param filepath
	 * @param filepath2 
	 */
	@SuppressWarnings("rawtypes")
	private static void printOracleMapper(Map<String, Class<?>> map,Map<String, String> beanMap, String entityPath, String mapperPath,String basicMapperPath,String basicRepositoryPath, String filepath) {
		Set key = map.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
        	StringBuilder sb = new StringBuilder();
            String tableName = (String) it.next();
            Class<?> className = map.get(tableName);
            String beanName = beanMap.get(tableName);
			if(StringUtils.isNotBlank(beanName)){
				Field[] fields = className.getDeclaredFields();
				Field[] ffields = className.getSuperclass().getDeclaredFields(); 
				/**
				 * 组织打印用的内容
				 */
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("\r\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
				sb.append("\r\n<mapper namespace=\""+mapperPath+"."+beanName+"Mapper\">");
				sb.append("\r\n	<!-- <cache /> -->");
				sb.append("\r\n	<!-- 大于等于(&gt;=);小于等于(&lt;=);IS NOT NULL; -->");
				sb.append("\r\n	<sql id=\"qbuilder\">");
				sb.append("\r\n		<where>");
				sb.append("\r\n			<if test=\"qbuilder.id != null\">"+tableName+".id = #{qbuilder.id}</if>");
				sb.append("\r\n			<if test=\"qbuilder.ids != null\">AND "+tableName+".id in ");
				sb.append("\r\n				<foreach item=\"id\" index=\"index\" collection=\"qbuilder.ids\" open=\"(\" separator=\",\" close=\")\">");
				sb.append("\r\n					#{id}");
				sb.append("\r\n				</foreach>");
				sb.append("\r\n			</if>");
				sb.append("\r\n			<if test=\"qbuilder.nativesql !=null\">");
				sb.append("\r\n				AND ( ${qbuilder.nativesql} )");
				sb.append("\r\n			</if>");
				sb.append("\r\n		</where>");
				sb.append("\r\n	</sql>");
				sb.append("\r\n");
				sb.append("\r\n	<select id=\"find\" resultType=\""+beanName+"\">");
				
				sb.append("\r\n	<choose> ");
				sb.append("\r\n		<when test=\"start != null and size != null\">");
				sb.append("\r\n			SELECT * FROM (SELECT A.*, ROWNUM RN FROM (");
				sb.append("\r\n				select * from "+tableName);
				sb.append("\r\n					<if test=\"qbuilder!=null\">");
				sb.append("\r\n						<include refid=\"qbuilder\"/>");
				sb.append("\r\n					</if>");
				sb.append("\r\n					<if test=\"orderby!=null\">");
				sb.append("\r\n						order by ${orderby}");
				sb.append("\r\n					</if>");
				sb.append("\r\n			) A WHERE ROWNUM &lt;=#{size}+#{start} ) WHERE RN &gt;#{start}");
				sb.append("\r\n		</when>");
				sb.append("\r\n		<when test=\"start == null and size != null\">");
				sb.append("\r\n			SELECT * FROM (");
				sb.append("\r\n				select * from "+tableName);
				sb.append("\r\n					<if test=\"qbuilder!=null\">");
				sb.append("\r\n						<include refid=\"qbuilder\"/>");
				sb.append("\r\n					</if>");
				sb.append("\r\n					<if test=\"orderby!=null\">");
				sb.append("\r\n						order by ${orderby}");
				sb.append("\r\n					</if>");
				sb.append("\r\n			) WHERE ROWNUM &lt;=#{size}");
				sb.append("\r\n		</when>");
				sb.append("\r\n		<otherwise>");
				sb.append("\r\n			select * from "+tableName);
				sb.append("\r\n				<if test=\"qbuilder!=null\">");
				sb.append("\r\n					<include refid=\"qbuilder\"/>");
				sb.append("\r\n				</if>");
				sb.append("\r\n				<if test=\"orderby!=null\">");
				sb.append("\r\n					order by ${orderby}");
				sb.append("\r\n				</if>");
				sb.append("\r\n		</otherwise>");
				sb.append("\r\n	</choose>");
				
				sb.append("\r\n	</select>");
				sb.append("\r\n");
				sb.append("\r\n	<select id=\"getTotalRows\" resultType=\"int\">");
				sb.append("\r\n		SELECT COUNT(*) FROM "+tableName+"");
				sb.append("\r\n		<if test=\"qbuilder != null\">");
				sb.append("\r\n			<include refid=\"qbuilder\"/>");
				sb.append("\r\n		</if>");
				sb.append("\r\n	</select>");
				sb.append("\r\n");
				sb.append("\r\n	<select id=\"findById\" resultType=\""+beanName+"\">");
				sb.append("\r\n		SELECT * FROM "+tableName+" WHERE id = #{id}");
				sb.append("\r\n	</select>");
				sb.append("\r\n");
				sb.append("\r\n	<insert id=\"insert\">");
				sb.append("\r\n		INSERT INTO "+tableName);
				sb.append("\r\n			(id");
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(","+field.getName());
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(","+field.getName());
						}
					}
				}
				sb.append(")");
				sb.append("\r\n		VALUES");
				sb.append("\r\n			(#{object.id}");
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(",#{object."+field.getName()+"}");
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(",#{object."+field.getName()+"}");
						}
					}
				}
				sb.append(")");
				sb.append("\r\n	</insert>");
				sb.append("\r\n");
				sb.append("\r\n	<update id=\"update\">");
				sb.append("\r\n		UPDATE "+tableName);
				sb.append("\r\n		SET");
				sb.append("\r\n");
				int count1 = 0 ;
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							if(count1>0){
								sb.append(",\r\n");
							}
							sb.append("			"+field.getName()+" = #{object."+field.getName()+"}");
							count1 ++;
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							if(count1>0){
								sb.append(",\r\n");
							}
							sb.append("			"+field.getName()+" = #{object."+field.getName()+"}");
							count1 ++;
						}
					}
				}
				sb.append("\r\n		WHERE");
				sb.append("\r\n			id = #{object.id}");
				sb.append("\r\n	</update>");
				sb.append("\r\n");
				sb.append("\r\n	<delete id=\"delete\">");
				sb.append("\r\n		DELETE FROM "+tableName+" WHERE id = #{id}");
				sb.append("\r\n	</delete>");
				sb.append("\r\n");
				sb.append("\r\n	<delete id=\"deleteByIds\">");
				sb.append("\r\n		DELETE FROM "+tableName+" WHERE id in ");
				sb.append("\r\n		<foreach item=\"id\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">");
				sb.append("\r\n			#{id}");
				sb.append("\r\n		</foreach>");
				sb.append("\r\n	</delete>");
				sb.append("\r\n");
				sb.append("\r\n	<delete id=\"cleanTable\">");
				sb.append("\r\n		DELETE FROM "+tableName);
				sb.append("\r\n	</delete>");
				sb.append("\r\n</mapper>");
				WriteFile.contentToTxt(filepath+"mapper/"+beanName+"Mapper.xml", sb.toString());
				StringBuilder sb2 = new StringBuilder();
				sb2.append("package "+mapperPath+";");
				sb2.append("\r\nimport "+basicMapperPath+".BasicMapper;");
				sb2.append("\r\nimport "+entityPath+"."+beanName+";");
				sb2.append("\r\nimport "+basicRepositoryPath+".BasicRepository;");
				sb2.append("\r\n@BasicRepository");
				sb2.append("\r\npublic interface "+beanName+"Mapper extends BasicMapper<"+beanName+",Long>{");
				sb2.append("\r\n");
				sb2.append("\r\n}");
				WriteFile.contentToTxt(filepath+"mapper/"+beanName+"Mapper.java", sb2.toString());	
				System.out.println("--------------------"+tableName+"--end---------------------------");
			}
        }
	}

	/**
	 * 生成mysqlmapper文件
	 * @param key
	 * @param map
	 * @param beanMap
	 * @param entityPath
	 * @param basicMapperPath
	 * @param mapperPath
	 * @param filepath
	 * @param filepath2 
	 */
	@SuppressWarnings("rawtypes")
	public static void printMysqlMapper(Map<String, Class<?>> map,Map<String, String> beanMap,String entityPath,String mapperPath,String basicMapperPath,String basicRepositoryPath,String filepath){
		Set key = map.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
        	StringBuilder sb = new StringBuilder();
            String tableName = (String) it.next();
            Class<?> className = map.get(tableName);
            String beanName = beanMap.get(tableName);
			if(StringUtils.isNotBlank(beanName)){
				Field[] fields = className.getDeclaredFields();
				Field[] ffields = className.getSuperclass().getDeclaredFields(); 
				/**
				 * 组织打印用的内容
				 */
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("\r\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
				sb.append("\r\n<mapper namespace=\""+mapperPath+"."+beanName+"Mapper\">");
				sb.append("\r\n	<!-- <cache /> -->");
				sb.append("\r\n	<!-- 大于等于(&gt;=);小于等于(&lt;=);IS NOT NULL; -->");
				sb.append("\r\n	<sql id=\"qbuilder\">");
				sb.append("\r\n		<where>");
				sb.append("\r\n			<if test=\"qbuilder.id != null\">"+tableName+".id = #{qbuilder.id}</if>");
				sb.append("\r\n			<if test=\"qbuilder.ids != null\">AND "+tableName+".id in ");
				sb.append("\r\n				<foreach item=\"id\" index=\"index\" collection=\"qbuilder.ids\" open=\"(\" separator=\",\" close=\")\">");
				sb.append("\r\n					#{id}");
				sb.append("\r\n				</foreach>");
				sb.append("\r\n			</if>");
				sb.append("\r\n			<if test=\"qbuilder.nativesql !=null\">");
				sb.append("\r\n				AND ( ${qbuilder.nativesql} )");
				sb.append("\r\n			</if>");
				sb.append("\r\n		</where>");
				sb.append("\r\n	</sql>");
				sb.append("\r\n");
				sb.append("\r\n	<select id=\"find\" resultType=\""+beanName+"\">");
				sb.append("\r\n		SELECT * FROM "+tableName+"");
				sb.append("\r\n		<if test=\"qbuilder != null\">");
				sb.append("\r\n			<include refid=\"qbuilder\"/>");
				sb.append("\r\n		</if>");
				sb.append("\r\n		<choose>");
				sb.append("\r\n			<when test=\"orderby != null\">");
				sb.append("\r\n				ORDER BY "+tableName+".${orderby}");
				sb.append("\r\n			</when>");
				sb.append("\r\n			<otherwise>");
				sb.append("\r\n				ORDER BY "+tableName+".id");
				sb.append("\r\n			</otherwise>");
				sb.append("\r\n		</choose>");
				sb.append("\r\n		<choose>");
				sb.append("\r\n			<when test=\"start != null and size != null\">");
				sb.append("\r\n				LIMIT #{start},#{size}");
				sb.append("\r\n			</when>");
				sb.append("\r\n			<when test=\"start == null and size != null\">");
				sb.append("\r\n				LIMIT #{size}");
				sb.append("\r\n			</when>");
				sb.append("\r\n		</choose>");
				sb.append("\r\n	</select>");
				sb.append("\r\n");
				sb.append("\r\n	<select id=\"getTotalRows\" resultType=\"int\">");
				sb.append("\r\n		SELECT COUNT(*) FROM "+tableName+"");
				sb.append("\r\n		<if test=\"qbuilder != null\">");
				sb.append("\r\n			<include refid=\"qbuilder\"/>");
				sb.append("\r\n		</if>");
				sb.append("\r\n	</select>");
				sb.append("\r\n");
				sb.append("\r\n	<select id=\"findById\" resultType=\""+beanName+"\">");
				sb.append("\r\n		SELECT * FROM "+tableName+" WHERE id = #{id}");
				sb.append("\r\n	</select>");
				sb.append("\r\n");
				sb.append("\r\n	<insert id=\"insert\">");
				sb.append("\r\n		INSERT INTO "+tableName);
				sb.append("\r\n			(id");
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(","+field.getName());
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(","+field.getName());
						}
					}
				}
				sb.append(")");
				sb.append("\r\n		VALUES");
				sb.append("\r\n			(#{object.id}");
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(",#{object."+field.getName()+"}");
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(",#{object."+field.getName()+"}");
						}
					}
				}
				sb.append(")");
				sb.append("\r\n	</insert>");
				sb.append("\r\n");
				sb.append("\r\n	<update id=\"update\">");
				sb.append("\r\n		UPDATE "+tableName);
				sb.append("\r\n		SET");
				sb.append("\r\n");
				int count1 = 0 ;
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							if(count1>0){
								sb.append(",\r\n");
							}
							sb.append("			"+field.getName()+" = #{object."+field.getName()+"}");
							count1 ++;
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							if(count1>0){
								sb.append(",\r\n");
							}
							sb.append("			"+field.getName()+" = #{object."+field.getName()+"}");
							count1 ++;
						}
					}
				}
				sb.append("\r\n		WHERE");
				sb.append("\r\n			id = #{object.id}");
				sb.append("\r\n	</update>");
				sb.append("\r\n");
				sb.append("\r\n	<delete id=\"delete\">");
				sb.append("\r\n		DELETE FROM "+tableName+" WHERE id = #{id}");
				sb.append("\r\n	</delete>");
				sb.append("\r\n");
				sb.append("\r\n	<delete id=\"deleteByIds\">");
				sb.append("\r\n		DELETE FROM "+tableName+" WHERE id in ");
				sb.append("\r\n		<foreach item=\"id\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">");
				sb.append("\r\n			#{id}");
				sb.append("\r\n		</foreach>");
				sb.append("\r\n	</delete>");
				sb.append("\r\n");
				sb.append("\r\n	<delete id=\"cleanTable\">");
				sb.append("\r\n		DELETE FROM "+tableName);
				sb.append("\r\n	</delete>");
				sb.append("\r\n</mapper>");
				WriteFile.contentToTxt(filepath+"mapper/"+beanName+"Mapper.xml", sb.toString());
				StringBuilder sb2 = new StringBuilder();
				sb2.append("package "+mapperPath+";");
				sb2.append("\r\nimport "+basicMapperPath+".BasicMapper;");
				sb2.append("\r\nimport "+entityPath+"."+beanName+";");
				sb2.append("\r\nimport "+basicRepositoryPath+".BasicRepository;");
				sb2.append("\r\n@BasicRepository");
				sb2.append("\r\npublic interface "+beanName+"Mapper extends BasicMapper<"+beanName+",Long>{");
				sb2.append("\r\n");
				sb2.append("\r\n}");
				WriteFile.contentToTxt(filepath+"mapper/"+beanName+"Mapper.java", sb2.toString());	
				System.out.println("--------------------"+tableName+"--end---------------------------");
			}
        }
	}
	
	@SuppressWarnings("rawtypes")
	public static void printSqlserverMapper(Map<String, Class<?>> map,Map<String, String> beanMap,String entityPath,String mapperPath,String basicMapperPath,String basicRepositoryPath,String filepath){
		Set key = map.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
        	StringBuilder sb = new StringBuilder();
            String tableName = (String) it.next();
            Class<?> className = map.get(tableName);
            String beanName = beanMap.get(tableName);
			if(StringUtils.isNotBlank(beanName)){
				Field[] fields = className.getDeclaredFields();
				Field[] ffields = className.getSuperclass().getDeclaredFields(); 
				/**
				 * 组织打印用的内容
				 */
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("\r\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
				sb.append("\r\n<mapper namespace=\""+mapperPath+"."+beanName+"Mapper\">");
				sb.append("\r\n	<!-- <cache /> -->");
				sb.append("\r\n	<!-- 大于等于(&gt;=);小于等于(&lt;=);IS NOT NULL; -->");
				sb.append("\r\n	<sql id=\"qbuilder\">");
				sb.append("\r\n		<where>");
				sb.append("\r\n			<if test=\"qbuilder.id != null\">"+tableName+".id = #{qbuilder.id}</if>");
				sb.append("\r\n			<if test=\"qbuilder.ids != null\">AND "+tableName+".id in ");
				sb.append("\r\n				<foreach item=\"id\" index=\"index\" collection=\"qbuilder.ids\" open=\"(\" separator=\",\" close=\")\">");
				sb.append("\r\n					#{id}");
				sb.append("\r\n				</foreach>");
				sb.append("\r\n			</if>");
				sb.append("\r\n			<if test=\"qbuilder.nativesql !=null\">");
				sb.append("\r\n				AND ( ${qbuilder.nativesql} )");
				sb.append("\r\n			</if>");
				sb.append("\r\n		</where>");
				sb.append("\r\n	</sql>");
				sb.append("\r\n");
				sb.append("\r\n	<select id=\"find\" resultType=\""+beanName+"\">");
				sb.append("\r\n		<choose>");
				sb.append("\r\n			<when test=\"start!=null and size !=null\">");
				sb.append("\r\n				select w2.n,w1.* from "+tableName+" w1,");
				sb.append("\r\n				(");
				sb.append("\r\n					select top (#{start}+#{size}) row_number() OVER");
				sb.append("\r\n					(");
				sb.append("\r\n					<choose>");
				sb.append("\r\n						<when test=\"orderby !=null\">");
				sb.append("\r\n							order by ${orderby}");
				sb.append("\r\n						</when>");
				sb.append("\r\n						<otherwise>");
				sb.append("\r\n							order by id asc");
				sb.append("\r\n						</otherwise>");
				sb.append("\r\n					</choose>");
				sb.append("\r\n					) n,id from "+tableName);
				sb.append("\r\n					<if test=\"qbuilder!=null\">");
				sb.append("\r\n						<include refid=\"qbuilder\"/>");
				sb.append("\r\n					</if>");
				sb.append("\r\n				) w2 where w1.id=w2.id and w2.n>#{start} order by w2.n asc");
				sb.append("\r\n			</when>");
				sb.append("\r\n			<otherwise>");
				sb.append("\r\n				select <if test=\"start==null and size!=null\">top #{size}</if> * from "+tableName);
				sb.append("\r\n				<if test=\"qbuilder!=null\">");
				sb.append("\r\n					<include refid=\"qbuilder\"/>");
				sb.append("\r\n				</if>");
				sb.append("\r\n				<if test=\"orderby!=null\">");
				sb.append("\r\n					order by ${orderby}");
				sb.append("\r\n				</if>");
				sb.append("\r\n			</otherwise>");
				sb.append("\r\n		</choose>");
				sb.append("\r\n	</select>");
				sb.append("\r\n");
				sb.append("\r\n	<select id=\"getTotalRows\" resultType=\"int\">");
				sb.append("\r\n		SELECT COUNT(*) FROM "+tableName+"");
				sb.append("\r\n		<if test=\"qbuilder != null\">");
				sb.append("\r\n			<include refid=\"qbuilder\"/>");
				sb.append("\r\n		</if>");
				sb.append("\r\n	</select>");
				sb.append("\r\n");
				sb.append("\r\n	<select id=\"findById\" resultType=\""+beanName+"\">");
				sb.append("\r\n		SELECT * FROM "+tableName+" WHERE id = #{id}");
				sb.append("\r\n	</select>");
				sb.append("\r\n");
				sb.append("\r\n	<insert id=\"insert\">");
				sb.append("\r\n		INSERT INTO "+tableName);
				sb.append("\r\n			(id");
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(","+field.getName());
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(","+field.getName());
						}
					}
				}
				sb.append(")");
				sb.append("\r\n		VALUES");
				sb.append("\r\n			(#{object.id}");
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(",#{object."+field.getName()+"}");
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							sb.append(",#{object."+field.getName()+"}");
						}
					}
				}
				sb.append(")");
				sb.append("\r\n	</insert>");
				sb.append("\r\n");
				sb.append("\r\n	<update id=\"update\">");
				sb.append("\r\n		UPDATE "+tableName);
				sb.append("\r\n		SET");
				sb.append("\r\n");
				int count1 = 0 ;
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							if(count1>0){
								sb.append(",\r\n");
							}
							sb.append("			"+field.getName()+" = #{object."+field.getName()+"}");
							count1 ++;
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							if(count1>0){
								sb.append(",\r\n");
							}
							sb.append("			"+field.getName()+" = #{object."+field.getName()+"}");
							count1 ++;
						}
					}
				}
				sb.append("\r\n		WHERE");
				sb.append("\r\n			id = #{object.id}");
				sb.append("\r\n	</update>");
				sb.append("\r\n");
				sb.append("\r\n	<delete id=\"delete\">");
				sb.append("\r\n		DELETE FROM "+tableName+" WHERE id = #{id}");
				sb.append("\r\n	</delete>");
				sb.append("\r\n");
				sb.append("\r\n	<delete id=\"deleteByIds\">");
				sb.append("\r\n		DELETE FROM "+tableName+" WHERE id in ");
				sb.append("\r\n		<foreach item=\"id\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">");
				sb.append("\r\n			#{id}");
				sb.append("\r\n		</foreach>");
				sb.append("\r\n	</delete>");
				sb.append("\r\n");
				sb.append("\r\n	<delete id=\"cleanTable\">");
				sb.append("\r\n		DELETE FROM "+tableName);
				sb.append("\r\n	</delete>");
				sb.append("\r\n</mapper>");
				WriteFile.contentToTxt(filepath+"mapper/"+beanName+"Mapper.xml", sb.toString());
				StringBuilder sb2 = new StringBuilder();
				sb2.append("package "+mapperPath+";");
				sb2.append("\r\nimport "+basicMapperPath+".BasicMapper;");
				sb2.append("\r\nimport "+entityPath+"."+beanName+";");
				sb2.append("\r\nimport "+basicRepositoryPath+".BasicRepository;");
				sb2.append("\r\n@BasicRepository");
				sb2.append("\r\npublic interface "+beanName+"Mapper extends BasicMapper<"+beanName+",Long>{");
				sb2.append("\r\n");
				sb2.append("\r\n}");
				WriteFile.contentToTxt(filepath+"mapper/"+beanName+"Mapper.java", sb2.toString());	
				System.out.println("--------------------"+tableName+"--end---------------------------");
			}
        }
	}
	
	
	/**
	 * 控制台打印mysqlmapper内容
	 * @param key
	 * @param map
	 * @param beanMap
	 * @param mapperPath
	 */
	@SuppressWarnings("rawtypes")
	public static void sysoMysqlMapper(Map<String, Class<?>> map,Map<String, String> beanMap,String mapperPath){
		Set key = map.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
            String tableName = (String) it.next();
            Class<?> className = map.get(tableName);
            String beanName = beanMap.get(tableName);
			if(StringUtils.isNotBlank(beanName)){
				Field[] fields = className.getDeclaredFields();
				Field[] ffields = className.getSuperclass().getDeclaredFields(); 
				System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				System.out.println("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
				System.out.println("<mapper namespace=\""+mapperPath+"."+beanName+"Mapper\">");
				System.out.println("	<!-- <cache /> -->");
				System.out.println("	<!-- 大于等于(&gt;=);小于等于(&lt;=);IS NOT NULL; -->");
				System.out.println("	<sql id=\"qbuilder\">");
				System.out.println("		<where>");
				System.out.println("			<if test=\"qbuilder.id != null\">"+tableName+".id = #{qbuilder.id}</if>");
				System.out.println("			<if test=\"qbuilder.ids != null\">AND "+tableName+".id in ");
				System.out.println("				<foreach item=\"id\" index=\"index\" collection=\"qbuilder.ids\" open=\"(\" separator=\",\" close=\")\">");
				System.out.println("					#{id}");
				System.out.println("				</foreach>");
				System.out.println("			</if>");
				System.out.println("			<if test=\"qbuilder.nativesql !=null\">");
				System.out.println("				AND ( ${qbuilder.nativesql} )");
				System.out.println("			</if>");
				System.out.println("		</where>");
				System.out.println("	</sql>");
				System.out.println("");
				System.out.println("	<select id=\"find\" resultType=\""+beanName+"\">");
				System.out.println("		SELECT * FROM "+tableName+"");
				System.out.println("		<if test=\"qbuilder != null\">");
				System.out.println("			<include refid=\"qbuilder\"/>");
				System.out.println("		</if>");
				System.out.println("		<choose>");
				System.out.println("			<when test=\"orderby != null\">");
				System.out.println("				ORDER BY "+tableName+".${orderby}");
				System.out.println("			</when>");
				System.out.println("			<otherwise>");
				System.out.println("				ORDER BY "+tableName+".id");
				System.out.println("			</otherwise>");
				System.out.println("		</choose>");
				System.out.println("		<choose>");
				System.out.println("			<when test=\"start != null and size != null\">");
				System.out.println("				LIMIT #{start},#{size}");
				System.out.println("			</when>");
				System.out.println("			<when test=\"start == null and size != null\">");
				System.out.println("				LIMIT #{size}");
				System.out.println("			</when>");
				System.out.println("		</choose>");
				System.out.println("	</select>");
				System.out.println("");
				System.out.println("	<select id=\"getTotalRows\" resultType=\"int\">");
				System.out.println("		SELECT COUNT(*) FROM "+tableName+"");
				System.out.println("		<if test=\"qbuilder != null\">");
				System.out.println("			<include refid=\"qbuilder\"/>");
				System.out.println("		</if>");
				System.out.println("	</select>");
				System.out.println("");
				System.out.println("	<select id=\"findById\" resultType=\""+beanName+"\">");
				System.out.println("		SELECT * FROM "+tableName+" WHERE id = #{id}");
				System.out.println("	</select>");
				System.out.println("");
				System.out.println("	<insert id=\"insert\">");
				System.out.println("		INSERT INTO "+tableName);
				System.out.print("			(id");
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							System.out.print(","+field.getName());
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							System.out.print(","+field.getName());
						}
					}
				}
				System.out.println(")");
				System.out.println("		VALUES");
				System.out.print("			(#{object.id}");
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							System.out.print(",#{object."+field.getName()+"}");
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							System.out.print(",#{object."+field.getName()+"}");
						}
					}
				}
				System.out.println(")");
				System.out.println("	</insert>");
				System.out.println("");
				System.out.println("	<update id=\"update\">");
				System.out.println("		UPDATE "+tableName);
				System.out.println("		SET");
				int count = 0 ;
				for (Field field : ffields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							if(count>0){
								System.out.println(",");
							}
							System.out.print("			"+field.getName()+" = #{object."+field.getName()+"}");
							count ++;
						}
					}
				}
				for (Field field : fields) {
					if(field.isAnnotationPresent(DBField.class)){
						if (!field.getName().equals("id")) {
							if(count>0){
								System.out.println(",");
							}
							System.out.print("			"+field.getName()+" = #{object."+field.getName()+"}");
							count ++;
						}
					}
				}
				System.out.println();
				System.out.println("		WHERE");
				System.out.println("			id = #{object.id}");
				System.out.println("	</update>");
				System.out.println("");
				System.out.println("	<delete id=\"delete\">");
				System.out.println("		DELETE FROM "+tableName+" WHERE id = #{id}");
				System.out.println("	</delete>");
				System.out.println("");
				System.out.println("	<delete id=\"deleteByIds\">");
				System.out.println("		DELETE FROM "+tableName+" WHERE id in ");
				System.out.println("		<foreach item=\"id\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">");
				System.out.println("			#{id}");
				System.out.println("		</foreach>");
				System.out.println("	</delete>");
				System.out.println("");
				System.out.println("	<delete id=\"cleanTable\">");
				System.out.println("		DELETE FROM "+tableName);
				System.out.println("	</delete>");
				System.out.println("</mapper>");
			}
		}
	}
}
