package com.project.mainPage.controller.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

// 주문 상세 페이지 VO
@Getter @Setter
public class OrderDetailVO implements Serializable {
    private String orderNo;
    private Integer totalPrice;
    private Byte payStatus;
    private String payStatusString;
    private Byte payType;
    private String payTypeString;
    private Date payTime;
    private Byte orderStatus;
    private String orderStatusString;
    private String userAddress;
    private Date createTime;
    private List<OrderItemVO> OrderItemVOS;
}
