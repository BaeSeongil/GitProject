package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_OrderItem;

@Mapper
public interface tmp_OrderItemMapper {
    
    int deleteByPrimaryKey(Long orderItemId);

    int insert(tmp_OrderItem record);

    int insertSelective(tmp_OrderItem record);

    tmp_OrderItem selectByPrimaryKey(Long orderItemId);

    // orderId로 주문 리스트 호출
    List<tmp_OrderItem> selectByOrderId(Long orderId);

    // orderId(여러개)로 주문 리스트 호출
    List<tmp_OrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    // 주문 데이터 일괄 삽입
    int insertBatch(@Param("orderItems") List<tmp_OrderItem> orderItems);

    int updateByPrimaryKeySelective(tmp_OrderItem record);

    int updateByPrimaryKey(tmp_OrderItem record);
}