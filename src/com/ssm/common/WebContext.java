package com.ssm.common;

import javax.servlet.http.HttpServletRequest;

import com.ssm.entity.User;
import com.zcj.web.context.BasicWebContext;

/**
 * WEB端(所有方法都不支持多线程)
 * 
 * @author ZCJ
 * @data 2013-8-13
 */
public class WebContext extends BasicWebContext {

	/** 判断是否登陆，如果登陆则返回用户对象，如果未登陆则返回空 */
	public static User getLoginUser(HttpServletRequest request) {
		Object o = BasicWebContext.getLoginUser(request);
		if (o == null) {
			return null;
		} else {
			User user = (User) o;
			return user;
		}
	}
	
	/** 获取登陆用户的Id */
	public static Long getLoginUserId(HttpServletRequest request) {
		User user = getLoginUser(request);
		if (user == null) {
			return null;
		} else {
			return user.getId();
		}
	}
	
	
}
