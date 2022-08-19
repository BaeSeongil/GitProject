package com.project.mainPage.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.SessionAttribute;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.project.mainPage.dto.BoardImg;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.service.BoardService;
import com.project.mainPage.dto.Board;
import com.project.mainPage.dto.BoardPrefer;
import com.project.mainPage.dto.Reply;
import com.project.mainPage.dto.ReplyPrefer;
import com.project.mainPage.mapper.BoardMapper;

import com.project.mainPage.mapper.BoardPreferMapper;
import com.project.mainPage.mapper.UsersMapper;


@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardPreferMapper boardPreferMapper;

	private UsersMapper usersMapper;
	
	@Value("${spring.servlet.multipart.location}") //파일이 임시저장되는 경로+파일을 저장할 경로
	private String savePath;
	

	
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
			System.out.println(board);
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
		if(board != null) {
			model.addAttribute("boardPrefer",boardPrefer);
			model.addAttribute("board",board);
			System.out.println();
			return "/board/detail";			
		}else {
			return "redirect:/board/list/1";
		}
	}
	@GetMapping("/insert.do")
	public String insert(HttpSession session) {
		//로그인 한사람만 등록 페이지 가능
		if(session.getAttribute("loginUsers")!=null) {
			return "/board/insert";
		}else {
			return "redirect:/users/login.do";
		}
	}
		
	@PostMapping("/insert.do")
	public String insert(
				Board  board,
				List <MultipartFile> imgFiles) {
//		System.out.println(board);
		System.out.println(savePath);
		int insert=0;
		try {
			//이미지 저장 및 처리
			if(imgFiles!=null) {
				List<BoardImg> boardImgs=new ArrayList<BoardImg>();
				// imgFiles 가 null이면 여기서 오류 발생!! 
				for(MultipartFile imgFile:imgFiles) {		
					String type=imgFile.getContentType(); 
					if(type.split("/")[0].equals("image")) {
						String newFileName="board_"+System.nanoTime()+"."+type.split("/")[1]; //"image/jpeg"
						Path newFilePath=Paths.get(savePath+"/"+newFileName);
						imgFile.transferTo(newFilePath);
						BoardImg boardImg=new BoardImg();
						boardImg.setImg_path(newFileName);
						boardImgs.add(boardImg);
					}
				}
				if(boardImgs.size()>0) {
					board.setBoardImgs(boardImgs);
				}
			}
			insert=boardService.registBoard(board);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(insert>0) {
			return "redirect:/board/list/1";
		}else {
			return "redirect:/board/insert.do";
		}
		
	}
}
	
	
	
	

