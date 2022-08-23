package com.project.mainPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.ShoppingBasket;


// com.project.mainPage.mapper.ShoppingBasketMappper
@Mapper
public interface ShoppingBasketMappper {
	int countByid(String userid);
	int insertOne(ShoppingBasket shoppingBasket);
	int deleteOne(int basket_id);
}
