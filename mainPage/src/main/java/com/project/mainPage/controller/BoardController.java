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
	@GetMapping("/insert.do")
	public String insert(HttpSession session) {
		//로그인 한사람만 등록 페이지 가능
		if(session.getAttribute("loginUsers")!=null) {
			return "/board/insert";
		}else {
			return "redirect:/board/login.do";
		}
	}
	
	//회원로그인페이지
	@GetMapping("/login.do")
		public void login() {};
	//회원로그인
	@PostMapping("/login.do")
		public String login(
				@RequestParam(value="userid") String userId, 
				@RequestParam(value="userpw") String userPw,
				HttpSession session) {
			UsersDto users = null;
			try {
				users = usersMapper.selectIdPwOne(userId, userPw);
			}catch(Exception e) {e.printStackTrace();}
			
			if(users != null) {
				session.setAttribute("loginUsers", users);
				System.out.println("로그인 성공! " + users);
				System.out.println(session.getAttribute("loginUsers"));
				session.setAttribute("users", users);
				return "redirect:/board/insert.do";
			}else {
				return "redirect:/board/login.do";				
			}
	}
		
	@PostMapping(path = "/insert.do",consumes = "multipart/form-data")
	public String insert(
				@RequestParam(value="userid") String userId, 
				Board  board,
				List <MultipartFile> imgFiles) {
		//model2(톰캣) 전송된 파일 저장하는 방법
		//1.cos.jar 추가 : 톰캣에서 blob으로 넘어온 data를 multipartRequest로 받을 수 있다.(파일 저장)
		//2.form 에 enctype="multipart/form-data" 추가 : 모든 파라미터를 blob으로 전송 
		//3.서버에서 multipartRequest 객체로 파일과 문자열 파라미터를 구분해서 처리
		System.out.println(board);
		System.out.println(savePath);
		//MultipartFile 받아온 파일은 임시로 저장된 파일
		//transferTo 임시로 저장된 파일을 실제로 저장하는 함수
		//Paths.get("경로+/+파일이름) : 임시 파일을 실제로 저장할 경로를 지정
		int insert=0;
		try {
			//이미지 저장 및 처리
			if(imgFiles!=null) {
				List<BoardImg> boardImgs=new ArrayList<BoardImg>();
				for(MultipartFile imgFile:imgFiles) {		
					String type=imgFile.getContentType(); //"image/jpeg"
					if(type.split("/")[0].equals("image")) {
						String newFileName="board_"+System.nanoTime()+"."+type.split("/")[1];
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
			board.setUsers(usersMapper.selectOne(userId));
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
	
	
	
	

