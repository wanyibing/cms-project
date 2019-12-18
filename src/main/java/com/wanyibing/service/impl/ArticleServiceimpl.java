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
import com.wanyibing.entity.Slide;
import com.wanyibing.mapper.ArticleMapper;
import com.wanyibing.mapper.SlideMapper;
import com.wanyibing.service.ArticleService;

@Service
public class ArticleServiceimpl implements ArticleService {

	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	SlideMapper slideMapper;
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
	public PageInfo<Article> list(int page) {

		PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		PageInfo<Article> info = new PageInfo<>(articleMapper.list());
			
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
	
}
