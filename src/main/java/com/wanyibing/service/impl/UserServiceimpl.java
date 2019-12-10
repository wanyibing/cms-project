package com.wanyibing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanyibing.mapper.UserMapper;
import com.wanyibing.service.UserService;

@Service
public class UserServiceimpl implements UserService {

	@Autowired
	private UserMapper mapper;
	
	 
}
