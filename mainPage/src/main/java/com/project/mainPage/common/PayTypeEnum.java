package com.project.mainPage.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 주문 상태
// 0. 없음
//
@Getter
@AllArgsConstructor
public enum PayTypeEnum {

    DEFAULT(-1, "ERROR"),
    NOT_PAY(0, "없음");

    private int payType;
    private String name;

    public static PayTypeEnum getPayTypeEnumByType(int payType) {
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (payTypeEnum.getPayType() == payType) {
                return payTypeEnum;
            }
        }
        return DEFAULT;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public void setName(String name) {
        this.name = name;
    }
}
