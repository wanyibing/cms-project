package com.wanyibing;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.wanyibing.cms.utils.FileUtils;
import com.wanyibing.entity.Article;

/**
 * 读取爬虫取出文章  吧文本的名称作为文章内容,封装道ARticle对象 把对象发送到kafka
 * @author 万一兵
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:producer.xml")
public class SenArticles2Kafka {

	@Autowired
	KafkaTemplate<String,String> kafkaTemplate;
	
	@Test
	public void testSenarticles() throws Exception {
		
		File file = new File("D:\\爬虫");
		//遍历file路径下的所有文件
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			//.replace javaString 替换
			//获取文章名字
			String title = file2.getName().replace(".txt", "");
			String content = FileUtils.readFile(file2, "utf8");
			//System.out.println(content);
			//声明一个对象
			Article article = new Article();
			article.setTitle(title);
			article.setContent(content);
			article.setPicture("D:pic");
			article.setChannelId(2);
			article.setUserId(71);
			article.setCategoryId(6);
			article.setArticleType(0);
			article.setLiulan(0);
			
			//然后对象转成json
			String jsonString = JSON.toJSONString(article);
			//发送到kafka
			kafkaTemplate.send("articles", jsonString);
			
			
			
			
			
		}
		
		
	}
	
	
}
