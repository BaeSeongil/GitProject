package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.mainPage.dto.Category;
import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.IdCheck;
import com.project.mainPage.dto.Pagination;

import com.project.mainPage.dto.Product;
import com.project.mainPage.mapper.CategoryMapper;
import com.project.mainPage.mapper.ProductMapper;
import com.project.mainPage.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private CategoryMapper categoryMapper;

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
 
	@GetMapping("/detail/{productid}")
	public String detail(@PathVariable int productid, Model model) {
		Product product = null;
		product = productMapper.selectOne(productid);
		System.out.println(product);
		try {
			if (product != null) {
				List<Product> products = productMapper.selectByProductName(product.getProductName());
				products
					.stream()
					.filter((p)->p.getProductName().equals("productName"))
					.distinct();
				System.out.println(products);
				model.addAttribute("product",product);
				model.addAttribute("products",products);
			
				return "/product/detail";
			} else {
				return "redirect:/product/cate/1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/product/cate/1";
	}
	@GetMapping("/insert.do")
	public void insert() {};
	@PostMapping("insert.do")
	public String insert(Product product) {
		
		return "/product/insert";
	}
	@GetMapping("/idCheck/{categoryId}")
	public @ResponseBody IdCheck idCheck(@PathVariable int categoryId) {
		IdCheck idCheck = new IdCheck();
		Category cate = categoryMapper.selectOne(categoryId);
		if(cate!=null) {
			idCheck.idCheck=true;
			idCheck.cate=cate;
		}
		return idCheck;
	}
	@GetMapping("/search")
	public String searchProduct(Criteria cri, Model model) {
		List<Product> list=productService.searchProduct(cri);
		if(!list.isEmpty()) {
			model.addAttribute("list",list);
		}else {
			model.addAttribute("listCheck","empty");
			
			return "/search";
		}
		model.addAttribute("pageMaker",new Pagination(cri, productService.productsGetTotal(cri)));
		return "/search";
	}
	
}
