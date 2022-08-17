package com.project.mainPage;
//package com.project.mainPage.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.project.mainPage.service.UserDetailsServiceImpl;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//	@Bean
//	  public UserDetailsService userDetailsService() {
//	    return new UserDetailsServiceImpl();
//	  }
//	@Bean
//	  public BCryptPasswordEncoder passwordEncoder() {
//	    return new BCryptPasswordEncoder();
//	  }
//	/**
//   * 인증 or 인가에 대한 설정
//   */
//	@Bean
//	  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	    http
//	    	.cors().disable()		//cors방지
//	        .csrf().disable() // csrf 비활성화 post 방식으로 값을 전송할 때 token을 사용해야하는 보안 설정을 해제
//	        .formLogin().disable() //기본 로그인 페이지
//	    	.headers().frameOptions().disable();
////	        .loginPage("/users/login.do") //인증X 사용자의 인증필요시 이동페이지
////	        .loginProcessingUrl("/users/login.do") //로그인 요청 시 
////	        .usernameParameter("userId")
////	        .passwordParameter("userPw")
////	        .defaultSuccessUrl("/") //성공시 리다이렉트페이지
////	        .failureUrl("/users/login.do"); //실패시 이동페이지
////	    http
////	        .authorizeRequests()
////	    	.anyRequest().authenticated()
////	    	.and()
////	    	.formLogin()
////	    	.defaultSuccessUrl("/")
////	    	.permitAll()
////	    	.and()
////	    	.logout()
////	    	.logoutUrl("users/logout")
////	    	.permitAll();
////	        
//	    return http.build();
//	  }
//	
//}
