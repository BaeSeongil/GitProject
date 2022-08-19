package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_Product;
import com.project.mainPage.dto.tmp_StockNum;

@Mapper
public interface tmp_ProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(tmp_Product record);

    int insertSelective(tmp_Product record);

    tmp_Product selectByPrimaryKey(Long productId);

    tmp_Product selectByCategoryIdAndName(@Param("productName") String productName, @Param("CategoryId") Long CategoryId);

    int updateByPrimaryKeySelective(tmp_Product record);

    int updateByPrimaryKeyWithBLOBs(tmp_Product record);

    int updateByPrimaryKey(tmp_Product record);

    List<tmp_Product> selectByPrimaryKeys(List<Long> productIds);

    int batchInsert(@Param("ProductList") List<tmp_Product> ProductList);

    int updateStockNum(@Param("StockNumS") List<tmp_StockNum> StockNumS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}