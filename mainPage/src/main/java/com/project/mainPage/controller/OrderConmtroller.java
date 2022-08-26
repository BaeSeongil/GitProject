package com.project.mainPage.controller;

import javax.servlet.http.HttpServletRequest;
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
	
	@GetMapping("/inserts.do")
	public String insert(HttpSession session,int count,int productid,Model model) {
		System.out.println(productid);
		System.out.println(count);
		if(session.getAttribute("loginUsers")!=null) {
			Order proList = orderMapper.selectProduct(productid);

			System.out.println(proList);
			model.addAttribute("productid",productid);
			model.addAttribute("proList",proList);
			model.addAttribute("count",count);

			return "/order/insert";
		}else {
			return "redirect:/users/login.do";
		}
	}
	
	@PostMapping("/insert.do")
	public String insert(Order order  , HttpServletRequest request,HttpSession session) {
		System.out.println(order);
		String prevPage=request.getHeader("Referer");//요청한 페이지의 이전 페이지(로그인하면 되돌아갈 페이지)
		session.setAttribute("redirectPage", prevPage);

		int insert = 0; 
		try {
			insert = orderMapper.insertOne(order);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(insert>0) {
			return "redirect:/category/cate/1";			
		}else {
			return "redirect:"+prevPage;
		}
		
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		
		return "/order/list";
	}
	
}
