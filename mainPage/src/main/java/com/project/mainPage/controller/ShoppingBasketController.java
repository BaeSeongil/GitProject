package com.project.mainPage.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.mainPage.dto.ShoppingBasket;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.mapper.ShoppingBasketMappper;

@Controller
@RequestMapping("/basket")
public class ShoppingBasketController {
	
	
	@Autowired
	ShoppingBasketMappper basketMappper;
	@PostMapping("/insert.do")
	public String insert(
			ShoppingBasket basket,
			@SessionAttribute UsersDto loginUsers,
			HttpSession session) {
		if(session.getAttribute("loginUsers")!=null) { 
			basket.setUserid(loginUsers.getUserid());
			System.out.println(basket);
			int insert=basketMappper.insertOne(basket);
			//장바구니 페이지로 이동 
			if(insert>0) {
				return "redirect:/basket/list";				
			}
		}
		return "redirect:/";			
	}
	
	@GetMapping("/list/{page}")
	public String list(
			@PathVariable int page,
			Model model){
		List<ShoppingBasket> basketList = basketMappper.selectAll();
		System.out.println("basketList : "+basketList);
		model.addAttribute(basketList);
		return "/basket/list";
	};
	
	
	
	
	
	

}