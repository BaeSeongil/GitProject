package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mainPage.dto.Category;
import com.project.mainPage.mapper.CategoryMapper;

@Controller
@RequestMapping("/category")
public class CategoryController {

		@Autowired
		private CategoryMapper categoryMapper;
	
		@GetMapping("/list/{page}")
		public String list(@PathVariable int page, Model model) {
			List<Category> categoryList = categoryMapper.selectCategoryAll(page);
			System.out.println(categoryList);
			model.addAttribute(categoryList);
			return "/category/list";
		}
		
		@GetMapping("/list/{page}/{cate}")
		public String list(@PathVariable int page, @PathVariable int cate, Model model) {
			List<Category> categoryList = categoryMapper.selectCategoryAll(page, cate);
			System.out.println(categoryList);
			model.addAttribute(categoryList);
			return "/category/list";
		}
}
