package com.wanyibing;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wanyibing.entity.Article;
import com.wanyibing.mapper.ArticleMapper;
import com.wanyibing.mapper.ArticleReP;
import com.wanyibing.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class ImporMysql2Es {

	
	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	ArticleReP articleReP;
	
	@Test
	public void importMysql2Es() {
		//1.从mysql种已经审核通过所有的文章
		List<Article> articles = articleMapper.findAllArticleWithStatus(1);
		//2.查询出来的文章保存再es的索引库
	  	
		articleReP.saveAll(articles);
		
	}
	
	//查询
	@Test
	public void findMysql2Es() {
		//1.从mysql种已经审核通过所有的文章
		//2.查询出来的文章保存再es的索引库
	  	
		List<Article> articles =articleReP.findByTitle("我");
		for (Article article : articles) {
			System.out.println(article);
		}
		
	}
	
}
