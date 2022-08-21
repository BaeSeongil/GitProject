package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Category;

@Mapper
public interface CategoryMapper {
	List<Category> selectCategoryAll(int page);
	List<Category> selectAll(int startRow,int pageSize);
	int selectAllCount();
	
	List<Category> selectCateAll(int categoryId, int startRow,int pageSize);
	int selectCateAllCount(int categoryId);
	int insertOne(Category category);
	List<Category> selectListAll(int startRow, int row);
	int selectListAllCount();
	Category selectOne(int categoryId);
	int updateOne(Category category);
	int deleteOne(int categoryId);
}
