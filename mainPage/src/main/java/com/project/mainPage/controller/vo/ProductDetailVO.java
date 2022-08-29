package com.project.mainPage.controller.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

// 상품 상세 페이지 VO
@Getter @Setter
public class ProductDetailVO implements Serializable {
    private Long productId;
    private String productName;
    private String productIntro;
    private String productCoverImg;
    private String[] productCarouselList;
    private Integer sellingPrice;
    private Integer originalPrice;
    private String productDetailContent;
}
