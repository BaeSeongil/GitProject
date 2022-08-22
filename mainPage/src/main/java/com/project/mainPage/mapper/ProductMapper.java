package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.Product;

@Mapper
public interface ProductMapper {

	List<Product> selectAll(int startRow, int pageSize);
	List<Product> selectAll();
	int selectAllCount();
	Product selectOne(int productid);
	
	public List<Product> searchProduct(Criteria cri);
	public int productsGetTotal(Criteria cri);
	
}