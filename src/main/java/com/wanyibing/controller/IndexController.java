package com.wanyibing.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.wanyibing.entity.Article;
import com.wanyibing.entity.Category;
import com.wanyibing.entity.Channel;
import com.wanyibing.entity.Slide;
import com.wanyibing.service.ArticleService;

/**
 * 
 * @author 万一兵
 *
 */
@Controller
public class IndexController {
	
	@Autowired 
	ArticleService articleSservice;
	
	/**
 	* @param request
	 * @throws InterruptedException 
 	*/
	@RequestMapping(value= {"index","/"})
	public String index(HttpServletRequest request,@RequestParam(defaultValue="1")int page) throws InterruptedException {
		Thread t1 = new Thread() {
			 @Override
				public void run() {
				//获取所有的栏目
				 List<Channel> channels = articleSservice.getChannels();
				 request.setAttribute("channels",channels);
				};
				
			
			};
		Thread t2 = new Thread() {
			 @Override
			public void run() {
			//热门文章
			 PageInfo<Article> artclePage =  articleSservice.hotList(page);
			 request.setAttribute("artclePage",artclePage);
			};
					
				
		};
		Thread t3 = new Thread() {
			 @Override
				public void run() {
				 //获取最新的文章
				 List<Article> lasArticles = articleSservice.lastList();
				 request.setAttribute("lasArticles",lasArticles);
				 
				};
				
					
		};
		Thread t4 = new Thread() {
			 @Override
				public void run() {
				 //轮播图
				 List<Slide> slides= articleSservice.getSlides();
				 request.setAttribute("slides",slides);
				};
				
			
			};
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		
		 
		 
		return "index";
	}
	
	@RequestMapping("channel")
	public String channel(HttpServletRequest request,
			int channelId,
			@RequestParam(defaultValue="0") int catId,
			@RequestParam(defaultValue="1") int page) throws InterruptedException {

		//获取所有的栏目
		Thread t1 = new Thread() {
			@Override
			public void run() {
				List<Channel> channels = articleSservice.getChannels();
				request.setAttribute("channels", channels);
			};
		};
		Thread  t2 =  new Thread() {
			public void run() {
		// 当前栏目下  当前分类下的文章
				PageInfo<Article> articlePage= articleSservice.getArticles(channelId,catId, page);
			request.setAttribute("articlePage", articlePage);
			};
		};
		Thread t3 = new Thread() {
			 @Override
				public void run() {
				 //获取最新的文章
				 List<Article> lasArticles = articleSservice.lastList();
				 request.setAttribute("lasArticles",lasArticles);
				 
				};
				
					
		};
		Thread t4 = new Thread() {
			 @Override
				public void run() {
				 //轮播图
				 List<Slide> slides= articleSservice.getSlides();
				 request.setAttribute("slides",slides);
				};
				
			
			};
			//获取当下栏目的所有分类
		Thread t5 = new Thread() {
			 @Override
				public void run() {
			List<Category> categoris= articleSservice.getCategoriesByChannelId(channelId);
			request.setAttribute("categoris", categoris);
			System.err.println("categoris is " + categoris);
				};
			};
				
			
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
			// 参数回传
			request.setAttribute("catId", catId);
			request.setAttribute("channelId", channelId);
		 
		 
		return "channel";
	}
	
	
}
