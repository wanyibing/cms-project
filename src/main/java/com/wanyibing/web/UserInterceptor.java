package com.wanyibing.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.wanyibing.Utils.CmsContant;
import com.wanyibing.entity.User;
import com.wanyibing.service.UserService;

public class UserInterceptor implements HandlerInterceptor {

	@Autowired
	UserService userService;
	
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
	
	 User loginUser = (User) request.getSession().getAttribute(CmsContant.USER_KEY);

	 if(loginUser!=null) {
		 return true;
	 }
	 
	 User user = new User();
	 Cookie[] cookies = request.getCookies();
	 for (int i = 0; i < cookies.length; i++) {
		
		 if("username".equals(cookies[i].getName())) {
			 user.setUsername(cookies[i].getValue());
		 }
		 if("userpwd".equals(cookies[i].getName())){
				user.setPassword(cookies[i].getValue());
			}
	}
	 //说明cookie中存放的用户信息不完整
	 if(null==user.getUsername() || null== user.getPassword()) {
		 
		 response.sendRedirect("user/login");
		//request.getRequestDispatcher("/user/login").forward(request, response);
		 return false;
	 }
	 
	// 利用cookie中用户信息进行登录操作
	loginUser = userService.login(user);
	System.out.println("111"+loginUser);
	if(loginUser!=null) {
		request.getSession().setAttribute(CmsContant.USER_KEY, loginUser);
	return true;
	}	
	 
				 response.sendRedirect("login");
				/* request.setAttribute("error", "请先登录");*/
				 return false;
	 	
}
	
}
