package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_Order;

@Mapper
public interface tmp_OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(tmp_Order record);

    int insertSelective(tmp_Order record);

    tmp_Order selectByPrimaryKey(Long orderId);

    tmp_Order selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(tmp_Order record);

    int updateByPrimaryKey(tmp_Order record);

    List<tmp_Order> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    int checkOut(@Param("orderIds") List<Long> orderIds);

    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    int checkDone(@Param("orderIds") List<Long> asList);
}