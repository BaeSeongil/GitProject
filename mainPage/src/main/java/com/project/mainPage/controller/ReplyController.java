package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.beans.factory.annotation.Value;

import com.project.mainPage.dto.Pagination;
import com.project.mainPage.dto.Reply;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.mapper.ReplyMapper;
import com.project.mainPage.mapper.ReplyPreferMapper;


@Controller
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	ReplyMapper replyMapper;
	
	@Value("${spring.servlet.multipart.location}")
	String savePath;
	
	@Autowired
	private ReplyPreferMapper replyPreferMapper;
	
	@RequestMapping("/list/{boardNo}/{page}")
	public String list(
			@PathVariable int boardNo,
			@PathVariable int page,
			@SessionAttribute(required = false) UsersDto loginUsers,
			Model model) {
		int row=5;
		int startRow=(page-1)*row;
		String url="/reply/list/"+boardNo;
		List<Reply> replys=null;
		String loginUserId=(loginUsers!=null)?loginUsers.getUserid() : null;
		try {
			int rowCount=replyMapper.selectBoardNoCount(boardNo);
			Pagination pagination=new Pagination(page, rowCount, url, row);
			replys=replyMapper.selectBoardNoPage(boardNo, startRow, row, loginUserId);
			model.addAttribute("pagination", pagination);
			System.out.println(pagination);
			model.addAttribute("replys", replys);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return "/board/replyList";
	}
}
