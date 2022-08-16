package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mainPage.dto.Product;
import com.project.mainPage.mapper.ProductMapper;
import com.project.mainPage.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductMapper productMapper;

	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model) {
		List<Product> productList = productMapper.selectPageAll();
		System.out.println(productList);
		model.addAttribute(productList);
		return "/product/list";
	}
	
	@GetMapping("/detail/{productid}")
	public String detail(@PathVariable int productid, Model model) {
		Product product = null;
		product = productMapper.selectOne(productid);
		System.out.println(product);
		try {
			if (product!=null) {
			model.addAttribute(product);
			return "/product/detail";
			} else {
				return "redirect:/product/list/1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/product/list/1";
			
	}
	
}
