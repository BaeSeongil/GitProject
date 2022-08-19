package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_CartItem;

@Mapper
public interface tmp_CartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(tmp_CartItem record);

    int insertSelective(tmp_CartItem record);

    tmp_CartItem selectByPrimaryKey(Long cartItemId);

    tmp_CartItem selectByUserIdAndProductId(@Param("newBeeUserId") Long newBeeUserId, @Param("productId") Long productId);

    List<tmp_CartItem> selectByUserId(@Param("newBeeUserId") Long newBeeUserId, @Param("number") int number);

    int selectCountByUserId(Long newBeeUserId);

    int updateByPrimaryKeySelective(tmp_CartItem record);

    int updateByPrimaryKey(tmp_CartItem record);
    // 일괄삭제
    int deleteBatch(List<Long> ids);
}