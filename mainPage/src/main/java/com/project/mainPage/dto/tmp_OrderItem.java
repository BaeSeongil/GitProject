package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class tmp_OrderItem {
    private Long orderItemId;   // 주문 상품 인덱스
    private Long orderId;   // 주문 인덱스
    private Long productId;   // 상품 인덱스
    private String productName; // 상품명
    private String productCoverImg; // 상품 대표 이미지
    private Integer sellingPrice;   // 판매가
    private Integer productCount;   // 구매 개수
    // 생성일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}