package com.wanyibing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.wanyibing.entity.Slide;

public interface SlideMapper {

	@Select("select id,title,picture,url from cms_slide order by id")
	List<Slide> getSlides();

	
	
}
