package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class tmp_Order {
    private Long orderId;   // 주문 인덱스
    private String orderNo; // 주문 번호
    private Long userId;    // 구매자 인덱스
    private Integer totalPrice; // 총 가격
    private Byte payStatus; // 결제상태(0: 미입금, 1: 결제완료 -1: 결제 실패)
    private Byte payType;   // 결제방법(0: 없음)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;   // 결제일
    private Byte orderStatus;   // 주문상태(0: 미입금 1: 결제완료 2: 포장완료 3: 배송완료 4: 수령완료 -1: 수동취소 -2: 시간초과 -3: 판매종료)
    private String extraInfo;   // 주문 추가 정보
    private String userAddress; // 배송주소
    private Byte isDeleted; // ID 필드 삭제 (0: 삭제되지 않음 1: 삭제됨)
    // 생성일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    // 갱신일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}