package com.project.mainPage.service;

import com.project.mainPage.dto.tmp_Product;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.PageResult;

import java.util.List;

public interface tmp_ProductService {
    // 백엔드
    // 상품 정보 가져오기
    PageResult getProductPage(PageQueryUtil pageUtil);
    // 상품 추가
    String saveProduct(tmp_Product product);
    // 상품 데이터 일괄 추가
    void batchSaveProduct(List<tmp_Product> ProductList);
    // 상품 정보 수정
    String updateProduct(tmp_Product product);
    // 상품 세부정보 가져오기
    tmp_Product getProductById(Long id);
    // 판매상태 일괄수정(진열/판매안함)
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    // 상품검색
    PageResult searchProduct(PageQueryUtil pageUtil);
}
