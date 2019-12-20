package com.wanyibing.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wanyibing.Utils.CmsContant;
import com.wanyibing.Utils.CmsError;
import com.wanyibing.Utils.CmsMessage;
import com.wanyibing.entity.Article;
import com.wanyibing.entity.Comment;
import com.wanyibing.entity.User;
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
	
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,int id) {
		
		Article article = articleService.findById(id);
		request.setAttribute("article", article);
		
		return "detail";
	}
	/**
	 * 评论
	 */
	@RequestMapping("postcomment")
	@ResponseBody
	public CmsMessage postcomment(HttpServletRequest request,int articleId,String content) {
		
		User loginUser  = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		
		if(loginUser==null) {
			return new CmsMessage(CmsError.NOT_LOGIN, "您尚未登录！", null);
		}
		
		Comment comment = new Comment();
		comment.setUserId(loginUser.getId());
		comment.setContent(content);
		comment.setArticleId(articleId);
		int result = articleService.addComment(comment);
		if(result > 0)
			return new CmsMessage(CmsError.SUCCESS, "成功", null);
		
		return new CmsMessage(CmsError.FAILED_UPDATE_DB, "异常原因失败，请与管理员联系", null);
		
	}
	// {articleId:'${article.id}',content:$("#co
			
		//comments?id
		@RequestMapping("comments")
		public String comments(HttpServletRequest request,int id,int page) {
			PageInfo<Comment> commentPage =  articleService.getComments(id,page);
			request.setAttribute("commentPage", commentPage);
			return "comments";
		}	
	
}
