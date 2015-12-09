package com.ssm.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.common.BasicController;
import com.ssm.common.Page;
import com.ssm.common.WebContext;
import com.ssm.entity.User;
import com.ssm.service.UserService;
import com.zcj.web.dto.ServiceResult;

@Controller
@RequestMapping("/user")
@Scope("prototype")
public class UserController extends BasicController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		// 清空旧session
		WebContext.removeLoginUserAndSession(request);
		// 保存当前登录用户的信息到session
		User user = new User();
		user.setId(945060403710976L);
		user.setName("登录人");
		WebContext.updateLoginUser(request, user);
		mav.setViewName("/WEB-INF/menu/index.jsp");
		return mav;
	}
	
	
	@RequestMapping("/addUser")
	public void addUser(HttpServletRequest request,User user,PrintWriter out){
		user.initRecord();
		try {
			userService.insert(user);
			out.write(ServiceResult.initSuccessJson(user));
		} catch (Exception e) {
			e.printStackTrace();
			out.write(ServiceResult.initErrorJson("参数错误"));
		}
	}
	
	@RequestMapping("/search")
	public void search(HttpServletRequest request,PrintWriter out){
		Page page=new Page();
		Map<String, Object> qbuilder=new HashMap<String, Object>();
		qbuilder.put("nameKey","%1%");
		page.setD(userService.findByPage("createDate", qbuilder));
		page.setTotal(userService.getTotalRows(qbuilder));
		out.write(ServiceResult.GSON_DT.toJson(page));
	}

}
