package com.ssm.entity;

import java.util.Date;

import com.ssm.common.BasicEntity;
import com.ssm.utils.DBField;

public class User extends BasicEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2718054059324745391L;
	@DBField(allowNull=false,length=100,comment="用户姓名")
	private String name;
	@DBField(allowNull=false,comment="创建时间")
	private Date createDate;
	@DBField(allowNull=false,length=20,comment="单位id")
	private Long unitId;

    @Override
    public void initRecord() {
    	// TODO Auto-generated method stub
    	super.initRecord();
    	createDate=new Date();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
    
    
    
}