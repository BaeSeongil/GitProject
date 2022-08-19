package com.project.mainPage.controller.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

// 홈페이지 상품 VO
@Getter @Setter
public class IndexConfigProductVO implements Serializable {
    private Long productId;
    private String productName;
    private String productIntro;
    private String productCoverImg;
    private Integer sellingPrice;
    private String tag;
}
