package com.wanyibing.controller;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanyibing.Utils.CmsContant;
import com.wanyibing.Utils.CmsError;
import com.wanyibing.Utils.CmsMessage;
import com.wanyibing.Utils.HLUtils;
import com.wanyibing.cms.utils.StringUtils;
import com.wanyibing.entity.Article;
import com.wanyibing.entity.Channel;
import com.wanyibing.entity.Comment;
import com.wanyibing.entity.Complain;
import com.wanyibing.entity.Shoucang;
import com.wanyibing.entity.User;
import com.wanyibing.mapper.ArticleReP;
import com.wanyibing.service.ArticleService;

@Controller
@RequestMapping("article")
public class ArticleController extends BaseController{

	@Autowired
	ArticleService articleService;
	@Autowired //注入es更苦
	ArticleReP articleReP;
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	//es搜索的方法
	@RequestMapping("search")
	public String search(String key,Model model,HttpServletRequest request,@RequestParam(defaultValue="1")int page) {
		/*Thread t1 = new Thread() {
			 @Override
				public void run() {
				//获取所有的栏目
				 List<Channel> channels = articleService.getChannels();
				 System.out.println("1111111111");
				 request.setAttribute("channels",channels);
				};
				
			
			};
			
			Thread t2 = new Thread() {
				 @SuppressWarnings("unchecked")
				@Override
				public void run() {
					 System.out.println("---------------");
				//热门文章
				 PageInfo<Article> artclePage =  articleSservice.hotList(page);
				 request.setAttribute("a",artclePage);
				 request.setAttribute("artclePage",artclePage);
				List<Article> listArticle = redisTemplate.opsForList().range("new_commentCns", 0, -1);
				if(listArticle==null||listArticle.size()==0) {
			 		 //3.如果为空
					 //4.如果为空几句从mysql查询最新文章放入redis返回前台
					//PageHelper.startPage(1,5);
					List<Article> list = articleService.getcommentCns();
			 		 System.err.println("从mysql中查询了热门文章....."+list);
			 		 redisTemplate.opsForList().leftPushAll("new_commentCns", list.toArray());
			 		PageInfo<Article> artclePage = new PageInfo<>(list);
			 		 request.setAttribute("a",artclePage);
			 		 request.setAttribute("artclePage",artclePage);
			 	}else {
			 		//5.如果不为空返回前台
			 		PageHelper.startPage(1,5);
			 		 System.err.println("从redis中查询了热门文章.....");
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
					 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
					 //获取最新的文章
					 //0.redis作为缓存来优化最新文章 
					 //1.先从redis查询最新文章
					 List<Article> rangeArticle = redisTemplate.opsForList().range("new_articles", 0, -1);
					 //2.判断redis中查询的是否为空(有么有最新文章
					 	if(rangeArticle==null||rangeArticle.size()==0) {
					 		 //3.如果为空
							 //4.如果为空几句从mysql查询最新文章放入redis返回前台
					 		 List<Article> lasArticles = articleService.lastList();
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
			t1.start();
			
			 t3.start();
			 t2.start(); */
			/*	//利用es的仓库来查询(无高量)
				List<Article> list = articleReP.findByTitle(key);
				PageInfo<Article> pageInfo = new PageInfo<>(list);
				model.addAttribute("artclePage", pageInfo);
				model.addAttribute("key", key);*/
			//高亮
			/*PageInfo<Article> pageInfo = (PageInfo<Article>)  HLUtils.findByHighLight(elasticsearchTemplate, Article.class, page, 2, new String[] {"title"}, "id", key);
				System.out.println(pageInfo);
			model.addAttribute("artclePage", pageInfo);
			model.addAttribute("key", key);*/
			 //定义一个开始时间
			 long start = System.currentTimeMillis();
			PageInfo<Article> pageInfo = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, page, 2, new String[] {"title"}, "id", key);
				//结束时间
			long end = System.currentTimeMillis();
			System.err.println("一共花费了"+(end-start)+"毫秒");
			model.addAttribute("artclePage", pageInfo);
			model.addAttribute("key", key);
			 
		return "index";
	}
	
	
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
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	ThreadPoolTaskExecutor executor;
	//利用kafka 的生产发送id
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,int id) {
		//articleService.addliulan(id);
	/*	User user = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		String addr = request.getRemoteAddr();
		String key = "Hits_"+id+"_"+addr+"_"+user;
		//System.out.println(key);
		
		//request.setAttribute("user", user);
		Article article = articleService.findById(id);
		request.setAttribute("article", article);
		String hasKey =  (String) redisTemplate.opsForValue().get(key);
		System.out.println("wwww"+hasKey);
		if(hasKey==null) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					article.setHits(article.getHits()+1);
					articleService.addliulan(id);
					redisTemplate.opsForValue().set(key, "",5, TimeUnit.MINUTES);
				}
			});
		}*/
		
		
		//当用户流蓝文章往kafka发送消息
		Article article = articleService.findById(id);
		request.setAttribute("article", article);
				 kafkaTemplate.send("articles","user_view=="+id+"");
		return "detail";
	}
	/**
	 * 收藏
	 * @param request
	 * @param title
	 * @return
	 */
	@RequestMapping("shoucangurl")
	public String shoucangurl(HttpServletRequest request,String title,int id) {
		//查去用户
		User loginUser = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		System.out.println(loginUser.getId());
		Shoucang shoucang = new Shoucang();
		shoucang.setUser_id(loginUser.getId());
		shoucang.setText(title);
		System.out.println(request.getRequestURI());
		//测试url是否正确
		if(StringUtils.isUrl("http://"+request.getRequestURI())){
			shoucang.setUrl("http://"+request.getRequestURI());
			System.out.println(shoucang);
			//添加收藏
			articleService.addshoucang(shoucang);
			return "redirect:detail?id="+id;
		}else {
			//地址收藏错误 
			System.err.println("url地址错误");
			request.setAttribute("err","地址错误无法收藏");
			Article article = articleService.findById(id);
			request.setAttribute("article", article);
		}
		
		
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
		
		/**
		 *跳转到投诉页面 
		 * @param request
		 * @param articleId
		 * @return
		 */
		@RequestMapping(value="complain",method=RequestMethod.GET)
		public String complain(HttpServletRequest request,int articleId) {
			
			Article article = articleService.getById(articleId);
			request.setAttribute("article",article);
			request.setAttribute("complain",new Complain());
			return "article/complain";
		}
		
		@RequestMapping(value="complain",method=RequestMethod.POST)
		public String complain(HttpServletRequest request,
				@ModelAttribute("complain") @Valid Complain complain,
				MultipartFile file,BindingResult result) throws IllegalStateException, IOException {
			
			if(!StringUtils.isUrl(complain.getSrcUrl())) {
				result.rejectValue("srcUrl", "", "不是合法的Url地址");
			}
			if(result.hasErrors()) {
				return "article/complain";
				
			}
			User loginUser = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
			String picUrl = this.processFile(file);
			complain.setPicture(picUrl);
			
			
			if(loginUser!=null) {
				complain.setUserId(loginUser.getId());
			}else {
				complain.setUserId(0);
			}
			
			articleService.addComplain(complain);
			return "redirect:/article/detail?id="+complain.getArticleId();
		}
		
		@RequestMapping("complains")
		public String complains(HttpServletRequest request,int articleId,
				@RequestParam(defaultValue="1")int page) {
			
			PageInfo<Complain> complianPage = articleService.getComplains(articleId,page);
			request.setAttribute("complianPage",complianPage);
			return "article/complainslist";
			
		}
		
}
