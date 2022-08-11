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
import org.springframework.web.multipart.MultipartFile;

import com.project.mainPage.dto.Notice;
import com.project.mainPage.dto.NoticeImg;
import com.project.mainPage.mapper.NoticeMapper;
import com.project.mainPage.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Value("${spring.servlet.multipart.location}") // 파일이 임시저장되는 경로 + 파일을 저장할 경로 
	private String savePath;
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model) {
		List<Notice> noticeList = noticeMapper.selectPageAll();
		System.out.println("notices : "+noticeList);
		model.addAttribute(noticeList);
		return "/notice/list";
	}
	
	@GetMapping("/detail/{noticeNo}")
	public String detail(@PathVariable int noticeNo, Model model) {
		Notice notice = null; 
		try {
			notice = noticeService.noticeUpdateView(noticeNo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("notice"+notice);
		if(notice != null) {
			model.addAttribute(notice);
			return "/notice/detail";
		}else {
			return "redirect:/notice/list/1";	
		}
	}
	
	// 공지사항 등록 
	@GetMapping("/insert.do")
	public String insert(HttpSession session) {
		if(session.getAttribute("loginUsers") != null) {
			return "/notice/insert";
		}else {
			return "redirect:/users/login.do";
		}
	}
	@PostMapping("/insert.do")
	public String insert(Notice notice, List<MultipartFile>  imgFiles) {
		System.out.println(notice);
		System.out.println(savePath);
		
		int insert = 0; 
		try {
			if(imgFiles != null) {
				List<NoticeImg> noticeImgs = new ArrayList<NoticeImg>();
				// imgFiles가 null 이면 오류 발생! 
				for(MultipartFile imgFile : imgFiles) {
					String type = imgFile.getContentType();
					// image 만 설정 
					if(type.split("/")[0].equals("image")) {
						String newFileName = "notice_"+System.nanoTime()+"."+type.split("/")[1]; 
						Path newFilePath = Paths.get(savePath+"/"+newFileName);
						imgFile.transferTo(newFilePath); // 파일데이터를 지정한 file로 저장
						NoticeImg noticeImg = new NoticeImg();
						noticeImg.setImg_path(newFileName);
						noticeImgs.add(noticeImg);						
					}
				}
				if(noticeImgs.size()>0) {
					notice.setNoticeImgs(noticeImgs);
				}
			}
			insert = noticeService.NoticeAndNoticeImg(notice);
		}catch(Exception e) {e.printStackTrace();}
		if(insert>0) {
			return "redirect:/notice/list/1";			
		}else {
			return "redirect:/notice/insert.do";						
		}
		
	}
	
	
	
}
