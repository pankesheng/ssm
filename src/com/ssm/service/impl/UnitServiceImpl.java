package com.ssm.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssm.common.BasicServiceImpl;
import com.ssm.entity.Unit;
import com.ssm.mapper.UnitMapper;
import com.ssm.service.UnitService;

@Service("unitService")
public class UnitServiceImpl extends BasicServiceImpl<Unit, Long, UnitMapper> implements UnitService{
	@Autowired
	private UnitMapper unitMapper;

	@Override
	protected UnitMapper getMapper() {
		return unitMapper;
	}
}