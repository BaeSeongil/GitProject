package com.project.mainPage.dto;

import lombok.Data;

// 재고 관리 객체
@Data
public class tmp_StockNum {
    private Long productId; // 상품 인덱스
    private Integer producCount;    // 상품 개수
}
