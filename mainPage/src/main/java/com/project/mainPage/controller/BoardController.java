package com.project.mainPage.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.acon.board.dto.BoardImg;
import com.project.mainPage.dto.Board;
import com.project.mainPage.mapper.BoardMapper;


@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardMapper boardMapper;
	
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model) {
		List<Board> boardList = boardMapper.selectPageAll();
		model.addAttribute(boardList);
		return "/board/list";
	}
	
	@GetMapping("/insert.do")
	public String insert(HttpSession session) {
		//로그인 한사람만 등록 페이지 가능
		if(session.getAttribute("loginUser")!=null) {
			return "/board/insert";
		}else {
			return "redirect:/";
		}
	}
	@PostMapping(path = "/insert.do",consumes = "multipart/form-data")
	public String insert(Board  board
			,List <MultipartFile> imgFiles
			) {
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
					String type=imgFile.getContentType();//"image/jpeg"
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
			insert=boardService.registBoard(board);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(insert>0) {
			return "redirect:/board/list/1";
		}else {
			return "redirect:/board/insert.do";
		}
		
	}
}
