package com.wanyibing.mapper;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.wanyibing.entity.Article;

public interface ArticleReP extends ElasticsearchCrudRepository<Article, Integer>{

	List<Article> findByTitle(String key);


}
