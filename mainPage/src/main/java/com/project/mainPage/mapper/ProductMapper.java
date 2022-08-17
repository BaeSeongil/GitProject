package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Product;

@Mapper
public interface ProductMapper {

	List<Product> selectPageAll();
	
	Product selectOne(int productid);
}
