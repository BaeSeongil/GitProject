package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mainPage.dto.Notice;
import com.project.mainPage.mapper.NoticeMapper;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private NoticeMapper noticeMapper;
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model) {
		List<Notice> noticeList = noticeMapper.selectPageAll();
		System.out.println("notices : "+noticeList);
		model.addAttribute(noticeList);
		return "/notice/list";
	}
	
	
	
	
	
	
	
}
