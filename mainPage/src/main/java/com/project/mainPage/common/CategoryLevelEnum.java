package com.project.mainPage.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 카테고리 등급
@Getter
@AllArgsConstructor
public enum CategoryLevelEnum {

    DEFAULT(0, "ERROR"),
    LEVEL_ONE(1, "대분류"),
    LEVEL_TWO(2, "주분류"),
    LEVEL_THREE(3, "소분류");

    private int level;
    private String name;

    public static CategoryLevelEnum getOrderStatusEnumByLevel(int level) {
        for (CategoryLevelEnum CategoryLevelEnum : CategoryLevelEnum.values()) {
            if (CategoryLevelEnum.getLevel() == level) {
                return CategoryLevelEnum;
            }
        }
        return DEFAULT;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }
}
