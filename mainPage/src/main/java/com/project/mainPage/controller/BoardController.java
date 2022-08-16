package com.project.mainPage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.mainPage.dto.Board;
import com.project.mainPage.dto.BoardPrefer;
import com.project.mainPage.dto.Reply;
import com.project.mainPage.dto.ReplyPrefer;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.mapper.BoardMapper;
import com.project.mainPage.mapper.BoardPreferMapper;
import com.project.mainPage.service.BoardService;


@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardPreferMapper boardPreferMapper;
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model) {
		List<Board> boardList = boardMapper.selectPageAll();
		model.addAttribute(boardList);
		return "/board/list";
	}
	
	@GetMapping("/detail/{boardNo}")
	public String detail(
			@PathVariable int boardNo,
			Model model,
			@SessionAttribute(required = false) UsersDto loginUsers) {
		Board board = null;		
		BoardPrefer boardPrefer = null;  // 로그인이 안되면 null
		try {
			board = boardService.boardUpdateView(boardNo);
			if(loginUsers != null) {
				boardPrefer = boardPreferMapper.selectFindUserIdAndBoardNo(loginUsers.getUserid(),boardNo);
				for(Reply reply : board.getReplys()) {
					for(ReplyPrefer replyPrefer : reply.getGood_prefers()) {
						if(replyPrefer.getUserid().equals(loginUsers.getUserid())){
							reply.setPrefer_active(true);
						}
					}
					for(ReplyPrefer replyPrefer : reply.getGood_prefers()) {
						if(replyPrefer.getUserid().equals(loginUsers.getUserid())){
							reply.setPrefer_active(false);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(board);
		if(board != null) {
			model.addAttribute("boardPrefer",boardPrefer);
			model.addAttribute("board",board);
			return "/board/detail";			
		}else {
			return "redirect:/board/list/1";
		}
		
	}
}
	
	
	
	

