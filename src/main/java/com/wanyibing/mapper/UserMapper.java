package com.wanyibing.mapper;
 

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.wanyibing.entity.User;

public interface UserMapper {

	@Select(" SELECT id,username,password FROM cms_user "
			+ " WHERE username = #{username} limit 1")  
	User getUserName(String username);

	@Insert("INSERT INTO cms_user(username,password,locked,create_time,score,role)"
			+ " VALUES(#{username},#{password},0,now(),0,0)")
	int adduser(User user);
	/**
	 * 根据用户密码查询  登录操作
	 * @param user
	 * @return
	 */
	@Select("SELECT id,username,password,nickname,birthday,"
			+ "gender,locked,create_time createTime,update_time updateTime,url,"
			+ "role FROM cms_user WHERE username=#{username}  AND password = #{password} "
			+ " LIMIT 1")
	User findByPwd(User user);
 
}
