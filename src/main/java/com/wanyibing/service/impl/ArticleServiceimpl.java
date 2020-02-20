package com.wanyibing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanyibing.Utils.CmsContant;
import com.wanyibing.entity.Article;
import com.wanyibing.entity.Category;
import com.wanyibing.entity.Channel;
import com.wanyibing.entity.Comment;
import com.wanyibing.entity.Complain;
import com.wanyibing.entity.Shoucang;
import com.wanyibing.entity.Slide;
import com.wanyibing.mapper.ArticleMapper;
import com.wanyibing.mapper.ArticleReP;
import com.wanyibing.mapper.SlideMapper;
import com.wanyibing.service.ArticleService;

@Service
public class ArticleServiceimpl implements ArticleService {

	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	SlideMapper slideMapper;
	
	@Autowired
	ArticleReP articleRep;
	
	//add
	public void save(Article article) {
		articleRep.save(article);
	}
	@Override
	public PageInfo<Article> listByUser(Integer id, int page) {
		 
		PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		PageInfo<Article> articlePage = new PageInfo<Article>(articleMapper.listByUser(id));
		
		return articlePage;
	}
	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return articleMapper.updateStatus(id,CmsContant.ARTICLE_STATUS_DEL);
	}
	@Override
	public List<Channel> getChannels() {
		// TODO Auto-generated method stub
		return articleMapper.getChannels();
	}
	@Override
	public List<Category> getcategoris(int cid) {
		// TODO Auto-generated method stub
		return articleMapper.getcategoris(cid);
	}
	@Override
	public int add(Article article) {
		// TODO Auto-generated method stub
		return articleMapper.add(article);
	}
	
	 
	
	
	@Override
	public Article getById(int id) {
		// TODO Auto-generated method stub
		return articleMapper.getById(id);
	}
	@Override
	public int update(Article article, Integer integer) {
		Article article2 = this.getById(article.getId());
		if(article2.getUserId() != integer) {
			//不是自己的文章
		}
		return articleMapper.update(article);
	}
	/**
	 * 管理员
	 */
	@Override
	public PageInfo<Article> list(int page, String status) {

		PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		PageInfo<Article> info = new PageInfo<>(articleMapper.list(status));
			
		return info;
	}
	
	/**
	 * 通过id查询字段
	 */
	@Override
	public Article findById(int id) {
		// TODO Auto-generated method stub
		return articleMapper.getById(id);
	}
	
	
	@Override
	public Article getInfoById(int id) {
		// TODO Auto-generated method stub
		return articleMapper.getInfoById(id);
	}
	/**
	 * 修改状态审核是否通过
	 */
	@Override
	public int setCheckStatus(int id, int status) {
		// TODO Auto-generated method stub
		return articleMapper.setCheckStatus(id,status);
	}
 
	@Override
	public int setHot(int id, int status) {
		return articleMapper.setHot(id,status);
	}
	/**
	 * 删除
	 */
	@Override
	public int deletearticle(int id) {
		// TODO Auto-generated method stub
		return articleMapper.deletearticle(id);
	}
	/**
	 * 获取轮播图
	 */
	@Override
	public List<Slide> getSlides() {
		// TODO Auto-generated method stub
		return slideMapper.getSlides();
	}
	/**
	 * 获取最新文章
	 */
	@Override
	public List<Article> lastList() {
		// TODO Auto-generated method stub
		return articleMapper.lastList(CmsContant.PAGE_SIZE);
	}
	/**
	 *获取热门文章 
	 */
	@Override
	public PageInfo<Article> hotList(int page) {
		// TODO Auto-generated method stub
		 PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		
		return new PageInfo<>(articleMapper.hotList());
	}
	/**
	 * 获取当前栏目下所有分类
	 */
	@Override
	public List<Category> getCategoriesByChannelId(int channelId) {
		// TODO Auto-generated method stub
		return articleMapper.getCategoriesByChannelId(channelId);
	}
	/**
	 * 评论
	 */
	@Override
	public int addComment(Comment comment) {
		// TODO Auto-generated method stub
				int result =  articleMapper.addComment(comment);
				 //文章评论数目自增
				if(result>0)
					articleMapper.increaseCommentCnt(comment.getArticleId());
				
				return result;
	}
	/**
	 * 查询评论
	 */
	@Override
	public PageInfo<Comment> getComments(int id, int page) {

		PageHelper.startPage(page, CmsContant.PAGE_SIZE);
		
		return new PageInfo<Comment>(articleMapper.getComments(id));
	}
	@Override
	public PageInfo<Article> getArticles(int channelId, int catId, int page) {
		PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		
		return new PageInfo<Article>(articleMapper.getArticles(channelId, catId));
	}
	
	/**
	 * 举报信息
	 */
	@Override
	public int addComplain(Complain complain) {
		
		//添加投诉到数据库
		int result = articleMapper.addCoplain(complain);
		//增加投诉的数量
		if(result>0) {
			articleMapper.increaseComplainCnt(complain.getArticleId());
		}
		
		return 0;
	}
	@Override
	public PageInfo<Complain> getComplains(int articleId, int page) {

		PageHelper.startPage(page, CmsContant.PAGE_SIZE);
		return new PageInfo<Complain>(articleMapper.getComplains(articleId));
	}
	/**
	 * 举报
	 */
	 @Override
	public PageInfo<Complain> complainlist(int page) {
		 PageHelper.startPage(page, CmsContant.PAGE_SIZE);
			return new PageInfo<Complain>(articleMapper.complainlist());
	}
	@Override
	public List<Article> getcommentCns() {
		// TODO Auto-generated method stub
		return articleMapper.getcommentCns();
	}
	/**
	 * 删除es数据
	 */
	@Override
	public void del(int id) {
		// TODO Auto-generated method stub
		articleRep.deleteById(id);
	}
	@Override
	public void addliulan(int id) {
		// TODO Auto-generated method stub
		articleMapper.addliulan(id);
	}
	/**
	 * 收藏
	 */
	@Override
	public PageInfo<Shoucang> listShoucang(Integer id, int page) {
		PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		  PageInfo<Shoucang> articlePage = new PageInfo<Shoucang>(articleMapper.listShoucang(id));
		
		return articlePage;
	}
	@Override
	public int deleteshoucang(int id) {
		// TODO Auto-generated method stub
		return articleMapper.deleteshoucang(id);
	}
	@Override
	public void addshoucang(Shoucang shoucang) {
		 
		articleMapper.addshoucang(shoucang);
	}
 
}
