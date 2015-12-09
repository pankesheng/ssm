package com.ssm.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssm.common.BasicServiceImpl;
import com.ssm.entity.User;
import com.ssm.mapper.UserMapper;
import com.ssm.service.UserService;

@Service("userService")
public class UserServiceImpl extends BasicServiceImpl<User, Long, UserMapper> implements UserService{
	@Autowired
	private UserMapper userMapper;

	@Override
	protected UserMapper getMapper() {
		return userMapper;
	}
}