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
				.excludePathPatterns("/users/login.do")
				.excludePathPatterns("/users/signup.do");
//				추가하거나 예외처리할 주소
//				.addPathPatterns("/ /**")		
//				.excludePathPatterns("/ / ")
//				.excludePathPatterns("/ / ");
		registry.addInterceptor(adminInterceptor)
				.addPathPatterns("/admin/**");
	}
	
}
