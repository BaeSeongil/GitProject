package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mainPage.dto.Category;
import com.project.mainPage.dto.Pagination;
import com.project.mainPage.dto.Product;
import com.project.mainPage.mapper.CategoryMapper;
import com.project.mainPage.mapper.ProductMapper;

@Controller
@RequestMapping("/category")
public class CategoryController {

		@Autowired
		private CategoryMapper categoryMapper;
	
		@Autowired
		private ProductMapper productMapper;
		
		@GetMapping("/list/{cate}")
		public String list(@PathVariable String cate, 
							Model model) {
			
			
			List<Product> productList = categoryMapper.selectCategoryAll();
			System.out.println("위"+productList);
			model.addAttribute(productList);
//			int row = 10;
//			int startRow = (cate - 1);
//
//			List<Category> categoryList = categoryMapper.selectCategoryAll(startRow, row);
//			
//			int count = categoryMapper.selectAllCount();
//			System.out.println(categoryList);
//			Pagination pagination = new Pagination(page, count, "/category/list/cate/", row);		
//			
//			model.addAttribute("cate",cate);
//			model.addAttribute("pagination", pagination);
//			
//			model.addAttribute("categoryList", categoryList);
//			model.addAttribute("productList", productList);
//			model.addAttribute("row",row);
//			model.addAttribute("count", count);
//			model.addAttribute("page",page);
			return "/category/list";
		}
		
		
		
}
