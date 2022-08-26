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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.mainPage.dto.Pagination;
import com.project.mainPage.dto.Product;
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
		int insert = 0; 
		if(session.getAttribute("loginUsers")!=null) {
			basket.setUserid(loginUsers.getUserid());
			System.out.println(basket);
			insert=basketMappper.insertOne(basket);
			//장바구니 페이지로 이동 
		}
		if(insert>0) {
			return "redirect:/basket/list/1";				
		}else {
			return "redirect:/product/detail/"+ basket.getProduct().getProductid();
		}
	}
	
	@GetMapping("/list/{page}")
	public String list(
			@PathVariable int page,
			Model model){
		int row = 5;
		int startRow = (page - 1) * row ;
		List<ShoppingBasket> basketList = basketMappper.selectAll(startRow,  row);
		System.out.println("basketList : "+basketList);
		int count = basketMappper.selectAllCount();
		Pagination pagination = new Pagination(page,count,  "/basket/list/", row);
		System.out.println(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("basketList",basketList);
		model.addAttribute("row", row);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		return "/basket/list";
	}; 
	
	@GetMapping("/user/delete/{userId}")
	public String delete(
			@PathVariable String userId,
			@SessionAttribute(name = "loginUsers",required = false) UsersDto loginUsers) {
		if(loginUsers!=null && loginUsers.getUserid().equals(userId)) {
			int delete=0;
			try {				
				delete=basketMappper.deleteUser(userId);
			} catch (Exception e) {e.printStackTrace();}
			if(delete>0) {
				System.out.println("전체선택삭제 성공 : "+ delete );
				return "redirect:/basket/list/1";			
			}else {
				return "redirect:/basket/list/1";			
			}			
		}else {
			return "redirect:/users/login.do";
		}
	}

	@PostMapping("/selected/delete") //?userId=dd&basket_id=1&basket_id=3 ..
	public String delete(
			@RequestParam("basket_id") int [] basketIds, 
			@RequestParam String userId,
			@SessionAttribute(name = "loginUsers",required = false) UsersDto loginUsers
			) {
		if(loginUsers!=null && loginUsers.getUserid().equals(userId)) {
			int delete=0;
			try {
				 
				for(int basketId:basketIds) { 
					delete+=basketMappper.deleteOne(basketId);					
				}
			
			} catch (Exception e) {e.printStackTrace();}
			if(delete>0) {
				System.out.println("선택삭제 성공 : "+ delete );
				return "redirect:/basket/list/1";			
			}else {
				return "redirect:/basket/list/1";			
			}			
		}else {
			return "redirect:/users/login.do";
		}
		
	}
	
	
	

}