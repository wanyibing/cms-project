package com.wanyibing.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.wanyibing.Utils.CmsContant;
import com.wanyibing.cms.utils.FileUtils;
import com.wanyibing.cms.utils.HtmlUtils;
import com.wanyibing.cms.utils.StringUtils;
import com.wanyibing.entity.Article;
import com.wanyibing.entity.Category;
import com.wanyibing.entity.Channel;
import com.wanyibing.entity.User;
import com.wanyibing.service.ArticleService;
import com.wanyibing.service.UserService;

@Controller
public class UserController {
 
	@Value("${upload.path}")
	String picRootPath;
	
	@Value("${pic.path}")
	String picUrl;
	
	@Autowired 
	private UserService service; 
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("logout")
	public String home(HttpServletRequest request) {
		request.getSession().removeAttribute(CmsContant.USER_KEY);
		return "redirect:/";
	}
	
	@RequestMapping(value="register",method=RequestMethod.GET)
	public String login1(HttpServletRequest request) {
		User user = new User(); 
		request.setAttribute("user", user);
		return "user/register";
	}
	
	@RequestMapping(value="register",method=RequestMethod.POST)
	public String register(HttpServletRequest request,
			@Valid @ModelAttribute("user")User user,
			BindingResult result) {
		//如果有错误就返回注册页面
		if(result.hasErrors()) {
			return "user/register";
		}
		
		//进行唯一验证
		User  existUser = service.getUserName(user.getUsername());
		if(existUser!=null) {
			result.rejectValue("username","", "用户名已经存在");
			return "user/register";
		}
		
		
		//加一个手动的校验
			if(StringUtils.isNumber(user.getPassword())) {
					result.rejectValue("password", "", "密码不能全是数字");
					return "user/register";
			}
			
			
			// 去注册
		int reRegister = service.register(user);
				
				//注册失败
				if(reRegister<1) {
					request.setAttribute("eror", "注册失败，请稍后再试！");
					return "user/register";
				}
		return "user/login";
	}
	
	@RequestMapping("checkname")
	@ResponseBody
	public boolean checkUserName(String username) {
		User existUser = service.getUserName(username);
		return existUser==null;
	}
	/**
	 * 转入登录页面
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.GET) 
	public String login() {
		 
		return "user/login";
	}
	/**
	 * 
	 */
	@RequestMapping(value="login",method=RequestMethod.POST) 
	public String login(HttpServletRequest request,User user,HttpServletResponse response) {
		String pwd = new String(user.getPassword());
		User loginUser = service.login(user);
		
		if(loginUser==null) {
			request.setAttribute("error", "用户名密码错误");
			return "user/login";
		}
		
		//登录成功  用户信息存放到session当中
		request.getSession().setAttribute(CmsContant.USER_KEY, loginUser);
		
		Cookie cookieUserName = new Cookie("username", user.getUsername());
		cookieUserName.setPath("/");
		cookieUserName.setMaxAge(10*24*3600);
		response.addCookie(cookieUserName);
		Cookie cookieUserPwd = new Cookie("userpwd",pwd);
		cookieUserPwd.setPath("/");
		cookieUserPwd.setMaxAge(10*24*3600);
		response.addCookie(cookieUserPwd);
		
		
	/*	Cookie cookie = new Cookie("password", user.getPassword());
		response.addCookie(cookie);
		*/
		
		//管理界面
		/*if(loginUser.getRole()==CmsContant.USER_ROLE_ADMIN)  
			return "redirect:/admin/index";*/
	 
		 
		return "redirect:home";
	}
	
	@RequestMapping("home")
	public String home() {
		
		return "user/home";
	}
	/**
	 * 显示文章
	 */
	@RequestMapping("articles")
	public String articles(HttpServletRequest request,@RequestParam(defaultValue="1")int page) {
		
		User loginUser = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		
		PageInfo<Article> articlePage = articleService.listByUser(loginUser.getId(),page);
		request.setAttribute("articlePage", articlePage);
		
		return "user/article/list";
	}
	/**
	 * 删除文章
	 */
	@RequestMapping("deletearticle")
	@ResponseBody
	public boolean deletearticle(int id) {
		int result  = articleService.delete(id);
		return result > 0;
	}
	@RequestMapping("postArticle")
	public String postArticle(HttpServletRequest request) {
		
		List<Channel> channels = articleService.getChannels();
		request.setAttribute("channels", channels);
		
		return "user/article/post";
	}
	
	@RequestMapping("getCategoris")
	@ResponseBody
	public List<Category> getCategoris(int cid){
		List<Category> categories = articleService.getcategoris(cid);
		return categories;
	}
	/**
	 * 上传博客文件
	 */
	@RequestMapping(value="postArticle",method=RequestMethod.POST)
	@ResponseBody
	public  boolean postArticle(HttpServletRequest request,Article article,MultipartFile file) {
		
		String picUrl;
		try {
			picUrl = processFile(file);
			article.setPicture(picUrl);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User loginUser = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		article.setUserId(loginUser.getId());
		
		
		return articleService.add(article)>0;
		
	}

	/**
	 * 放图片
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	private String processFile(MultipartFile file) throws IllegalStateException, IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String subPath = sdf.format(new Date());
		
	 
		File path = new File(picRootPath+"/"+subPath);
		
		if (!path.exists()) {
			path.mkdirs();
		}
		//计算新的文件名称
		String suffixName= FileUtils.getSuffixName(file.getOriginalFilename());
		//随机生成文件名
		String fileName = UUID.randomUUID().toString()+suffixName;
		file.transferTo(new File(picRootPath+"/"+subPath+"/"+fileName));
		
		return subPath+"/"+fileName;
	}
	
	/**
	 * 跳转修改页面
	 * @param request
	 * @param article
	 * @param file
	 * @return
	 */
	@RequestMapping(value="updateArticle",method=RequestMethod.GET)
	public String updateArticle(HttpServletRequest request,int id) {
		
		//获取栏目
		List<Channel> channels= articleService.getChannels();
		request.setAttribute("channels", channels);
		
		//获取文章
		Article article = articleService.getById(id);
		User loginUser = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		if(loginUser.getId() != article.getUserId()) {
			// todo 准备做异常处理的！！
			
		}
		request.setAttribute("article", article);
		request.setAttribute("content1",  HtmlUtils.htmlspecialchars(article.getContent()));
		
		return "user/article/update";
		
	}
	
	
	@RequestMapping(value="updateArticle",method=RequestMethod.POST)
	@ResponseBody
	public boolean updateArticle(HttpServletRequest request,Article article,MultipartFile file) {
		
		System.out.println("aartcle is "+article);
		String picUrl;
		try {
			// 处理上传文件
			picUrl = processFile(file);
			article.setPicture(picUrl);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//当前用户是文章的作者
			User loginUser = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
			 
			
			int updateREsult  = articleService.update(article,loginUser.getId());
		
		return updateREsult>0;
	}
}
