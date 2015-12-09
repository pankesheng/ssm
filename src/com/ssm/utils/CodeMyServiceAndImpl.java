package com.ssm.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CodeMyServiceAndImpl {
	
	@SuppressWarnings("rawtypes")
	public static void printCode(Map<String, Class<?>> map,Map<String, String> beanMap,String filepath,String entityPath,String mapperPath,String servicePath,String basicServicePath,String basicServiceImplPath) {
		Set key = map.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
        	StringBuilder sb = new StringBuilder();
            String tableName = (String) it.next();
            String beanName = beanMap.get(tableName);
            sb.append("package "+servicePath+";");
            sb.append("\r\nimport "+basicServicePath+".BasicService;");
            sb.append("\r\nimport "+entityPath+"."+beanName+";");
            sb.append("\r\n");
            sb.append("\r\npublic interface "+beanName+"Service extends BasicService<"+beanName+", Long>{");
            sb.append("\r\n");
            sb.append("\r\n}");
            WriteFile.contentToTxt(filepath+"service//"+beanName+"Service.java", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            String lowerTableName = beanName.toLowerCase();
            sb2.append("package "+servicePath+".impl;");
            sb2.append("\r\nimport org.springframework.beans.factory.annotation.Autowired;");
            sb2.append("\r\nimport org.springframework.stereotype.Service;");
            sb2.append("\r\nimport "+basicServiceImplPath+".BasicServiceImpl;");
            sb2.append("\r\nimport "+entityPath+"."+beanName+";");
            sb2.append("\r\nimport "+mapperPath+"."+beanName+"Mapper;");
    		sb2.append("\r\nimport "+servicePath+"."+beanName+"Service;");
    		sb2.append("\r\n");
			sb2.append("\r\n@Service(\""+lowerTableName+"Service\")");
			sb2.append("\r\npublic class "+beanName+"ServiceImpl extends BasicServiceImpl<"+beanName+", Long, "+beanName+"Mapper> implements "+beanName+"Service{");
            sb2.append("\r\n	@Autowired");
            sb2.append("\r\n	private "+beanName+"Mapper "+lowerTableName+"Mapper;");
        	sb2.append("\r\n");
            sb2.append("\r\n	@Override");
            sb2.append("\r\n	protected "+beanName+"Mapper getMapper() {");
            sb2.append("\r\n		return "+lowerTableName+"Mapper;");
            sb2.append("\r\n	}");
            sb2.append("\r\n}");
            WriteFile.contentToTxt(filepath+"service//impl//"+beanName+"ServiceImpl.java", sb2.toString());
        }
	}
}
