package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mainPage.dto.Pagination;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.mapper.UsersMapper;


@Controller
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UsersMapper usersMapper;
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model) {
		int row = 8;
		int startRow = (page - 1)*row;
		List<UsersDto> userList = usersMapper.selectPageAll(startRow,row);
		int count = usersMapper.selectPageAllCount();
		
		Pagination pagination = new Pagination(page, count, "/users/list/", row);
		System.out.println(pagination);
		model.addAttribute("pagination",pagination);
		model.addAttribute("userList",userList);
		model.addAttribute("row",row);
		model.addAttribute("count",count);
		model.addAttribute("page",page);	
		return "/users/list";
	}
	
	
	
	
	
	 	
	
	
}
