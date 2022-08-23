package com.project.mainPage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.mainPage.mapper.OrderMapper;

@Service
public class orderService {
	@Value("${spring.servlet.multipart.location}")
	String savePath;
	
	@Autowired
	private OrderMapper orderMapper;
	
	
}
