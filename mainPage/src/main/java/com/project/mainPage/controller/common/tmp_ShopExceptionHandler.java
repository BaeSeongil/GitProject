package com.project.mainPage.controller.common;

import com.project.mainPage.common.ShopException;
import com.project.mainPage.util.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

// 전역 예외 처리
@RestControllerAdvice
public class tmp_ShopExceptionHandler {

    @ExceptionHandler(ShopException.class)
    public Object handleException(ShopException e, HttpServletRequest req) {
        Result result = new Result();
        result.setResultCode(500);
        // 사용자 정의 예외인지 구분
        if (e instanceof ShopException) {
            result.setMessage(e.getMessage());
        } else {
            e.printStackTrace();
            result.setMessage("알 수 없는 예외");
        }
        // 요청이 ajax인지 확인하고, ajax 요청이면
        // Result json 스트링을 반환하며
        // ajax 요청이 아니면 error 페이지를 반환
        String contentTypeHeader = req.getHeader("Content-Type");
        String acceptHeader = req.getHeader("Accept");
        String xRequestedWith = req.getHeader("X-Requested-With");
        if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
            return result;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("url", req.getRequestURL());
            modelAndView.addObject("stackTrace", e.getStackTrace());
            modelAndView.setViewName("error/error");
            return modelAndView;
        }
    }
}
