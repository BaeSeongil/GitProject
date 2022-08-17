package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Category;

@Mapper
public interface CategoryMapper {
	
	List<Category> selectCategoryAll(int page);
	List<Category> selectCategoryAll(int startRow, int pageSize);
	int selectAllCount();
	Category selectOne(int categoryid);
}
