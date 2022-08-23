package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.Product;

@Mapper
public interface ProductMapper {


	List<Product> selectAll(int startRow, int pageSize);

	List<Product> selectAll();
	List<Product> selectByProductName(String productName);
	int selectAllCount();
	Product selectOne(int productid);
	//String selectNameOne(String productName);

	//상품 검색
	public List<Product> searchProduct(Criteria cri);
	//상품 총 갯수
	public int productsGetTotal(Criteria cri);
	
	public List<Product> selectSearchAll(Criteria cri);
	//상품 리스트 요청
	public String[] getProductIdList(String keyword);
	
	int updateOne (Product product);

}