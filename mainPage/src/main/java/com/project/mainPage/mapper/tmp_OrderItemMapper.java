package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_OrderItem;

@Mapper
public interface tmp_OrderItemMapper {
    // 삭제
    int deleteByPrimaryKey(Long orderItemId);
    // 등록
    int insert(tmp_OrderItem record);
    // 등록
    int insertSelective(tmp_OrderItem record);
    // 호출
    tmp_OrderItem selectByPrimaryKey(Long orderItemId);
    // orderId로 주문 리스트 호출
    List<tmp_OrderItem> selectByOrderId(Long orderId);
    // orderId(여러개)로 주문 리스트 호출
    List<tmp_OrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);
    // 주문 데이터 일괄 삽입
    int insertBatch(@Param("orderItems") List<tmp_OrderItem> orderItems);
    // 업데이트
    int updateByPrimaryKeySelective(tmp_OrderItem record);
    // 업데이트
    int updateByPrimaryKey(tmp_OrderItem record);
}