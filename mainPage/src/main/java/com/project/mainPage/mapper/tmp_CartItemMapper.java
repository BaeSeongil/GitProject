package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_CartItem;

@Mapper
public interface tmp_CartItemMapper {
    // Id를 통해 삭제
    int deleteByPrimaryKey(Long cartItemId);
    // 등록
    int insert(tmp_CartItem record);
    // 등록
    int insertSelective(tmp_CartItem record);
    // ID를 통해 데이터 가져오기
    tmp_CartItem selectByPrimaryKey(Long cartItemId);
    // UserId와 productId를 통해 데이터 가져오기
    tmp_CartItem selectByUserIdAndProductId(@Param("userId") Long UserId, @Param("productId") Long productId);
    // UserId를 통해 데이터 가져오기
    List<tmp_CartItem> selectByUserId(@Param("userId") Long UserId, @Param("number") int number);
    // UserId와 일치하는 장바구니 개수
    int selectCountByUserId(@Param("userId")Long UserId);
    // 업데이트
    int updateByPrimaryKeySelective(tmp_CartItem record);
    // 업데이트
    int updateByPrimaryKey(tmp_CartItem record);
    // 일괄삭제
    int deleteBatch(List<Long> ids);
}