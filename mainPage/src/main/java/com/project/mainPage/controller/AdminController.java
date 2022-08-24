package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.mainPage.dto.Board;
import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.Notice;
import com.project.mainPage.dto.Pagination;
import com.project.mainPage.dto.Product;
import com.project.mainPage.mapper.BoardMapper;
import com.project.mainPage.mapper.CategoryMapper;
import com.project.mainPage.mapper.NoticeMapper;
import com.project.mainPage.mapper.ProductMapper;
import com.project.mainPage.mapper.ReplyMapper;

@Controller
public class AdminController {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private NoticeMapper noticeMapper;
	
	
	@GetMapping("/admin/main")
	public void adminMain() throws Exception {}
	
	@GetMapping("/search/{page}")
	public String searchProduct(
			@RequestParam(required = false) String type,
			@RequestParam(defaultValue = "search") String keyword,
			@PathVariable int page, Criteria cri, Model model) {
		System.out.println(keyword);
		int row = 5;
		int startRow = (page - 1) * row;
		cri.setAmount(row);
		cri.setSkip(startRow);
		Pagination pagination;
		Pagination pagination2;
		Pagination pagination3;
		int count = 0;
		List<Product> productList=productMapper.searchAllProduct(cri);
		int productCount = productMapper.productsAllGetTotal(cri);
		if(!productList.isEmpty()) {
			model.addAttribute("productList",productList);
			model.addAttribute("row", row);
			model.addAttribute("productCount", productCount);
			model.addAttribute("page", page);
			pagination = new Pagination(page, productCount, "/search/", row);
			model.addAttribute("pagination", pagination);
		}else {
			model.addAttribute("listCheck","empty"); 
			return "/search"; }
		
		List<Board> boardList= boardMapper.searchAllBoard(cri);
		int boardCount = boardMapper.boardAllGetTotal(cri);
		if(!boardList.isEmpty()) {
			model.addAttribute("boardList",boardList);
			model.addAttribute("row", row);
			model.addAttribute("boardCount", boardCount);
			model.addAttribute("page", page);
			pagination2 = new Pagination(page, boardCount, "/search/", row);
			model.addAttribute("pagination2", pagination2);
		}else {
			model.addAttribute("listCheck","empty"); 
			return "/search"; }  
		
		List<Notice> noticeList= noticeMapper.searchAllNotice(cri);
		int noticeCount = noticeMapper.noticeAllGetTotal(cri);
		if(!noticeList.isEmpty()) {
			model.addAttribute("noticeList",noticeList);
			model.addAttribute("row", row);
			model.addAttribute("noticeCount", noticeCount);
			model.addAttribute("page", page);
			pagination3 = new Pagination(page, noticeCount, "/search/", row);
			model.addAttribute("pagination3", pagination3);
		}else {
			model.addAttribute("listCheck","empty"); 
			return "/search"; }
		count=productCount+boardCount+noticeCount;
		return "/search";
	}
}
