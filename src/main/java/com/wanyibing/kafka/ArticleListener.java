package com.wanyibing.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.wanyibing.entity.Article;
import com.wanyibing.service.ArticleService;
 
public class ArticleListener implements MessageListener<String,String>{

	@Autowired
	ArticleService articleService;
	
	/**
	 * 就是监听消息的方法
	 */
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		//接受消息 
		String value = data.value();
		//如果以这个开头说明是流量晓峰的业务
		if(value.startsWith("user_view")) {
			String[] split = value.split("==");
			String id = split[1];
			//System.out.println(id);
			//1.根据id查询文章执行浏览量+1操作
			Article findById = articleService.findById(Integer.parseInt(id));
			//2.执行加一操作
			findById.setHits(findById.getHits()+1);
			//System.out.println(findById.getHits());
			//3.把执行后的进行报仇呢导数据
			//System.out.println("--"+findById);
			articleService.addliulan(Integer.parseInt(id));
			System.out.println("加+1成功");
			
		}else {
			//把json类型的串 转成article对象  入去爬虫信息
			System.err.println("收到消息");
			Article article = JSON.parseObject(value,Article.class);
			//保存再mysql的数据库中	
			
			articleService.add(article);
		}
		
	}

}
