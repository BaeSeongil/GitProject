package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;





import com.project.mainPage.dto.Pagination;

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
		int row = 10;
		int startRow = (page - 1) * row;
		List<Product> productList = productMapper.selectAll(startRow, row);
		int count = productMapper.selectAllCount();
		
		Pagination pagination = new Pagination(page, count, "/product/list/", row);
		System.out.println(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("productList", productList);
		model.addAttribute("row", row);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		return "/product/list";
	}

	
	  @GetMapping("/cate/{page}") public String prolist(@PathVariable int page,Model model) { 
			int row = 12;
			int startRow = (page - 1) * row;
			List<Product> productList = productMapper.selectAll(startRow, row);
			int count = productMapper.selectAllCount();


			Pagination pagination = new Pagination(page, count, "/product/cate/", row);
			System.out.println(pagination);
			model.addAttribute("pagination", pagination);
			model.addAttribute("productList", productList);
			model.addAttribute("row", row);
			model.addAttribute("count", count);
			model.addAttribute("page", page);
			return "/product/cate";
	}
	 

	@GetMapping("/detail/{productid}")
	public String detail(@PathVariable int productid, Model model) {
		Product product = null;
		product = productMapper.selectOne(productid);
		System.out.println(product);
		try {
			if (product != null) {
				model.addAttribute(product);
				return "/product/detail";
			} else {
				return "redirect:/product/cate/1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/product/cate/1";

	}
//	@GetMapping("/list/{page}")
//	public String list(@PathVariable int page, 
//					@PathVariable int startRow,
//					@PathVariable int pageSize,
//					Model model) {
//		int row = 10;
//		int startRow = (page - 1)*row;
//		List<Product> productList = productMapper.selectAll(startRow,row);
//		int count = productMapper.selectAllCount();
//		
//		Pagination pagination = new Pagination(page, count, "/product/list/", row);
//		System.out.println(pagination);
//		model.addAttribute("pagination",pagination);
//		model.addAttribute("productList",productList);
//		model.addAttribute("row",row);
//		model.addAttribute("count",count);
//		model.addAttribute("page",page);	
//		return "/product/list";
//	}
	


}
