package com.project.mainPage.controller.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

// 장바구니 페이지 쇼핑 항목 VO
@Getter @Setter
public class CartItemVO implements Serializable {
    private Long cartItemId;
    private Long productId;
    private Integer productCount;
    private String productName;
    private String productCoverImg;
    private Integer sellingPrice;
}
