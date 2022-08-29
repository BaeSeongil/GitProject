package com.project.mainPage.service.impl;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.project.mainPage.common.Constants;
import com.project.mainPage.common.OrderStatusEnum;
import com.project.mainPage.common.PayStatusEnum;
import com.project.mainPage.common.PayTypeEnum;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.common.ShopException;
import com.project.mainPage.controller.vo.CartItemVO;
import com.project.mainPage.controller.vo.OrderDetailVO;
import com.project.mainPage.controller.vo.OrderItemVO;
import com.project.mainPage.controller.vo.OrderListVO;
import com.project.mainPage.controller.vo.UserVO;
import com.project.mainPage.dto.tmp_Product;
import com.project.mainPage.dto.tmp_Order;
import com.project.mainPage.dto.tmp_OrderItem;
import com.project.mainPage.dto.tmp_StockNum;
import com.project.mainPage.mapper.tmp_ProductMapper;
import com.project.mainPage.mapper.tmp_CartItemMapper;
import com.project.mainPage.mapper.tmp_OrderItemMapper;
import com.project.mainPage.mapper.tmp_OrderMapper;
import com.project.mainPage.service.tmp_OrderService;
import com.project.mainPage.util.BeanUtil;
import com.project.mainPage.util.NumberUtil;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.PageResult;

@Service
public class tmp_OrderServiceImpl implements tmp_OrderService {

    @Autowired
    private tmp_OrderMapper tmp_OrderMapper;
    @Autowired
    private tmp_OrderItemMapper tmp_OrderItemMapper;
    @Autowired
    private tmp_CartItemMapper tmp_CartItemMapper;
    @Autowired
    private tmp_ProductMapper ProductMapper;

    @Override
    public PageResult getOrdersPage(PageQueryUtil pageUtil) {
        List<tmp_Order> Orders = tmp_OrderMapper.findOrderList(pageUtil);
        int total = tmp_OrderMapper.getTotalOrders(pageUtil);
        PageResult pageResult = new PageResult(Orders, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String updateOrderInfo(tmp_Order tmp_Order) {
        tmp_Order temp = tmp_OrderMapper.selectByPrimaryKey(tmp_Order.getOrderId());
        // 비어 있지 않고 orderStatus>=0이면 출고되기 전에 상태를 수정할 수 있습니다.
        if (temp != null && temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3) {
            temp.setTotalPrice(tmp_Order.getTotalPrice());
            temp.setUserAddress(tmp_Order.getUserAddress());
            temp.setUpdateTime(new Date());
            if (tmp_OrderMapper.updateByPrimaryKeySelective(temp) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkDone(Long[] ids) {
        // 모든 주문 조회, 상태 판단, 상태 수정 및 시간 업데이트
        List<tmp_Order> orders = tmp_OrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errortmp_OrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (tmp_Order tmp_Order : orders) {
                if (tmp_Order.getIsDeleted() == 1) {
                    errortmp_OrderNos += tmp_Order.getOrderNo() + " ";
                    continue;
                }
                if (tmp_Order.getOrderStatus() != 1) {
                    errortmp_OrderNos += tmp_Order.getOrderNo() + " ";
                }
            }
            if (!StringUtils.hasText(errortmp_OrderNos)) {
                // 주문 상태는 정상이고 배송 완료 작업을 수행할 수 있으며 주문 상태 및 업데이트 시간을 수정할 수 있습니다
                if (tmp_OrderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                // 주문은 지금 출고 불가합니다
                if (errortmp_OrderNos.length() > 0 && errortmp_OrderNos.length() < 100) {
                    return errortmp_OrderNos + "주문 상태가 결제 성공이 아닌 경우 출고 작업을 수행할 수 없습니다";
                } else {
                    return "당신은 지불이 성공한 주문서가 아닌 상태를 너무 많이 선택하여 배송완료 작업을 수행할 수 없습니다";
                }
            }
        }
        // 데이터를 찾을 수 없음 오류 메시지 반환
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkOut(Long[] ids) {
        // 모든 주문 조회, 상태 판단, 상태 수정 및 시간 업데이트
        List<tmp_Order> orders = tmp_OrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errortmp_OrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (tmp_Order tmp_Order : orders) {
                if (tmp_Order.getIsDeleted() == 1) {
                    errortmp_OrderNos += tmp_Order.getOrderNo() + " ";
                    continue;
                }
                if (tmp_Order.getOrderStatus() != 1 && tmp_Order.getOrderStatus() != 2) {
                    errortmp_OrderNos += tmp_Order.getOrderNo() + " ";
                }
            }
            if (!StringUtils.hasText(errortmp_OrderNos)) {
                // 주문 상태는 정상이고 출고 완료 작업을 수행할 수 있으며 주문 상태 및 업데이트 시간을 수정할 수 있습니다
                if (tmp_OrderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                // 주문은 지금 출고 불가합니다
                if (errortmp_OrderNos.length() > 0 && errortmp_OrderNos.length() < 100) {
                    return errortmp_OrderNos + "주문의 상태가 결제성공이 아니거나 배송완료 후 출고불능 상태입니다.";
                } else {
                    return "당신은 배송완료된 주문서가 아닌 상태를 너무 많이 선택하여 출고완료 작업을 수행할 수 없습니다";
                }
            }
        }
        // 데이터를 찾을 수 없음 오류 메시지 반환
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String closeOrder(Long[] ids) {
        // 모든 주문 조회, 상태 판단, 상태 수정 및 시간 업데이트
        List<tmp_Order> orders = tmp_OrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errortmp_OrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (tmp_Order tmp_Order : orders) {
                // isDeleted=1 마감된 주문이어야 합니다
                if (tmp_Order.getIsDeleted() == 1) {
                    errortmp_OrderNos += tmp_Order.getOrderNo() + " ";
                    continue;
                }
                // 주문이 마감되었거나 완료되어 마감할 수 없습니다
                if (tmp_Order.getOrderStatus() == 4 || tmp_Order.getOrderStatus() < 0) {
                    errortmp_OrderNos += tmp_Order.getOrderNo() + " ";
                }
            }
            if (!StringUtils.hasText(errortmp_OrderNos)) {
                // 주문 상태는 정상이며 마감 작업을 수행할 수 있으며 주문 상태 및 업데이트 시간을 수정할 수 있습니다
                if (tmp_OrderMapper.closeOrder(Arrays.asList(ids),
                        OrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                // 현재 주문을 마감할 수 없습니다
                if (errortmp_OrderNos.length() > 0 && errortmp_OrderNos.length() < 100) {
                    return errortmp_OrderNos + "주문을 마감할 수 없습니다";
                } else {
                    return "선택한 주문을 마감할 수 없습니다.";
                }
            }
        }
        // 데이터를 찾을 수 없음 오류 메시지 반환
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String saveOrder(UserVO user, List<CartItemVO> myCartItems) {
        List<Long> itemIdList = myCartItems.stream().map(CartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> productIds = myCartItems.stream().map(CartItemVO::getProductId).collect(Collectors.toList());
        List<tmp_Product> ProductList = ProductMapper.selectByPrimaryKeys(productIds);
        // 단종 품목 확인
        List<tmp_Product> productListNotSelling = ProductList.stream()
                .filter(ProductTemp -> ProductTemp.getProductSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(productListNotSelling)) {
            // productListNotSelling 개체가 비어 있지 않으면 단종 품목이 있음을 의미합니다
            ShopException.fail(productListNotSelling.get(0).getProductName() + "주문을 생성할 수 없습니다");
        }
        Map<Long, tmp_Product> ProductMap = ProductList.stream()
                .collect(Collectors.toMap(tmp_Product::getProductId, Function.identity(), (entity1, entity2) -> entity1));
        // 제품 재고 결정
        for (CartItemVO CartItemVO : myCartItems) {
            // 장바구니의 관련 제품 데이터가 검색된 제품에 존재하지 않습니다
            // 오류 알림이 반환됩니다
            if (!ProductMap.containsKey(CartItemVO.getProductId())) {
                ShopException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            // 수량이 재고보다 많은 상황이 있으며, 오류 알림이 직접 반환됩니다
            if (CartItemVO.getProductCount() > ProductMap.get(CartItemVO.getProductId()).getStockNum()) {
                ShopException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        // 구매 항목 삭제
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(productIds)
                && !CollectionUtils.isEmpty(ProductList)) {
            if (tmp_CartItemMapper.deleteBatch(itemIdList) > 0) {
                List<tmp_StockNum> tmp_StockNumS = BeanUtil.copyList(myCartItems, tmp_StockNum.class);
                int updateStockNumResult = ProductMapper.updateStockNum(tmp_StockNumS);
                if (updateStockNumResult < 1) {
                    ShopException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                // 주문 번호 생성
                String orderNo = NumberUtil.genOrderNo();
                int priceTotal = 0;
                // 주문서 저장
                tmp_Order tmp_Order = new tmp_Order();
                tmp_Order.setOrderNo(orderNo);
                tmp_Order.setUserId(user.getUserId());
                tmp_Order.setUserAddress(user.getAddress());
                // 총 주문금액
                for (CartItemVO CartItemVO : myCartItems) {
                    priceTotal += CartItemVO.getProductCount() * CartItemVO.getSellingPrice();
                }
                if (priceTotal < 1) {
                    ShopException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                tmp_Order.setTotalPrice(priceTotal);
                // 주문 본문 필드는 결제 주문 생성을 위한 설명 정보로 사용되며, 제3자 결제 인터페이스와 연결되어 있지 않으므로 이 필드는 임시로 빈
                // 문자열로 설정됩니다
                String extraInfo = "";
                tmp_Order.setExtraInfo(extraInfo);
                // 주문상품 생성 및 주문상품 기록 저장
                if (tmp_OrderMapper.insertSelective(tmp_Order) > 0) {
                    // 모든 주문 항목 스냅샷 생성 및 데이터베이스에 저장
                    List<tmp_OrderItem> tmp_OrderItems = new ArrayList<>();
                    for (CartItemVO CartItemVO : myCartItems) {
                        tmp_OrderItem tmp_OrderItem = new tmp_OrderItem();
                        // BeanUtil 툴 클래스를 사용하여 CartItemVO의 속성을 tmp_OrderItem 오브젝트로 복사
                        BeanUtil.copyProperties(CartItemVO, tmp_OrderItem);
                        // useGeneratedKeys는 tmp_OrderMapper 파일의 insert() 메소드에서 사용되므로 orderId를 얻을 수 있습니다.
                        tmp_OrderItem.setOrderId(tmp_Order.getOrderId());
                        tmp_OrderItems.add(tmp_OrderItem);
                    }
                    // 데이터베이스에 저장
                    if (tmp_OrderItemMapper.insertBatch(tmp_OrderItems) > 0) {
                        // 모든 작업이 성공한 후 Controller 메서드에 대한 주문 번호를 반환하여 주문 세부 정보로 이동
                        return orderNo;
                    }
                    ShopException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                ShopException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            ShopException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        ShopException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }

    @Override
    public OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        tmp_Order tmp_Order = tmp_OrderMapper.selectByOrderNo(orderNo);
        if (tmp_Order == null) {
            ShopException.fail(ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult());
        }
        // 현재 userId의 주문인지 확인
        // 그렇지 않으면 오류 보고
        if (!userId.equals(tmp_Order.getUserId())) {
            ShopException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        List<tmp_OrderItem> orderItems = tmp_OrderItemMapper.selectByOrderId(tmp_Order.getOrderId());
        // 주문 항목 데이터 가져오기
        if (CollectionUtils.isEmpty(orderItems)) {
            ShopException.fail(ServiceResultEnum.ORDER_ITEM_NOT_EXIST_ERROR.getResult());
        }
        List<OrderItemVO> OrderItemVOS = BeanUtil.copyList(orderItems, OrderItemVO.class);
        OrderDetailVO OrderDetailVO = new OrderDetailVO();
        BeanUtil.copyProperties(tmp_Order, OrderDetailVO);
        OrderDetailVO.setOrderStatusString(
                OrderStatusEnum.getOrderStatusEnumByStatus(OrderDetailVO.getOrderStatus()).getName());
        OrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(OrderDetailVO.getPayType()).getName());
        OrderDetailVO.setOrderItemVOS(OrderItemVOS);
        return OrderDetailVO;
    }

    @Override
    public tmp_Order getOrderByOrderNo(String orderNo) {
        return tmp_OrderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = tmp_OrderMapper.getTotalOrders(pageUtil);
        List<tmp_Order> tmp_Orders = tmp_OrderMapper.findOrderList(pageUtil);
        List<OrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            // 데이터 변환 : dto를 vo로 변환
            orderListVOS = BeanUtil.copyList(tmp_Orders, OrderListVO.class);
            // 주문 상태 표시 값 설정
            for (OrderListVO OrderListVO : orderListVOS) {
                OrderListVO.setOrderStatusString(
                        OrderStatusEnum.getOrderStatusEnumByStatus(OrderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = tmp_Orders.stream().map(tmp_Order::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<tmp_OrderItem> orderItems = tmp_OrderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<tmp_OrderItem>> itemByOrderIdMap = orderItems.stream()
                        .collect(groupingBy(tmp_OrderItem::getOrderId));
                for (OrderListVO OrderListVO : orderListVOS) {
                    // 각 주문 목록 개체별 주문 항목 데이터 캡슐화
                    if (itemByOrderIdMap.containsKey(OrderListVO.getOrderId())) {
                        List<tmp_OrderItem> orderItemListTemp = itemByOrderIdMap.get(OrderListVO.getOrderId());
                        // tmp_OrderItem 개체 목록을 OrderItemVO 개체 목록으로 변환
                        List<OrderItemVO> OrderItemVOS = BeanUtil.copyList(orderItemListTemp, OrderItemVO.class);
                        OrderListVO.setOrderItemVOS(OrderItemVOS);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String cancelOrder(String orderNo, Long userId) {
        tmp_Order tmp_Order = tmp_OrderMapper.selectByOrderNo(orderNo);
        if (tmp_Order != null) {
            // 현재 userId의 주문인지 확인
            // 그렇지 않으면 오류 보고
            if (!userId.equals(tmp_Order.getUserId())) {
                ShopException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            }
            // 주문 상태 판단
            if (tmp_Order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_SUCCESS.getOrderStatus()
                    || tmp_Order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_CLOSED_BY_User.getOrderStatus()
                    || tmp_Order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
                    || tmp_Order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            if (tmp_OrderMapper.closeOrder(Collections.singletonList(tmp_Order.getOrderId()),
                    OrderStatusEnum.ORDER_CLOSED_BY_User.getOrderStatus()) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String finishOrder(String orderNo, Long userId) {
        tmp_Order tmp_Order = tmp_OrderMapper.selectByOrderNo(orderNo);
        if (tmp_Order != null) {
            // 현재 userId의 주문인지 확인
            // 그렇지 않으면 오류 보고
            if (!userId.equals(tmp_Order.getUserId())) {
                return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
            }
            // 주문 상태 판단
            // 출고되지 않은 상태에서는 수정작업이 진행되지 않습니다
            if (tmp_Order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_EXPRESS.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            tmp_Order.setOrderStatus((byte) OrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            tmp_Order.setUpdateTime(new Date());
            if (tmp_OrderMapper.updateByPrimaryKeySelective(tmp_Order) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        tmp_Order tmp_Order = tmp_OrderMapper.selectByOrderNo(orderNo);
        if (tmp_Order != null) {
            // 주문상태판단
            // 미지급상태에서는 수정작업이 진행되지 않습니다
            if (tmp_Order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            tmp_Order.setOrderStatus((byte) OrderStatusEnum.ORDER_PAID.getOrderStatus());
            tmp_Order.setPayType((byte) payType);
            tmp_Order.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            tmp_Order.setPayTime(new Date());
            tmp_Order.setUpdateTime(new Date());
            if (tmp_OrderMapper.updateByPrimaryKeySelective(tmp_Order) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public List<OrderItemVO> getOrderItems(Long id) {
        tmp_Order tmp_Order = tmp_OrderMapper.selectByPrimaryKey(id);
        if (tmp_Order != null) {
            List<tmp_OrderItem> orderItems = tmp_OrderItemMapper.selectByOrderId(tmp_Order.getOrderId());
            // 주문 항목 데이터 가져오기
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<OrderItemVO> OrderItemVOS = BeanUtil.copyList(orderItems, OrderItemVO.class);
                return OrderItemVOS;
            }
        }
        return null;
    }
}