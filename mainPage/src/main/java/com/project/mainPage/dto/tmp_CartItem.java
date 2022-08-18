package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class tmp_CartItem {
    private Long cartItemId;    // 장바구니 인덱스
    private Long userId;    // 유저 인덱스
    private Long productId;   // 상품 인덱스
    private Integer productCount; // 구매한 상품 개수
    private Byte isDeleted; // ID 필드 삭제 (0: 삭제되지 않음 1: 삭제됨)
    // 생성일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    // 갱신일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}