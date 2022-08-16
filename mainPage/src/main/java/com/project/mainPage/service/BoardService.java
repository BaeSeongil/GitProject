package com.project.mainPage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.mainPage.dto.Board;
import com.project.mainPage.mapper.BoardMapper;

@Service
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	
	@Value("${spring.servlet.multipart.location}")
	String savePath;
	
	
	public Board boardUpdateView(int boardNo) throws Exception{
		boardMapper.detailUpdateViews(boardNo);
		return boardMapper.selectDetailOneAll(boardNo);
	}
}
