package com.wanyibing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanyibing.Utils.CmsError;
import com.wanyibing.Utils.CmsMessage;
import com.wanyibing.entity.Article;
import com.wanyibing.service.ArticleService;

@Controller
@RequestMapping("article")
public class ArticleController {

	@Autowired
	ArticleService articleService;
	
	@RequestMapping("getDetail")
	@ResponseBody
	public CmsMessage getDetail(int id) {
		if(id<=0) {
			
		}
		// 获取文章详情
		Article article = articleService.findById(id);
		// 不存在
		if(article==null)
			return new CmsMessage(CmsError.NOT_EXIST, "文章不存在",null);
		
		CmsMessage cmsMessage = new CmsMessage(CmsError.SUCCESS,"",article);
		System.out.println("-=-=-=-=-=-=-=-=-="+cmsMessage);
		// 返回数据
		return  cmsMessage;
		
	}
	
}
