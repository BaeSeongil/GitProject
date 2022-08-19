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
		
		@GetMapping("/list/{page}")
		public String list(@PathVariable int page, Model model) {
			List<Category> categoryList = categoryMapper.selectCategoryAll(page);
			System.out.println(categoryList);
			model.addAttribute(categoryList);
			return "/category/list";
		}
		
		  @GetMapping("/cate/{page}") 
		  public String prolist(@PathVariable int page,Model model) { 
				int row = 12;
				int startRow = (page - 1) * row;
				List<Category> categoryList = categoryMapper.selectAll(startRow,row);
				int count = categoryMapper.selectAllCount();


				Pagination pagination = new Pagination(page, count, "/category/cate/", row);
				System.out.println(pagination);
				model.addAttribute("pagination", pagination);
				model.addAttribute("categoryList", categoryList);
				model.addAttribute("row", row);
				model.addAttribute("count", count);
				model.addAttribute("page", page);
				return "/category/cate";
		}
		
		  @GetMapping("/cate/{categoryId}/{page}") 
		  public String prolist(@PathVariable int page,@PathVariable int categoryId ,Model model) { 
				int row = 12;
				int startRow = (page - 1) * row;
				List<Category> categoryList = categoryMapper.selectCateAll(categoryId,startRow,row);
				int count = categoryMapper.selectCateAllCount(categoryId);

 
				Pagination pagination = new Pagination(page, count, "/category/cate/"+categoryId+"/", row);
				System.out.println(pagination);
				model.addAttribute("pagination", pagination);
				model.addAttribute("categoryList", categoryList);
				model.addAttribute("row", row);
				model.addAttribute("count", count);
				model.addAttribute("page", page);
				return "/category/cate";
		}
		
}
