//package com.project.mainPage.service;
//
//import java.nio.file.attribute.UserPrincipalNotFoundException;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.project.mainPage.dto.UsersDto;
//import com.project.mainPage.mapper.UsersMapper;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//	
//	@Autowired
//	private UsersMapper usersMapper;
//	
//	@Override
//	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//		UsersDto user = usersMapper.selectOne(userId);
////				.orElseThrow(()->new UsernameNotFoundException());
//		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//		
//		return new org.springframework.security.core.userdetails
//				.User(user.getUserid(), user.getUserpw(), grantedAuthorities);
//	}
//
//}
