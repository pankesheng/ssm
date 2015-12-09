package com.ssm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.common.BasicController;

@Controller
@RequestMapping("/index")
@Scope("prototype")
public class IndexController extends BasicController{
	
	@RequestMapping("/left")
	public ModelAndView left(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/WEB-INF/menu/left.jsp");
		return mav;
	}
	
	@RequestMapping("/container")
	public ModelAndView container(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/WEB-INF/menu/container.jsp");
		return mav;
	}
	@RequestMapping("/top")
	public ModelAndView top(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/WEB-INF/menu/top.jsp");
		return mav;
	}
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/WEB-INF/menu/main.jsp");
		return mav;
	}
	
}
