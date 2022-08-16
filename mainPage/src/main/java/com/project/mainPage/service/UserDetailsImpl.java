//package com.project.mainPage.service;
//
//import java.util.Collection;
//import java.util.Collections;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.project.mainPage.dto.UsersDto;
//
//public class UserDetailsImpl implements UserDetails {
//	private UsersDto user;
//	public UserDetailsImpl(UsersDto user) {
//		this.user=user;
//	}
//	@Override
//	public String getPassword() {
//		return user.getUserpw();
//	}
//	@Override
//	public String getUsername() {
//		return user.getUserid();
//	}
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return Collections.emptyList();
//	}
//}
