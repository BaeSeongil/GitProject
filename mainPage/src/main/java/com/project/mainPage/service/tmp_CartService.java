package com.project.mainPage.service;

import com.project.mainPage.controller.vo.CartItemVO;
import com.project.mainPage.dto.tmp_CartItem;

import java.util.List;

public interface tmp_CartService {
    // 장바구니에 상품 저장
    String saveCartItem(tmp_CartItem CartItem);
    // 장바구니 업데이트
    String updateCartItem(tmp_CartItem CartItem);
    // 장바구니 세부 정보 가져오기
    tmp_CartItem getCartItemById(Long CartItemId);
    // 장바구니에서 상품 삭제
    Boolean deleteById(Long CartItemId, Long userId);
    // 장바구니의 목록 데이터 가져오기
    List<CartItemVO> getMyCartItems(Long UserId);
}
