package com.ssm.common;

public class Configuration {

	private static String contextPath;

	// 通过ApplicationListener初始化
	private static String realPath;
	
	private static Boolean debug;
	
	public static String getContextPath() {
		return contextPath;
	}

	public static void setContextPath(String contextPath) {
		Configuration.contextPath = contextPath;
	}

	public static String getRealPath() {
		return realPath;
	}

	public static Boolean getDebug() {
		return debug;
	}

	public static void setDebug(Boolean debug) {
		Configuration.debug = debug;
	}

	public static void setRealPath(String realPath) {
		Configuration.realPath = realPath;
	}

}
