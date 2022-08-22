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
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	//회원리스트
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
				return "redirect:/";
			}else {
				return "redirect:/users/login.do";				
			}
	}
	
	//회원로그아웃
	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		session.removeAttribute("loginUsers");
		System.out.println("로그아웃 성공");
		return "redirect:/";
	}
	
	//회원가입
	@GetMapping("/signup.do")
	public void signup() {}
	@PostMapping("/signup.do")
	public String signup(UsersDto user) {
//		String rawPw = "";            // 인코딩 전 비밀번호
//        String encodePw = "";        // 인코딩 후 비밀번호
		int insert=0;
		System.out.println(user);
		try {
//			rawPw = user.getUserpw();            // 비밀번호 데이터 얻음
//		    encodePw = passwordEncoder.encode(rawPw);        // 비밀번호 인코딩
//		    user.setUserpw(encodePw);               // 인코딩된 비밀번호 user객체에 다시 저장
			insert=usersMapper.insertOne(user); // 회원가입 쿼리실행
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(insert>0) {
			return "redirect:/users/list/1";
		}else {
			return "redirect:/users/signup.do";
		}
	}
	//회원상세페이지
	@GetMapping("/detail/{userId}")
	public String detail(@PathVariable String userId, Model model) {
		UsersDto user = usersMapper.selectOne(userId);
		model.addAttribute(user);
		System.out.println("user : "+user);
		return "users/detail";
	} 
	//회원정보수정
	@PostMapping("/update.do")
	public String update(UsersDto user) {
		int update=0;
		try {
			update=usersMapper.updateOne(user);
			System.out.println(user);
			System.out.println(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(update>0) {
			System.out.println("수정성공 : "+ user);
			return "redirect:/users/list/1";
		}else {
			return "redirect:/users/detail/"+user.getUserid();
		}
	}
	//회원가입중복체크(id)
	@GetMapping("/idCheck/{userId}")
	//ResponseBody가 들어가야 ajax가 작동한다
	@ResponseBody public IdCheck idCheck(@PathVariable String userId) {
		IdCheck idCheck = new IdCheck();
		UsersDto user=usersMapper.selectOne(userId);
		if(user!=null) { //중복된 아이디가 있다
			idCheck.idCheck=true;
			idCheck.user=user;
		}
		return idCheck;
	}
	//회원삭제
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
	
	
	/*
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
	 */
	
//	@GetMapping("/cart/insert.do")
//	public void insert() {}
//	
//	@PostMapping("/cart/insert.do")
//	public String insert(HttpSession session) {
//		
//	}
	
	//푸터 연결용
	@GetMapping("/agreement")
	public void agreement() {};
	@GetMapping("/privacy")
	public void privacy() {};
	@GetMapping("/emailRejection")
	public void emailRejection() {};
	
}
