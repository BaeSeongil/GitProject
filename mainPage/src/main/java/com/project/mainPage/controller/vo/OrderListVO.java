package com.project.mainPage.controller.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

// 주문 목록 페이지 VO
@Getter @Setter
public class OrderListVO implements Serializable {
    private Long orderId;
    private String orderNo;
    private Integer totalPrice;
    private Byte payType;
    private Byte orderStatus;
    private String orderStatusString;
    private String userAddress;
    private Date createTime;
    private List<OrderItemVO> OrderItemVOS;
}
