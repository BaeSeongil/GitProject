package com.project.mainPage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mainPage.mapper.OrderMapper;

@Controller
@RequestMapping("/order")
public class OrderConmtroller {
	@Autowired
	private OrderMapper orderMapper;
	
	@GetMapping("/insert.do")
	public String insert(HttpSession session) {		
		if(session.getAttribute("loginUsers")!=null) {
			return "/order/insert";
		}else {
			return "redirect:/users/login.do";
		}
	}
	
	
}
