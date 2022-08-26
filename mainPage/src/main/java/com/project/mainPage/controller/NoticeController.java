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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.Notice;
import com.project.mainPage.dto.NoticeImg;
import com.project.mainPage.dto.Pagination;
import com.project.mainPage.dto.Product;
import com.project.mainPage.dto.UsersDto;
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
		int row = 10;
		int startRow = (page - 1) * row;
		List<Notice> noticeList = noticeMapper.selectPageAll(startRow, row);
		int count = noticeMapper.selectPageAllCount();
		
		Pagination pagination = new Pagination(page, count, "/notice/list/", row);
		System.out.println(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("row", row);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
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
	
	// 공지사항 삭제 
	@GetMapping("/delete/{noticeNo}/{userId}")
	public String delete(
			@PathVariable int noticeNo,
			@PathVariable String userId,
			@SessionAttribute(name="loginUsers",required = false) UsersDto loginUsers
			) {
		// loginUsers null이 아니고 loginUsers == userId 때 삭제 가능 
			if(loginUsers != null && loginUsers.getUserid().equals(userId)) {
				int delete = 0 ;
				try {
					delete = noticeService.removeNotice(noticeNo);
				}catch (Exception e) {e.printStackTrace();}
				if(delete>0) {
					System.out.println("삭제성공"+noticeNo);
					return "redirect:/notice/list/1";
				}else {
					return "redirect:/notice/detail/"+noticeNo;
				}
			}else {
				return "redirect:/users/login.do";				
			}
		
	}
	@GetMapping("/search/{page}")
	public String searchProduct(
			@RequestParam(value = "type") String type,
			@RequestParam(value = "keyword") String keyword,
			@PathVariable int page, Criteria cri, Model model) {
		int row = 10;
		int startRow = (page - 1) * row;
		cri.setAmount(row);
		cri.setSkip(startRow);
		List<Notice> list=noticeMapper.searchNotice(cri);
		int count = noticeMapper.noticeGetTotal(cri);
		  if(!list.isEmpty()) { model.addAttribute("list",list);
		  }else { model.addAttribute("listCheck","empty"); return "/notice/search"; }
		Pagination pagination = new Pagination(page, count, "/notice/search/", row);
		model.addAttribute("pagination", pagination);
		System.out.println(pagination);
		model.addAttribute("list", list);
		System.out.println("list : "+list);
		model.addAttribute("row", row);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		return "/notice/search";
	}
	
	
}
