package com.project.mainPage.service;

import com.project.mainPage.controller.vo.OrderDetailVO;
import com.project.mainPage.controller.vo.OrderItemVO;
import com.project.mainPage.controller.vo.CartItemVO;
import com.project.mainPage.controller.vo.UserVO;
import com.project.mainPage.dto.tmp_Order;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.PageResult;

import java.util.List;

public interface tmp_OrderService {
    // 백엔드
    // 주문 정보 가져오기
    PageResult getOrdersPage(PageQueryUtil pageUtil);
    // 주문 정보 업데이트
    String updateOrderInfo(tmp_Order Order);
    // 배송
    String checkDone(Long[] ids);
    // 출고
    String checkOut(Long[] ids);
    // 주문 마감
    String closeOrder(Long[] ids);
    // 주문 저장
    String saveOrder(UserVO user, List<CartItemVO> myCartItems);
    // 주문 세부정보 가져오기
    OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);
    // 주문 정보 가져오기
    tmp_Order getOrderByOrderNo(String orderNo);

    // 내 주문 목록
    PageResult getMyOrders(PageQueryUtil pageUtil);
    // 수동으로 주문 취소
    String cancelOrder(String orderNo, Long userId);
    // 상품 수령 확인
    String finishOrder(String orderNo, Long userId);
    // 결제 성공함
    String paySuccess(String orderNo, int payType);
    // 주문한 상품 정보 가져오기
    List<OrderItemVO> getOrderItems(Long id);
}
