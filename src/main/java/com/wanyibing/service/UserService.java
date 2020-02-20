package com.wanyibing.service;

import javax.validation.Valid;

import com.wanyibing.entity.User;
 
public interface UserService {

	User getUserName(String username);

	int register(@Valid User user);

	User login(User user);
 
}
