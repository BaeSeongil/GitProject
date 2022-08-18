package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Category;
import com.project.mainPage.dto.Product;

@Mapper
public interface CategoryMapper {

	List<Category> selectAll(int startRow,int pageSize);
	int selectAllCount();
	
	List<Category> selectCateAll(int categoryId, int startRow,int pageSize);
	int selectCateAllCount(int categoryId);
}
