//package com.project.mainPage.mapper;
//
//public interface PasswordEncoder {
//
//	// 비밀번호 단방향 암호화
//		String encode(CharSequence rawPassword);
//	    
//	        // 암호화되지 않은 비밀번호(raw)와 암호화된 비밀번호(encode)가 일치하는지 비교
//		boolean matches(CharSequence rawPassword, String encodedPassword);
//
//		// 기본적으로 false를 return, Custom하게 구현할 경우 이를 기반으로 더 강력한 암호화 구현
//		default boolean upgradeEncoding(String encodedPassword) {
//			return false;
//		}
//	
//}
