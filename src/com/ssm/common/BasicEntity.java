package com.ssm.common;

import java.io.Serializable;

import com.ssm.utils.DBField;
import com.zcj.util.UtilString;

/**
 * 222
 * @author zouchongjin@sina.com
 * @data 2015年12月28日
 */
public class BasicEntity  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2410697513041678555L;
	@DBField(comment="主键id",length=20,allowNull=false)
	private Long id;
	
	public void initRecord(){
		id = UtilString.getLongUUID();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
