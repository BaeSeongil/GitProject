package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class tmp_User {
    private Long userId;    // 사용자 인덱스
    private String nickName;    // 닉네임
    private String loginName;   // ID
    private String password; // Password
    private String introduceSign;   // 상태 메시지
    private String address; // 주소
    private Byte isDeleted; // ID 필드 삭제 (0: 삭제되지 않음 1: 삭제됨)
    private Byte lockedFlag;    // 사용자 차단(0: 차단 해제 1: 차단)
    // 생성일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}