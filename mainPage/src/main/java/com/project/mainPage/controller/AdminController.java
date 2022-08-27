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
			@PathVariable int page, Criteria cri, Model model) {
		int row = 5;
		int startRow = (page - 1) * row;
		cri.setAmount(row);
		cri.setSkip(startRow);
		List<Product> productList=productMapper.searchAllProduct(cri);
		int productCount = productMapper.productsAllGetTotal(cri);
		List<Board> boardList= boardMapper.searchAllBoard(cri);
		int boardCount = boardMapper.boardAllGetTotal(cri);
		List<Notice> noticeList= noticeMapper.searchAllNotice(cri);
		int noticeCount = noticeMapper.noticeAllGetTotal(cri);
		if(!productList.isEmpty()) {
			model.addAttribute("productList",productList);
			model.addAttribute("productCount", productCount);
			model.addAttribute("page", page);
		}if(!boardList.isEmpty()) {
			model.addAttribute("boardList",boardList);
			model.addAttribute("boardCount", boardCount);
			model.addAttribute("page", page);
		}if(!noticeList.isEmpty()) {
			model.addAttribute("noticeList",noticeList);
			model.addAttribute("noticeCount", noticeCount);
			model.addAttribute("page", page);
		}else {
			return "/search"; }
		return "/search";
	}
	@GetMapping("/search/product/{page}")
	public String searchProduct(
			@RequestParam(defaultValue = "search") String keyword,
			@PathVariable int page, Criteria cri, Model model) {
		int row = 10;
		int startRow = (page - 1) * row;
		cri.setAmount(row);
		cri.setSkip(startRow);
		Pagination pagination;
		List<Product> productList=productMapper.searchAllProduct(cri);
		int productCount = productMapper.productsAllGetTotal(cri);
		if(!productList.isEmpty()) {
			model.addAttribute("productList",productList);
			model.addAttribute("row", row);
			model.addAttribute("productCount", productCount);
			model.addAttribute("page", page);
			pagination = new Pagination(page, productCount, "/search/product/", row);
			model.addAttribute("pagination", pagination);
		}else {
			return "/search/product"; }
		return "/search/product";
	}
	@GetMapping("/search/board/{page}")
	public String searchBoard(
			@RequestParam(defaultValue = "search") String keyword,
			@PathVariable int page, Criteria cri, Model model) {
		int row = 10;
		int startRow = (page - 1) * row;
		cri.setAmount(row);
		cri.setSkip(startRow);
		Pagination pagination;
		List<Board> boardList= boardMapper.searchAllBoard(cri);
		int boardCount = boardMapper.boardAllGetTotal(cri);
		if(!boardList.isEmpty()) {
			model.addAttribute("boardList",boardList);
			model.addAttribute("row", row);
			model.addAttribute("boardCount", boardCount);
			model.addAttribute("page", page);
			pagination = new Pagination(page, boardCount, "/search/board/", row);
			model.addAttribute("pagination", pagination);
		}else {
			return "/search/board"; }
		return "/search/board";
	}
	@GetMapping("/search/notice/{page}")
	public String searchNotice(
			@RequestParam(defaultValue = "search") String keyword,
			@PathVariable int page, Criteria cri, Model model) {
		int row = 10;
		int startRow = (page - 1) * row;
		cri.setAmount(row);
		cri.setSkip(startRow);
		Pagination pagination;
		List<Notice> noticeList= noticeMapper.searchAllNotice(cri);
		int noticeCount = noticeMapper.noticeAllGetTotal(cri);
		if(!noticeList.isEmpty()) {
			model.addAttribute("noticeList",noticeList);
			model.addAttribute("row", row);
			model.addAttribute("noticeCount", noticeCount);
			model.addAttribute("page", page);
			pagination = new Pagination(page, noticeCount, "/search/notice/", row);
			model.addAttribute("pagination", pagination);
		}else {
			return "/search/notice"; }
		return "/search/notice";
	}
}