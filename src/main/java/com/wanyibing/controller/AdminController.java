package com.wanyibing.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wanyibing.Utils.CmsError;
import com.wanyibing.Utils.CmsMessage;
import com.wanyibing.entity.Article;
import com.wanyibing.service.ArticleService;

@RequestMapping("admin")
@Controller
public class AdminController {

	@Autowired
	ArticleService articleService;
	
	@RequestMapping("index")
	public String index() {
		
			return "admin/index";
		 
	}
	/**
	 * 修改i状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("setArticeStatus")
	@ResponseBody
	public CmsMessage setArticeStatus(int id,int status) {
		
		if(status != 1 && status !=2) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT, "参数不合法", null);
			
		}
		if(id<0) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT,"id参数值不合法",null);
		}
		Article article = articleService.getInfoById(id);
		if(article==null) {
			return new CmsMessage(CmsError.NOT_EXIST,"数据不存在",null);
		}
		
		if(article.getStatus()==status) {
			return new CmsMessage(CmsError.NEEDNT_UPDATE,"数据无需更改",null);
		}
		
		
		int result = articleService.setCheckStatus(id,status);
		if(result<1)
			return new CmsMessage(CmsError.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		
		
		return new CmsMessage(CmsError.SUCCESS,"成功",null);
	}
	
	@RequestMapping("setArticeHot")
	@ResponseBody
	public CmsMessage setArticeHot(int id,int status) {
		
		if(status != 1 && status !=2) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT, "参数不合法", null);
			
		}
		if(id<0) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT,"id参数值不合法",null);
		}
		Article article = articleService.getInfoById(id);
		if(article==null) {
			return new CmsMessage(CmsError.NOT_EXIST,"数据不存在",null);
		}
		
		if(article.getStatus()==status) {
			return new CmsMessage(CmsError.NEEDNT_UPDATE,"数据无需更改",null);
		}
		int result = articleService.setHot(id,status);
		if(result<1)
			return new CmsMessage(CmsError.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		
		CmsMessage cmsMessage = new CmsMessage(CmsError.SUCCESS,"成功",null);
		return cmsMessage;
		
	}
	
	@RequestMapping("article")
	public String article(HttpServletRequest request,int page) {
		
		PageInfo<Article> articlePage = articleService.list(page);
		request.setAttribute("articlePage", articlePage);
		
		
		return "admin/article/list";
	}
	
	@RequestMapping("deletearticle")
	@ResponseBody
	public boolean deletearticle(int id) {
		
		int i = articleService.deletearticle(id);
		
		return i>0;
	}
	
}
