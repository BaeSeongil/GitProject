package com.project.mainPage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mainPage.dto.Order;
import com.project.mainPage.dto.Product;
import com.project.mainPage.mapper.OrderMapper;
import com.project.mainPage.mapper.ProductMapper;

@Controller
@RequestMapping("/order")
public class OrderConmtroller {
	@Autowired
	private OrderMapper orderMapper;
	
	@PostMapping("/inserts.do")
	public String insert(HttpSession session,Product product,Model model) {
		System.out.println(product);
		if(session.getAttribute("loginUsers")!=null) {
			Order proList = orderMapper.selectProduct(product.getProductid());
			System.out.println(proList);
			model.addAttribute("proList",proList);
			
			return "/order/insert";
		}else {
			return "redirect:/users/login.do";
		}
	}
	
	@PostMapping("/insert.do")
	public String insert(Order order) {
		System.out.println(order);
		return "redirect:/category/cate/1";
	
	}
	
}
