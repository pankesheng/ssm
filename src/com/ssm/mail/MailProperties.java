package com.ssm.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailProperties {
	private static String mailServerHost;
	private static String mailServerPort;
	private static String mailUserName;
	private static String mailPassword;
	
	static{
		 Properties prop = new Properties();   
        InputStream in =MailProperties.class.getResourceAsStream("/conf/mail.properties");   
        try {   
            prop.load(in);   
            mailServerHost = prop.getProperty("mailServerHost").trim();  
            mailServerPort = prop.getProperty("mailServerPort").trim();  
            mailUserName = prop.getProperty("mailUserName").trim();
            mailPassword = prop.getProperty("mailPassword").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public MailProperties() {
		// TODO Auto-generated constructor stub
	}
	public static String getMailServerHost() {
		return mailServerHost;
	}
	public static void setMailServerHost(String mailServerHost) {
		MailProperties.mailServerHost = mailServerHost;
	}
	public static String getMailServerPort() {
		return mailServerPort;
	}
	public static void setMailServerPort(String mailServerPort) {
		MailProperties.mailServerPort = mailServerPort;
	}
	public static String getMailUserName() {
		return mailUserName;
	}
	public static void setMailUserName(String mailUserName) {
		MailProperties.mailUserName = mailUserName;
	}
	public static String getMailPassword() {
		return mailPassword;
	}
	public static void setMailPassword(String mailPassword) {
		MailProperties.mailPassword = mailPassword;
	}
	
}
