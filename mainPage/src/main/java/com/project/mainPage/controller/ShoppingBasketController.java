package com.project.mainPage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.mainPage.dto.ShoppingBasket;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.mapper.ProductMapper;

@Controller
@RequestMapping("/basket")
public class ShoppingBasketController {
	
	@Autowired
	ProductMapper productMapper;
	
	@PostMapping("/insert.do")
	public String insert(
			ShoppingBasket basket,
			@SessionAttribute(name ="loginUsers",required = false) UsersDto loginUsers) {
	
		if(loginUsers != null) {
			System.out.println(basket);
		}
		
		return null;
	}

}