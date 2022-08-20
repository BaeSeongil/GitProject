package com.project.mainPage.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.controller.vo.OrderItemVO;
import com.project.mainPage.dto.tmp_Order;
import com.project.mainPage.service.tmp_OrderService;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.Result;
import com.project.mainPage.util.ResultGenerator;


@Controller
@RequestMapping("/admin")
public class tmp_OrderController {

    @Resource
    private tmp_OrderService OrderService;

    @GetMapping("/orders")
    public String ordersPage(HttpServletRequest request) {
        request.setAttribute("path", "orders");
        return "admin/list_order";
    }

    // 목록
    @RequestMapping(value = "/orders/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (!StringUtils.hasText((CharSequence) params.get("page")) || !StringUtils.hasText((CharSequence) params.get("limit"))) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(OrderService.getOrdersPage(pageUtil));
    }

 // 업데이트
    @RequestMapping(value = "/orders/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody tmp_Order Order) {
        if (Objects.isNull(Order.getTotalPrice())
                || Objects.isNull(Order.getOrderId())
                || Order.getOrderId() < 1
                || Order.getTotalPrice() < 1
                || !StringUtils.hasText(Order.getUserAddress())) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        String result = OrderService.updateOrderInfo(Order);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

   // 상세 정보
    @GetMapping("/order-items/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        List<OrderItemVO> orderItems = OrderService.getOrderItems(id);
        if (!CollectionUtils.isEmpty(orderItems)) {
            return ResultGenerator.genSuccessResult(orderItems);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    // 배송
    @RequestMapping(value = "/orders/checkDone", method = RequestMethod.POST)
    @ResponseBody
    public Result checkDone(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        String result = OrderService.checkDone(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    // 출고
    @RequestMapping(value = "/orders/checkOut", method = RequestMethod.POST)
    @ResponseBody
    public Result checkOut(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        String result = OrderService.checkOut(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    // 주문 마감
    @RequestMapping(value = "/orders/close", method = RequestMethod.POST)
    @ResponseBody
    public Result closeOrder(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        String result = OrderService.closeOrder(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


}