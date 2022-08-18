package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Category;
import com.project.mainPage.dto.Product;

@Mapper
public interface CategoryMapper {
	
	List<Product> selectCategoryAll();
	List<Category> selectCategoryAll(int startRow, int pageSize);
	int selectAllCount();
	Category selectOne(int categoryid);
	List<Product> selectCateProduct(String categoryName);
}
