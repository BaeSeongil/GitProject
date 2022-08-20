package com.project.mainPage.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 주문 상태
// 0. 미입금
// 1. 결제완료
// -1. 결제실패
@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    DEFAULT(-1, "결제실패"),
    PAY_ING(0, "미입금"),
    PAY_SUCCESS(1, "결제완료");

    private int payStatus;
    private String name;

    public static PayStatusEnum getPayStatusEnumByStatus(int payStatus) {
        for (PayStatusEnum payStatusEnum : PayStatusEnum.values()) {
            if (payStatusEnum.getPayStatus() == payStatus) {
                return payStatusEnum;
            }
        }
        return DEFAULT;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
