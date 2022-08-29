package com.project.mainPage.controller.vo;

import com.project.mainPage.dto.tmp_Category;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

// 검색 페이지 카테고리 VO
@Getter @Setter
public class SearchPageCategoryVO implements Serializable {
    private String firstLevelCategoryName;
    private List<tmp_Category> secondLevelCategoryList;
    private String secondLevelCategoryName;
    private List<tmp_Category> thirdLevelCategoryList;
    private String currentCategoryName;
}
