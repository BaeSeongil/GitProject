package com.project.mainPage.service;

import javax.servlet.http.HttpSession;

import com.project.mainPage.dto.tmp_AdminUser;

public interface tmp_AdminUserService {

    String login(String userName, String password, HttpSession httpSession);

    // 사용자 정보 가져오기
    tmp_AdminUser getUserDetailById(Integer loginUserId);

    // 현재 로그인한 사용자의 비밀번호 수정
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    // 현재 로그인한 사용자의 이름 정보 수정
    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);

}
