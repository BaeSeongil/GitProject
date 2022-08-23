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
import com.project.mainPage.mapper.ShoppingBasketMappper;

@Controller
@RequestMapping("/basket")
public class ShoppingBasketController {
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	ShoppingBasketMappper basketMappper;
	@PostMapping("/insert.do")
	public String insert(
			ShoppingBasket basket,
			@SessionAttribute UsersDto loginUsers,
			HttpSession session) {
		if(session.getAttribute("loginUsers")!=null) {
			//int count = 
			basket.setUserid(loginUsers.getUserid());
			System.out.println(basket);
			int insert=basketMappper.insertOne(basket);
			//장바구니 페이지로 이동 
			
		}
		
		return "redirect:/";
	}

}