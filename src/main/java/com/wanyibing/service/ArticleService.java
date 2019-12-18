package com.wanyibing.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wanyibing.entity.Article;
import com.wanyibing.entity.Category;
import com.wanyibing.entity.Channel;
import com.wanyibing.entity.Slide;

public interface ArticleService {

	PageInfo<Article> listByUser(Integer id, int page);

	int delete(int id);

	List<Channel> getChannels();

	List<Category> getcategoris(int cid);

	int add(Article article);

	int update(Article article, Integer integer);

	Article getById(int id);

	PageInfo<Article> list(int page);

	Article findById(int id);

	Article getInfoById(int id);

	int setCheckStatus(int id, int status);

	int setHot(int id, int status);

	int deletearticle(int id);

	PageInfo<Article> hotList(int page);

	List<Article> lastList();

	List<Slide> getSlides();

}
