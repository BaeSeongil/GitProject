package com.project.mainPage.controller.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

// 홈페이지 카테고리 데이터 VO
@Getter @Setter
public class CategoryVO implements Serializable {
    private Long categoryId;
    private Byte categoryLevel;
    private String categoryName;
    private List<SecondLevelCategoryVO> secondLevelCategoryVOS;
}
