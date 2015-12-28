package com.ssm.common;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.zcj.util.UtilDate;

public class BasicController {

	protected Page page = new Page();

	protected String RESULT_PAR_ERROR = "404";// 参数错误的返回值

	// 处理参数中的日期格式
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}


	
	
	
	public class DateEditor extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			Date date = UtilDate.format(text);
			setValue(date);
		}
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}