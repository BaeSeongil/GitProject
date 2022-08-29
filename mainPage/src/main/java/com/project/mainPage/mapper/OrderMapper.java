package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Order;
import com.project.mainPage.dto.Product;
//com.project.mainPage.mapper.OrderMapper
@Mapper
public interface OrderMapper {
	
	Order selectProduct(int productid);
	Order selectUsers(String userid);
	Order selectOne(int orderid);
	
	int insertOne(Order order);
	int deleteOne(int orderid);
	
	List<Order> selectAll();
}
