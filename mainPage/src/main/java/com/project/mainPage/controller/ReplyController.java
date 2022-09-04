package com.project.mainPage.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import com.project.mainPage.dto.Board;
import com.project.mainPage.dto.BoardImg;
import com.project.mainPage.dto.Pagination;
import com.project.mainPage.dto.Reply;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.mapper.ReplyMapper;
import com.project.mainPage.mapper.ReplyPreferMapper;
import com.project.mainPage.service.ReplyService;


@Controller
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	ReplyMapper replyMapper;
	
	@Value("${spring.servlet.multipart.location}")
	String savePath;
	
	@Autowired
	private ReplyPreferMapper replyPreferMapper;
	
	@Autowired
	private ReplyService replyService;
	
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
			model.addAttribute("replys", replys);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return "/board/replyList";
	}
	
	@PostMapping("/insert.do")
	public String insert(
			Board board, Reply reply,
			MultipartFile imgFile,
			@SessionAttribute(required = true) UsersDto loginUsers,
			HttpSession session,
			Model model
			) {
		String msg = "";
		int insert = 0;
		try {
			//이미지 저장 및 처리
			if(imgFile!=null) {
				reply.setBoard_no(board.getBoard_no());
				String type=imgFile.getContentType();
				if(type.split("/")[0].equals("image")) {
					String newFileName="reply_"+System.nanoTime()+"."+type.split("/")[1]; //"image/jpeg"
					Path newFilePath=Paths.get(savePath+"/"+newFileName);
					imgFile.transferTo(newFilePath);
					reply.setImg_path(newFileName);
				}
			}
			System.out.println(reply);
			insert=replyService.registReply(reply);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(insert>0) {
			msg="댓글 등록 성공";
			session.setAttribute("msg", msg);
			return "redirect:/board/detail/"+board.getBoard_no();
		}else {
			msg="댓글 등록 실패";
			session.setAttribute("msg", msg);
			return "/board/detail/"+board.getBoard_no();
		}
	}
}
