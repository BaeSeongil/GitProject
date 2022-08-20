package com.project.mainPage.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceResultEnum {
    ERROR("error"),

    SUCCESS("success"),

    DATA_NOT_EXIST("데이터를 찾을 수 없습니다"),

    SAME_CATEGORY_EXIST("같은 이름의 카테고리가 이미 존재합니다"),

    SAME_LOGIN_NAME_EXIST("사용자 이름이 이미 존재합니다"),

    LOGIN_NAME_NULL("ID를 입력하세요"),

    LOGIN_PASSWORD_NULL("비밀번호를 입력하세요"),

    SAME_INDEX_CONFIG_EXIST("같은 홈페이지 구성 항목이 이미 존재합니다"),

    PRODUCT_CATEGORY_ERROR("카테고리 데이터가 비정상입니다"),

    SAME_PRODUCT_EXIST("같은 상품 정보가 이미 존재합니다"),

    PRODUCT_NOT_EXIST("상품이 존재하지 않습니다"),

    PRODUCT_PUT_DOWN("상품이 삭제되었습니다"),

    SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR("단일 품목의 최대 구매 수량을 초과했습니다"),

    SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR("장바구니의 최대 수량을 초과했습니다"),

    LOGIN_ERROR("로그인 실패"),

    LOGIN_USER_LOCKED("사용자 로그인이 차단되었습니다"),

    ORDER_NOT_EXIST_ERROR("주문이 존재하지 않습니다"),

    ORDER_ITEM_NOT_EXIST_ERROR("주문 상품이 존재하지 않습니다"),

    NULL_ADDRESS_ERROR("주소는 비워둘 수 없습니다"),

    ORDER_PRICE_ERROR("주문 가격이 비정상입니다"),

    ORDER_GENERATE_ERROR("주문 생성 순서가 비정상입니다"),

    SHOPPING_ITEM_ERROR("장바구니 데이터가 비정상입니다"),

    SHOPPING_ITEM_COUNT_ERROR("재고가 부족합니다"),

    ORDER_STATUS_ERROR("주문 상태가 비정상입니다"),

    OPERATE_ERROR("작업 실패"),

    NO_PERMISSION_ERROR("권한이 없습니다"),

    DB_ERROR("database error");

    private String result;

    public void setResult(String result) {
        this.result = result;
    }
}