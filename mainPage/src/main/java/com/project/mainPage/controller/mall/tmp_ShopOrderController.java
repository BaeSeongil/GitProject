package com.project.mainPage.controller.mall;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.mainPage.common.Constants;
import com.project.mainPage.common.OrderStatusEnum;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.common.ShopException;
import com.project.mainPage.controller.vo.CartItemVO;
import com.project.mainPage.controller.vo.OrderDetailVO;
import com.project.mainPage.controller.vo.UserVO;
import com.project.mainPage.dto.tmp_Order;
import com.project.mainPage.service.tmp_CartService;
import com.project.mainPage.service.tmp_OrderService;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.Result;
import com.project.mainPage.util.ResultGenerator;

@Controller
@RequestMapping("/mall")
public class tmp_ShopOrderController {

    @Resource
    private tmp_CartService CartService;
    @Resource
    private tmp_OrderService OrderService;

    @GetMapping("/orders/{orderNo}")
    public String orderDetailPage(HttpServletRequest request, @PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        OrderDetailVO orderDetailVO = OrderService.getOrderDetailByOrderNo(orderNo, user.getUserId());
        request.setAttribute("orderDetailVO", orderDetailVO);
        return "mall/order-detail";
    }

    @GetMapping("/orders")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("userId", user.getUserId());
        if (!StringUtils.hasText((CharSequence) params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        // 주문 데이터 캡슐화
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("orderPageResult", OrderService.getMyOrders(pageUtil));
        request.setAttribute("path", "orders");
        return "mall/my-order";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<CartItemVO> myCartItems = CartService.getMyCartItems(user.getUserId());
        if (!StringUtils.hasText(user.getAddress().trim())) {
            // 배송지 없음
            ShopException.fail(ServiceResultEnum.NULL_ADDRESS_ERROR.getResult());
        }
        if (CollectionUtils.isEmpty(myCartItems)) {
            // 장바구니에 데이터가 없으면
            // 에러 페이지로 넘어간다
            ShopException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        }
        // 주문을 저장하고 주문 번호를 반환
        String saveOrderResult = OrderService.saveOrder(user, myCartItems);
        // 주문 세부정보 페이지로 이동
        return "redirect:/orders/" + saveOrderResult;
    }

    @PutMapping("/orders/{orderNo}/cancel")
    @ResponseBody
    public Result cancelOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String cancelOrderResult = OrderService.cancelOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/orders/{orderNo}/finish")
    @ResponseBody
    public Result finishOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String finishOrderResult = OrderService.finishOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @GetMapping("/selectPayType")
    public String selectPayType(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        tmp_Order Order = OrderService.getOrderByOrderNo(orderNo);
        // 주문 userId 확인
        if (!user.getUserId().equals(Order.getUserId())) {
            ShopException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        // 주문 상태 확인
        if (Order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            ShopException.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", Order.getTotalPrice());
        return "mall/pay-select";
    }

    @GetMapping("/payPage")
    public String payOrder(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession, @RequestParam("payType") int payType) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        tmp_Order Order = OrderService.getOrderByOrderNo(orderNo);
        // 주문 userId 확인
        if (!user.getUserId().equals(Order.getUserId())) {
            ShopException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        // 주문 상태 확인
        if (Order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            ShopException.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", Order.getTotalPrice());
        if (payType == 1) {
            return "mall/tmppay";   // 임시 구매 페이지로 이동
        } else {
            return "mall/tmppay";
        }
    }

    @GetMapping("/paySuccess")
    @ResponseBody
    public Result paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        String payResult = OrderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }

}
