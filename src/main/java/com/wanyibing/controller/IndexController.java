package com.wanyibing.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
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
	
	@Autowired
	RedisTemplate redisTemplate;
	
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
			 @SuppressWarnings("unchecked")
			@Override
			public void run() {
		/*	//热门文章
			 PageInfo<Article> artclePage =  articleSservice.hotList(page);
			 request.setAttribute("artclePage",artclePage);*/
				 PageHelper.startPage(1,5);
			List<Article> listArticle = redisTemplate.opsForList().range("new_commentCns", 0, -1);
			if(listArticle==null||listArticle.size()==0) {
		 		 //3.如果为空
				 //4.如果为空几句从mysql查询最新文章放入redis返回前台
				//PageHelper.startPage(1,5);
				List<Article> list = articleSservice.getcommentCns();
		 		 System.err.println("从mysql中查询了热门文章....."+list);
		 		 //从MySQL查询的文章设置五分钟的时间 然后到期
		 		 redisTemplate.opsForList().leftPushAll("new_commentCns", list.toArray(),5,TimeUnit.MINUTES);
		 		PageInfo<Article> artclePage = new PageInfo<>(list);
		 		 request.setAttribute("a",artclePage);
		 		 request.setAttribute("artclePage",artclePage);
		 	}else {
		 		//5.如果不为空返回前台
		 		
		 		 System.err.println("从redis中查询了热门文章.....");
		 		// System.out.println(listArticle.toString());
		 		PageInfo<Article> artclePage = new PageInfo<>(listArticle);
		 		request.setAttribute("a",artclePage);
				 request.setAttribute("artclePage",artclePage);
		 	}
		
			
				 
			};
					
				
		};
		Thread t3 = new Thread() {
			 @SuppressWarnings("unchecked")
			@Override
				public void run() {
				 //获取最新的文章
				 //0.redis作为缓存来优化最新文章 
				 //1.先从redis查询最新文章
				 List<Article> rangeArticle = redisTemplate.opsForList().range("new_articles", 0, -1);
				 //2.判断redis中查询的是否为空(有么有最新文章
				 	if(rangeArticle==null||rangeArticle.size()==0) {
				 		 //3.如果为空
						 //4.如果为空几句从mysql查询最新文章放入redis返回前台
				 		 List<Article> lasArticles = articleSservice.lastList();
				 		 System.err.println("从mysql中查询了最新文章.....");
				 		 redisTemplate.opsForList().leftPushAll("new_articles", lasArticles.toArray());
				 		 redisTemplate.expire("new_articles", 5, TimeUnit.MINUTES);
				 		 request.setAttribute("lasArticles",lasArticles);
				 	}else {
				 		//5.如果不为空返回前台
				 		
				 		 System.err.println("从redis中查询了最新文章.....");
						 request.setAttribute("lasArticles",rangeArticle);
				 	}
				
				 
				 
				 
				 
				 
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
	
	/*@RequestMapping("commentCns")
	public String commentCns(HttpServletRequest request) {
		
		articleSservice.
		
		return "";
	}
	*/
	
}
