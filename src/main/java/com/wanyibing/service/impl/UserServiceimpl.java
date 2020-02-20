package com.wanyibing.service.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanyibing.Utils.CmsUtils;
import com.wanyibing.entity.User;
import com.wanyibing.mapper.UserMapper;
import com.wanyibing.service.UserService;
 
@Service
public class UserServiceimpl implements UserService {

	@Autowired
	private UserMapper mapper;
	
	 @Override
	public User getUserName(String username) {
		// TODO Auto-generated method stub
		return mapper.getUserName(username);
	}
	 
	@Override
	public int register(@Valid User user) {
		// TODO Auto-generated method stub
		//计算密文 
		String encryPwd = CmsUtils.encry(user.getPassword(), user.getUsername());
		user.setPassword(encryPwd);
		
		return mapper.adduser(user);
	}

	@Override
	public User login(User user) {
		user.setPassword(CmsUtils.encry(user.getPassword(), user.getUsername()));
		User loginUser = mapper.findByPwd(user);
		return loginUser;
	}
}
