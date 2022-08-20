package com.project.mainPage.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShopException extends RuntimeException {

    public ShopException(String message) {
        super(message);
    }

    // 예외 던지기 (의도적으로 예외를 발생)
    public static void fail(String message) {
        throw new ShopException(message);
    }

}
