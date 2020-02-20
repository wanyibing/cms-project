package com.wanyibing.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wanyibing.Utils.CmsContant;
import com.wanyibing.Utils.CmsError;
import com.wanyibing.Utils.CmsMessage;
import com.wanyibing.entity.Article;
import com.wanyibing.entity.Complain;
import com.wanyibing.entity.User;
import com.wanyibing.mapper.ArticleMapper;
import com.wanyibing.mapper.ArticleReP;
import com.wanyibing.service.ArticleService;

@RequestMapping("admin") 
@Controller
public class AdminController { 

	@Autowired
	ArticleService articleService;
	
	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	ArticleReP articleReP;
	
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
		//1.从mysql种已经审核通过所有的文章
		List<Article> articles = articleMapper.findAllArticleWithStatus(1);
		//2.查询出来的文章保存再es的索引库
		articleReP.saveAll(articles);
		
		return new CmsMessage(CmsError.SUCCESS,"成功",null);
	}
	
	@RequestMapping("setArticeHot")
	@ResponseBody
	public CmsMessage setArticeHot(int id,int status) {
		
		if(status != 0 && status !=1) {
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
		}
		int result = articleService.setHot(id,status);
		if(result<1)
			return new CmsMessage(CmsError.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		
		CmsMessage cmsMessage = new CmsMessage(CmsError.SUCCESS,"成功",null);
		return cmsMessage;
		
	}
	
	@RequestMapping("article")
	public String article(HttpServletRequest request,@RequestParam(defaultValue="1")int page,String status) {
		
		PageInfo<Article> articlePage = articleService.list(page,status);
		System.out.println(articlePage);
		request.setAttribute("articlePage", articlePage);
		request.setAttribute("status",status);
		return "admin/article/list";
	}
	
	@RequestMapping("deletearticle")
	@ResponseBody
	public boolean deletearticle(int id) {
		
		int i = articleService.deletearticle(id);
		
		return i>0;
	}
	
	/**
	 * 管理员中心
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping("loginAdmin") 
	@ResponseBody
	public boolean login(HttpServletRequest request,User user) {
		
		User u = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		System.out.println(u);
		if(u.getRole()!=CmsContant.USER_ROLE_ADMIN)
			request.setAttribute("erro","您不是管理员");
		
		//管理界面
		if(u.getRole()==CmsContant.USER_ROLE_ADMIN)  
			return true;
	 
		 
		return false;
	}
	
	@RequestMapping("complain")
	public String complainlist(HttpServletRequest request,@RequestParam(defaultValue="1")int page) {
		
		PageInfo<Complain> articlePage = articleService.complainlist(page);		
		request.setAttribute("articlePage",articlePage);
		return "admin/article/complain";
	}
	
}
