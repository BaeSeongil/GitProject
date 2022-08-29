package com.project.mainPage.dto;

import lombok.Data;

@Data
public class tmp_AdminUser {
    private Integer adminUserId;    // 인덱스
    private String loginUserName;   // ID
    private String loginPassword;   // Password -> MD5로 암호화됨
    private String nickName;    // 닉네임
    private Byte locked;    // 차단 (1이면 차단, 0이면 아님)
}