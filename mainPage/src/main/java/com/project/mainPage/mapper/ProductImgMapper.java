package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.ProductImg;

@Mapper
public interface ProductImgMapper {
	int insertOne (ProductImg productImg);
	List<ProductImg> selectProductId (int productid);

	ProductImg selectOne(int productImgNo);
	int deleteOne(int productImgNo);
	int selectCountProductId(int productid);
}
