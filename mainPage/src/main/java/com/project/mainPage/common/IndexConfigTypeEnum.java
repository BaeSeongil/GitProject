package com.project.mainPage.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 메인 페이지 구성 항목
// 1. 검색바
// 2. 검색 드롭다운 
// 3. 히트상품
// 4. 신상품
// 5. 추천상품
@Getter
@AllArgsConstructor
public enum IndexConfigTypeEnum {

    DEFAULT(0, "DEFAULT"),
    INDEX_SEARCH_HOTS(1, "INDEX_SEARCH_HOTS"),
    INDEX_SEARCH_DOWN_HOTS(2, "INDEX_SEARCH_DOWN_HOTS"),
    INDEX_PRODUCT_HOT(3, "INDEX_PRODUCT_HOTS"),
    INDEX_PRODUCT_NEW(4, "INDEX_PRODUCT_NEW"),
    INDEX_PRODUCT_RECOMMOND(5, "INDEX_PRODUCT_RECOMMOND");

    private int type;
    private String name;

    public static IndexConfigTypeEnum getIndexConfigTypeEnumByType(int type) {
        for (IndexConfigTypeEnum indexConfigTypeEnum : IndexConfigTypeEnum.values()) {
            if (indexConfigTypeEnum.getType() == type) {
                return indexConfigTypeEnum;
            }
        }
        return DEFAULT;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }
}
