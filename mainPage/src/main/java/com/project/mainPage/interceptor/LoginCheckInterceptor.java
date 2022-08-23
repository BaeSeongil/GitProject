package com.project.mainPage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("logincheck testing");
		String prevPage = request.getHeader("Referer");
		HttpSession session = request.getSession();
		Object loginCheck_obj=session.getAttribute("loginUsers");
		System.out.println(session);
		System.out.println(loginCheck_obj);
		if(loginCheck_obj!=null) {
			return true;
		}else {
			session.setAttribute("redirectPage", prevPage);
			response.sendRedirect("/users/login.do");
			return false;
		}
		
	}
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//	}
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//	}
}
