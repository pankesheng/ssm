package com.ssm.entity;

import java.util.Date;

import com.ssm.common.BasicEntity;

public class Article extends BasicEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9080385873199079361L;
	private Long userId;
	private String title;
	private String content;
	private Date createDate;
	
	@Override
	public void initRecord() {
		super.initRecord();
		createDate = new Date();
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
