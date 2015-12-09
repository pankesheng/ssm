package com.ssm.mail;

public class test {
	public static void main(String[] args){  
		//这个类主要是设置邮件  
		MailSenderInfo mailInfo = new MailSenderInfo();   
		mailInfo.setValidate(true);   
		mailInfo.setToAddress("392445930@qq.com");   
		mailInfo.setSubject("测试标题");   
		mailInfo.setContent("测试内容测试内容测试内容测试内容测试内容测试内容测试内容");   
		//这个类主要来发送邮件  
		SimpleMailSender sms = new SimpleMailSender();  
		sms.sendTextMail(mailInfo);//发送文体格式   
   }  
}
