package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mainPage.dto.Category;
import com.project.mainPage.dto.Pagination;
import com.project.mainPage.mapper.CategoryMapper;

@Controller
@RequestMapping("/category")
public class CategoryController {

		@Autowired
		private CategoryMapper categoryMapper;
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
		  // 관리자만 할 수 있게 해야 하는데 어떻게.. 하지..........?
		  // 접근이 안 되는 거라 괜찮나요..? ㅠ_ㅠ 
		  // 네 접근자체가 불가능합니다.

		  @GetMapping("/insert.do")
		  public void insert() {}
		  
		  @PostMapping("/insert.do")
		  public String insert(Category category) {
			  int insert = 0;
			  try {
				  insert = categoryMapper.insertOne(category);
				  System.out.println("등록 성공");
			} catch (Exception e) {e.printStackTrace();}
			  if (insert >0) {
				  return "redirect:/category/list/1";
			  } else {
				  return "redirect:/category/insert.do";
			  }
		  }
		  @GetMapping("/list/{page}") 
		  public String catelist(@PathVariable int page,Model model) { 
				int row = 10;
				int startRow = (page - 1) * row;
				List<Category> categoryList = categoryMapper.selectListAll(startRow,row);
				int count = categoryMapper.selectListAllCount();

				Pagination pagination = new Pagination(page, count, "/category/list/", row);
				System.out.println(pagination);
				model.addAttribute("pagination", pagination);
				model.addAttribute("categoryList", categoryList);
				model.addAttribute("row", row);
				model.addAttribute("count", count);
				model.addAttribute("page", page);
				return "/category/list";

		}
		  
		  @GetMapping("/detail/{categoryId}")
		  public String detail (
				  @PathVariable int categoryId, Model model
				  ) {
			  Category category = categoryMapper.selectOne(categoryId);
			  System.out.println(category);
			  model.addAttribute(category);
			  return "/category/detail";
		  }
		  
		  @PostMapping("/update.do")
		  public String update(Category category) {
			  int update = 0;
			  try {
				  update = categoryMapper.updateOne(category);
				  System.out.println(update);
				  System.out.println(category.getCategoryName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			  if (update>0) {
				   return "redirect:/category/list/1";
			  } else {
				  return "redirect:/category/detail/"+category.getCategoryId();
			  }
			 
		  }
		  @GetMapping("/delete/{categoryId}")
		  public String delete (@PathVariable int categoryId) {
			  int delete = 0;
			  
			  try {
				delete = categoryMapper.deleteOne(categoryId);
				System.out.println("삭제 성공");
			} catch (Exception e) {
				e.printStackTrace();
			}
			  if (delete>0) {
				  return "redirect:/category/list/1";
			  } else {
				  return "redirect:/category/detail/"+categoryId;
			  }
			  
		  }
}
