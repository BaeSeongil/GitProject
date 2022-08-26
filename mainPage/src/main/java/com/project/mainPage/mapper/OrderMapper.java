package com.project.mainPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Order;
//com.project.mainPage.mapper.OrderMapper
@Mapper
public interface OrderMapper {
	
	Order selectProduct(int productid);
	int insertOne(Order order);
}