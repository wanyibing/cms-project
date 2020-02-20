package com.wanyibing;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wanyibing.cms.utils.FileUtils;
import com.wanyibing.entity.Article;
import com.wanyibing.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class Demo {

	@Autowired 
	ArticleService articleService;
	
	
	//测试添加成功
	@Test
	public void addArticle() throws IOException {
		File file = new File("D:\\爬虫\\【注意】年轻姑娘一上班就累！医生：这是病，得治!.txt");
		 String readFile = FileUtils.readFile(file, "utf-8");
		 
		 Article article = new Article();
		 article.setTitle("“命悬一线”的刺激：玻璃栈道“生死劫”");
		 article.setContent(readFile.substring(140));
		 article.setPicture("");
		 article.setChannelId(1);
		 article.setCategoryId(2);
		 article.setUserId(46);
		 article.setArticleType(0000000000);
		 System.out.println(readFile);
		 articleService.add(article);
			
	}
	
	
}
