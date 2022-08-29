package com.project.mainPage.common.config.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Admin 인증 및 권한 제어
// 실제 controller(handler)가 실행되기 전에 실행
@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String requestServletPath = request.getServletPath();
        if (requestServletPath.startsWith("/admin") && null == request.getSession().getAttribute("loginUser")) {
            request.getSession().setAttribute("errorMsg", "로그인 되어있지 않음");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        } else {
            request.getSession().removeAttribute("errorMsg");
            return true;
        }
    }

    // handler가 실행된 후에 실행
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }
    
    // 전체 요청이 끝나고 난 후 마지막에 실행
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
