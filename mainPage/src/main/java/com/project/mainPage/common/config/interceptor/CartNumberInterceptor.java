package com.project.mainPage.common.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.project.mainPage.common.Constants;
import com.project.mainPage.controller.vo.UserVO;
import com.project.mainPage.mapper.tmp_CartItemMapper;

@Component
public class CartNumberInterceptor implements HandlerInterceptor {

    @Autowired
    private tmp_CartItemMapper CartItemMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // 장바구니의 수량 변경을 인터페이스에서 처리
        if (null != request.getSession() && null != request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY)) {
            // 로그인 되어있으면
            // DB를 조회
            UserVO UserVO = (UserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
            // shopCartItemCount 업데이트 
            UserVO.setShopCartItemCount(CartItemMapper.selectCountByUserId(UserVO.getUserId()));
            request.getSession().setAttribute(Constants.MALL_USER_SESSION_KEY, UserVO);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
