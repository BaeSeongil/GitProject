package com.project.mainPage.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.mainPage.common.Constants;
import com.project.mainPage.common.config.interceptor.AdminLoginInterceptor;
import com.project.mainPage.common.config.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;
    @Autowired
    private LoginInterceptor LoginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        // /admin 접두사가 붙은 url 경로에 인터셉터 추가
        // 백그라운드 로그인 인터셉터 (AdminLoginInterceptor)
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/dist/**")
                .excludePathPatterns("/admin/plugins/**");

        // 쇼핑몰 로그인 인터셉터
        registry.addInterceptor(LoginInterceptor)
                .excludePathPatterns("/admin/**")
                .excludePathPatterns("/mall/register")
                .excludePathPatterns("/mall/login")
                .excludePathPatterns("/mall/logout")
                .addPathPatterns("/mall/product/detail/**")
                .addPathPatterns("/mall/cart")
                .addPathPatterns("/mall/cart/**")
                .addPathPatterns("/mall/saveOrder")
                .addPathPatterns("/mall/orders")
                .addPathPatterns("/mall/orders/**")            
                .addPathPatterns("/mall/personal")
                .addPathPatterns("/mall/personal/updateInfo")
                .addPathPatterns("/mall/selectPayType")
                .addPathPatterns("/mall/payPage");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
        registry.addResourceHandler("/product-img/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
    }
}
