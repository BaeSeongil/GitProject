package com.project.mainPage.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 주문 상태
// 0. 미입금
// 1. 결제완료
// 2. 포장완료
// 3. 배송완료
// 4. 수령완료
// -1. 수동취소
// -2. 시간초과
// -3. 판매종료
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    DEFAULT(-9, "ERROR"),
    ORDER_PRE_PAY(0, "미입금"),
    ORDER_PAID(1, "결제완료"),
    ORDER_PACKAGED(2, "포장완료"),
    ORDER_EXPRESS(3, "배송완료"),
    ORDER_SUCCESS(4, "수령완료"),
    ORDER_CLOSED_BY_User(-1, "수동취소"),
    ORDER_CLOSED_BY_EXPIRED(-2, "시간초과"),
    ORDER_CLOSED_BY_JUDGE(-3, "판매종료");

    private int orderStatus;
    private String name;

    public static OrderStatusEnum getOrderStatusEnumByStatus(int orderStatus) {
        for (OrderStatusEnum OrderStatusEnum : OrderStatusEnum.values()) {
            if (OrderStatusEnum.getOrderStatus() == orderStatus) {
                return OrderStatusEnum;
            }
        }
        return DEFAULT;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setName(String name) {
        this.name = name;
    }
}
