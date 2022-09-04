package com.project.mainPage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public class SessionController {
	public String DeleteSessionMsg (HttpSession session, HttpServletRequest req){
		session.setAttribute("msg", null);
		if(req.getHeader("Referer")!=null) {
			return "redirect:"+req.getHeader("Referer");
		}else {
			return "/";
		}
	}
}
