package com.project.mainPage.controller.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserVO implements Serializable {
    private Long userId;
    private String nickName;
    private String loginName;
    private String introduceSign;
    private String address;
    private int shopCartItemCount;
}
