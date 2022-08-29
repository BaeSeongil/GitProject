package com.project.mainPage.controller.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

// 주문 내역 페이지 주문 항목 VO
@Getter @Setter
public class OrderItemVO implements Serializable {
    private Long productId;
    private Integer productCount;
    private String productName;
    private String productCoverImg;
    private Integer sellingPrice;
}
