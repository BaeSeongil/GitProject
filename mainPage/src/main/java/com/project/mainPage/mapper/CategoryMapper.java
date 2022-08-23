package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Category;

@Mapper
public interface CategoryMapper {
	// Category/cate/1
	List<Category> selectAll(int startRow,int pageSize);
	int selectAllCount();
	// Category/list/1
	List<Category> selectListAll(int startRow, int pageSize);
	int selectListAllCount();
	// Category/cate/1/1
	List<Category> selectCateAll(int categoryId, int startRow,int pageSize);
	int selectCateAllCount(int categoryId);
	Category selectOne(int categoryId);
	int insertOne(Category category);

	int updateOne(Category category);
	int deleteOne (int categoryId);
	
}

	
