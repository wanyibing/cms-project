package com.wanyibing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.wanyibing.cms.utils.StringUtils;
import com.wanyibing.entity.Shoucang;
import com.wanyibing.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class TestUrl {

	@Autowired
	ArticleService articleService;
	/**
	 * 测试添加收藏
	 */
	@Test
	public void addUrl() {
		
		
		Shoucang shoucang = new Shoucang();
		shoucang.setUser_id(66);
		shoucang.setText("测试");
		if(StringUtils.isUrl("http://localhost:8084/")) {
			shoucang.setUrl("http://localhost:8084/" );
			shoucang.setCreated("2019-02-05 22:12:15");
			//添加收藏
			articleService.addshoucang(shoucang);
		}else {
			System.err.println("地址错误");
		}
		
		
	}
	/**
	 * 查询收藏
	 */
	@Test
	public void listUrl() {
		
		PageInfo<Shoucang> listShoucang = articleService.listShoucang(66,1);
		  
	}
	/**
	 * 删除
	 */
	@Test
	public void delet() {
		
		articleService.deleteshoucang(21);
		  
	}
	
}
