package com.wanyibing.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.wanyibing.Utils.CmsContant;
import com.wanyibing.entity.User;

public class UserInterceptor implements HandlerInterceptor {

@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
	 User user = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
			 if(user == null) {
				 response.sendRedirect("login");
				/* request.setAttribute("error", "请先登录");*/
				 return false;
			 }
	return true;
}
	
}
