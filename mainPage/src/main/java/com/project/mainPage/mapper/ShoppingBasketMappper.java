package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.ShoppingBasket;


// com.project.mainPage.mapper.ShoppingBasketMappper
@Mapper
public interface ShoppingBasketMappper {
	int countByid(String userid);
	int insertOne(ShoppingBasket shoppingBasket);
	int deleteOne(int basket_id);
	List<ShoppingBasket> selectAll();
}
