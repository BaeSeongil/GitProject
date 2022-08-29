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
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.Pagination;
import com.project.mainPage.dto.Product;
import com.project.mainPage.dto.QaBoard;
import com.project.mainPage.dto.QaReply;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.mapper.ProductMapper;
import com.project.mainPage.mapper.QaBoardMapper;
import com.project.mainPage.mapper.QaReplyMapper;

@Controller
@RequestMapping("/qaboard")
public class QaBoardController {
	@Autowired
	private QaBoardMapper qaBoardMapper;
	@Autowired
	private QaReplyMapper qaReplyMapper;
	@Autowired
	private ProductMapper productMapper;
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model) {
		int row =10;
		int startRow = (page-1)*row;
		List<QaBoard> list = qaBoardMapper.selectPageAll(startRow, row);
		int count = qaBoardMapper.selectPageAllCount();
		
		Pagination pagination = new Pagination(page, count, "/qaboard/list/", row);
		System.out.println(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", list);
		model.addAttribute("row", row);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		return "/qaboard/list";
	}
	@GetMapping("/detail/{qaBoardno}")
	public String detail(@PathVariable int qaBoardno, Model model,
			@SessionAttribute(required = false) UsersDto loginUsers) {
		QaBoard qaBoard = qaBoardMapper.selectOne(qaBoardno);
		System.out.println("qaBoard"+qaBoard);
		model.addAttribute("qaBoard",qaBoard);
		return "/qaboard/detail";
	}
	@GetMapping("/insert.do")
	public String insert(@PathVariable int qaBoardno,Model model,
			@SessionAttribute(required = false) UsersDto loginUsers) {
		List<Product> qList=productMapper.selectAllProduct();
		System.out.println(qList);
		model.addAttribute("qList", qList);
		return "/qaboard/insert";
	}
	@PostMapping("/insert.do")
	public String insert(QaBoard qaBoard) {
		int insert=0;
		System.out.println(qaBoard);
		try {
			insert=qaBoardMapper.insertOne(qaBoard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(insert>0) {
			return "redirect:/qaboard/list/1";
		}else {
			return "redirect:/qaboard/insert.do";
		}
	}
	@GetMapping("/delete/{qaBoardNo}")
	public String delete(@PathVariable int qaBoardNo) {
		int delete=0;
		delete=qaBoardMapper.deleteOne(qaBoardNo);
		if(delete>0) {
			return "redirect:/qaboard/list/1";
		}else {
			return "redirect:/qaboard/detail/"+qaBoardNo;
		}
	} 
	@GetMapping("/answer.do")
	public void replyInsert() {}
	@PostMapping("/answer.do")
	public String replyInsert(QaReply qaReply,QaBoard qaBoard ) {
		int insert=0;
		int answer=0;
		int page=qaBoard.getQaBoardNo();
		System.out.println(page);
		
		try {
			answer=qaReplyMapper.insertOne(qaReply);
			insert=qaBoardMapper.answerOne(qaBoard);
			System.out.println(insert);
			System.out.println(answer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(answer>0) {
			return "redirect:/qaboard/list/1";
		}else {
			return "redirect:/qaboard/detail/"+page;
		}
	}
	@GetMapping("/search/{page}")
	public String searchProduct(
			@RequestParam(value = "type") String type,
			@RequestParam(value = "keyword") String keyword,
			@PathVariable int page, Criteria cri, Model model) {
		int row = 10;
		int startRow = (page - 1) * row;
		cri.setAmount(row);
		cri.setSkip(startRow);
		List<QaBoard> list= qaBoardMapper.searchQaBoard(cri);
		int count = qaBoardMapper.QaBoardGetTotal(cri);
		  if(!list.isEmpty()) { model.addAttribute("list",list);
		  }else { model.addAttribute("listCheck","empty"); return "/qaboard/search"; }
		Pagination pagination = new Pagination(page, count, "/qaboard/search/", row);
		System.out.println(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", list);
		System.out.println("list : "+list);
		model.addAttribute("row", row);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		return "/qaboard/search";
	}
}
