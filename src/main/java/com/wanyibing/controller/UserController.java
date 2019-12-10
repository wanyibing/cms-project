package com.wanyibing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wanyibing.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
 
	@Autowired 
	private UserService service; 
	
	@RequestMapping("login")
	public String login() {
		
		return "user/login";
	}
}
