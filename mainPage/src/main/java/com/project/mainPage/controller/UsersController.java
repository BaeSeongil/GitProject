package com.project.mainPage.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.mainPage.dto.IdCheck;
import com.project.mainPage.dto.Pagination;
import com.project.mainPage.dto.UsersDto;
import com.project.mainPage.mapper.UsersMapper;


@Controller
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UsersMapper usersMapper;
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, Model model) {
		int row = 8;
		int startRow = (page - 1)*row;
		List<UsersDto> userList = usersMapper.selectPageAll(startRow,row);
		int count = usersMapper.selectPageAllCount();
		
		Pagination pagination = new Pagination(page, count, "/users/list/", row);
		System.out.println(pagination);
		model.addAttribute("pagination",pagination);
		model.addAttribute("userList",userList);
		model.addAttribute("row",row);
		model.addAttribute("count",count);
		model.addAttribute("page",page);	
		return "/users/list";
	}
	
	@GetMapping("/login.do")
		public void login() {};
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
				return "redirect:/";
			}else {
				return "redirect:/users/login.do";				
			}
	}
	
	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		session.removeAttribute("loginUsers");
		System.out.println("로그아웃 성공");
		return "redirect:/";
	}
	@GetMapping("/signup.do")
	public void signup() {}
	@PostMapping("/signup.do")
	public String signup(UsersDto user) {
		int insert=0;
		System.out.println(user);
		insert=usersMapper.insertOne(user);
		if(insert>0) {
			return "redirect:/users/list/1";
		}else {
			return "redirect:/users/signup.do";
		}
	}
	@GetMapping("/detail/{userId}")
	public String detail(@PathVariable String userId, Model model) {
		UsersDto user = usersMapper.selectOne(userId);
		model.addAttribute(user);
		System.out.println("user : "+user);
		return "users/detail";
	} 
	@PostMapping("/update.do")
	public String update(UsersDto user) {
		int update=0;
		try {
			update = usersMapper.updateOne(user);			
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(update>0) {
			System.out.println("수정성공 : "+ user);
			return "redirect:/users/list/1";
		}else {
			return "redirect:/users/detail/"+user.getUserid();
		}
	}
	@GetMapping("/idCheck/{userId}")
	public @ResponseBody IdCheck idCheck(@PathVariable String userId) {
		IdCheck idCheck = new IdCheck();
		UsersDto user=usersMapper.selectOne(userId);
		if(user!=null) {
			idCheck.idCheck=true;
			idCheck.user=user;
		}
		return idCheck;
	}
	@GetMapping("/delete/{userId}")
	public String delete(@PathVariable String userId) {
		int delete=0;
		delete=usersMapper.deleteOne(userId);
		if(delete>0) {
			return "redirect:/users/list/1";
		}else {
			return "redirect:/users/detail/"+userId;
		}
	} 	
	
	
}
