package com.project.mainPage.controller.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

// 홈페이지 카테고리 데이터 VO (소분류)
@Getter @Setter
public class ThirdLevelCategoryVO implements Serializable {
    private Long categoryId;
    private Byte categoryLevel;
    private String categoryName;
}
