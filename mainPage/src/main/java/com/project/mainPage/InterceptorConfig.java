package com.project.mainPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.mainPage.interceptor.AdminInterceptor;
import com.project.mainPage.interceptor.LoginCheckInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	@Autowired
	LoginCheckInterceptor loginCheckInterceptor;
	@Autowired
	AdminInterceptor adminInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor)
				.addPathPatterns("/users/**")
				.addPathPatterns("/**/insert/**")
				.addPathPatterns("/**/delete/**")
				.addPathPatterns("/**/update/**")
				.excludePathPatterns("/users/login.do")
				.excludePathPatterns("/users/signup.do")
				.excludePathPatterns("/users/privacy")
				.excludePathPatterns("/users/emailRejection")
				.excludePathPatterns("/users/agreement");
//				추가하거나 예외처리할 주소
//				.addPathPatterns("/ /**")		
//				.excludePathPatterns("/ / ")
//				.excludePathPatterns("/ / ");
		registry.addInterceptor(adminInterceptor)
				.addPathPatterns("/admin/**")
				.addPathPatterns("/users/list/**")
				.addPathPatterns("/notice/insert.do")
				.addPathPatterns("/notice/delete.do")
				.addPathPatterns("/product/insert.do")
				.addPathPatterns("/product/update.do")
				.addPathPatterns("/product/delete.do");
	}
	
}
