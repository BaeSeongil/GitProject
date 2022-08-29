package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_Product;
import com.project.mainPage.dto.tmp_StockNum;
import com.project.mainPage.util.PageQueryUtil;

@Mapper
public interface tmp_ProductMapper {
    // 삭제
    int deleteByPrimaryKey(Long productId);
    // 등록
    int insert(tmp_Product record);
    // 등록
    int insertSelective(tmp_Product record);
    // 호출
    tmp_Product selectByPrimaryKey(Long productId);
    // Id와 상품명으로 호출
    tmp_Product selectByCategoryIdAndName(@Param("productName") String productName, @Param("CategoryId") Long CategoryId);
    // 업데이트
    int updateByPrimaryKeySelective(tmp_Product record);
    // 업데이트: 파일 객체(img)
    int updateByPrimaryKeyWithBLOBs(tmp_Product record);
    // 업데이트
    int updateByPrimaryKey(tmp_Product record);
    // 여러 ID로 상품 리스트 호출
    List<tmp_Product> selectByPrimaryKeys(List<Long> productIds);
    // 일괄 등록
    int batchInsert(@Param("ProductList") List<tmp_Product> ProductList);
    // 재고수량 업데이트
    int updateStockNum(@Param("StockNumS") List<tmp_StockNum> StockNumS);
    // 일괄 업데이트 : 진열 상태
    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);
    List<tmp_Product> findProductList(PageQueryUtil pageUtil);
    int getTotalProduct(PageQueryUtil pageUtil);
    List<tmp_Product> findProductListBySearch(PageQueryUtil pageUtil);
    int getTotalProductBySearch(PageQueryUtil pageUtil);
}