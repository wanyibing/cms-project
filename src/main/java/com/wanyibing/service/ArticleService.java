package com.wanyibing.service;

import java.util.List;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.wanyibing.entity.Article;
import com.wanyibing.entity.Category;
import com.wanyibing.entity.Channel;
import com.wanyibing.entity.Comment;
import com.wanyibing.entity.Complain;
import com.wanyibing.entity.Shoucang;
import com.wanyibing.entity.Slide;
 
public interface ArticleService {
	
	void save(Article article);

	PageInfo<Article> listByUser(Integer id, int page);

	int delete(int id);

	List<Channel> getChannels();

	List<Category> getcategoris(int cid);

	int add(Article article);

	int update(Article article, Integer integer);

	Article getById(int id);

	PageInfo<Article> list(int page, String status);

	//文章详情
	Article findById(int id);

	Article getInfoById(int id);

	int setCheckStatus(int id, int status);

	int setHot(int id, int status);

	int deletearticle(int id);

	PageInfo<Article> hotList(int page);

	List<Article> lastList();

	List<Slide> getSlides();

	List<Category> getCategoriesByChannelId(int channelId);

	int addComment(Comment comment);

	PageInfo<Comment> getComments(int id, int page);

	PageInfo<Article> getArticles(int channelId, int catId, int page);

	int addComplain(Complain complain);

	PageInfo<Complain> getComplains(int articleId, int page);

	PageInfo<Complain> complainlist(int page);

	List<Article> getcommentCns();

	void del(int id);

	void addliulan(int id);

	PageInfo<Shoucang> listShoucang(Integer id, int page);

	int deleteshoucang(int id);

	void addshoucang(Shoucang shoucang);

	 
}
