package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.mapper.UsersMapper;


@Controller
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UsersMapper userMapper;
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page) {
		List<UsersDto> userList = userMapper.selectAll(page);
		System.out.println(userList);
		return "/users/list";
	}
	
	
	
	
	
	 	
	
	
}
