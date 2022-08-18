package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class tmp_Product {
    private Long productId; // 상품 인덱스
    private String productName; // 상품명
    private String productIntro;    // 상품 설명
    private Long CategoryId;    // 카테고리 인덱스
    private String productCoverImg; // 상품 대표 이미지
    private String productCarousel; // 상품 carousel 이미지
    private Integer originalPrice;  // 원래 상품 가격
    private Integer sellingPrice;   // 할인된 가격
    private Integer stockNum;   // 재고량
    private String tag; // 상품 라벨
    private Byte productSellStatus; // 상품 진열 상태(0: 판매안함 1: 진열)
    // 생성일, 생성유저
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer createUser;
    // 갱신일, 갱신유저
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private Integer updateUser;
    private String productDetailContent;    // 상품 상세정보(img, text etc... in html format)
}