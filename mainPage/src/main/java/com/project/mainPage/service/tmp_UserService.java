package com.project.mainPage.service;

import com.project.mainPage.controller.vo.UserVO;
import com.project.mainPage.dto.tmp_User;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.PageResult;

import javax.servlet.http.HttpSession;

public interface tmp_UserService {
    // 백엔드
    // 사용자 정보 페이지 형식으로 가져오기
    PageResult getUsersPage(PageQueryUtil pageUtil);
    
    // 사용자 등록
    String register(String loginName, String password);
    // 로그인
    String login(String loginName, String passwordMD5, HttpSession httpSession);
    // 현재 로그인한 사용자의 비밀번호 수정
    Boolean updatePassword(String originalPassword, String newPassword, HttpSession httpSession);
    // 사용자 정보 수정 및 최신 사용자 정보 반환
    UserVO updateUserInfo(tmp_User User, HttpSession httpSession);
    // 사용자 계정 차단 활성화 및 비활성화
    // 0: 차단 해제
    // 1: 차단
    Boolean lockUsers(Integer[] ids, int lockStatus);
}
