package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_Order;
import com.project.mainPage.util.PageQueryUtil;

@Mapper
public interface tmp_OrderMapper {
    // 삭제
    int deleteByPrimaryKey(Long orderId);
    // 등록
    int insert(tmp_Order record);
    // 등록
    int insertSelective(tmp_Order record);
    // 데이터 호출
    tmp_Order selectByPrimaryKey(Long orderId);
    // Order 일련번호로 호출
    tmp_Order selectByOrderNo(String orderNo);
    // 업데이트
    int updateByPrimaryKeySelective(tmp_Order record);
    // 업데이트
    int updateByPrimaryKey(tmp_Order record);
    // 여러 orderId로 데이터 호출
    List<tmp_Order> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);
    // 배송완료
    int checkOut(@Param("orderIds") List<Long> orderIds);
    // 주문 취소
    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);
    // 포장완료
    int checkDone(@Param("orderIds") List<Long> asList);
    List<tmp_Order> findOrderList(PageQueryUtil pageUtil);
    int getTotalOrders(PageQueryUtil pageUtil);
}