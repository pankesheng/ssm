package com.ssm.entity;

import java.util.Date;

import com.ssm.common.BasicEntity;
import com.ssm.utils.DBField;

public class Unit extends BasicEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3283161138430988422L;
	@DBField(allowNull=false,comment="单位名称",length=255)
	private String unitName;
	@DBField(comment="排序号",length=10)
	private Integer orderNo;
	@DBField(comment="备注",type="text")
	private String remark;
	@DBField(comment="创建时间",allowNull=false)
	private Date createDate;
	@DBField(comment="上级单位id",length=20)
	private Long fId;
	@Override
	public void initRecord() {
		super.initRecord();
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
