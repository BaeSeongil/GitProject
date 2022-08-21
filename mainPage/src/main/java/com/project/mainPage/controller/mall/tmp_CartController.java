package com.project.mainPage.controller.mall;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.mainPage.common.Constants;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.common.ShopException;
import com.project.mainPage.controller.vo.CartItemVO;
import com.project.mainPage.controller.vo.UserVO;
import com.project.mainPage.dto.tmp_CartItem;
import com.project.mainPage.service.tmp_CartService;
import com.project.mainPage.util.Result;
import com.project.mainPage.util.ResultGenerator;

@Controller
@RequestMapping("/mall")
public class tmp_CartController {

    @Resource
    private tmp_CartService CartService;

    @GetMapping({"/cart"})
    public String cartListPage(HttpServletRequest request,
                               HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        int itemsTotal = 0;
        int priceTotal = 0;
        List<CartItemVO> myCartItems = CartService.getMyCartItems(user.getUserId());
        if (!CollectionUtils.isEmpty(myCartItems)) {
            //총 구매 항목 수
            itemsTotal = myCartItems.stream().mapToInt(CartItemVO::getProductCount).sum();
            if (itemsTotal < 1) {
                ShopException.fail("구매 항목을 비워둘 수 없습니다");
            }
            //총 주문금액
            for (CartItemVO CartItemVO : myCartItems) {
                priceTotal += CartItemVO.getProductCount() * CartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                ShopException.fail("주문 가격이 비정상입니다");
            }
        }
        request.setAttribute("itemsTotal", itemsTotal);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myCartItems", myCartItems);
        return "mall/cart";
    }

    @PostMapping({"/cart"})
    @ResponseBody
    public Result saveCartItem(@RequestBody tmp_CartItem CartItem,
                                                 HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        CartItem.setUserId(user.getUserId());
        String saveResult = CartService.saveCartItem(CartItem);
        //추가 성공
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //추가 실패
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping({"/cart"})
    @ResponseBody
    public Result updateCartItem(@RequestBody tmp_CartItem CartItem,
                                                   HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        CartItem.setUserId(user.getUserId());
        String updateResult = CartService.updateCartItem(CartItem);
        //업데이트 성공
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //업데이트 실패
        return ResultGenerator.genFailResult(updateResult);
    }

    @DeleteMapping("/cart/{CartItemId}")
    @ResponseBody
    public Result updateCartItem(@PathVariable("CartItemId") Long CartItemId,
                                                   HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Boolean deleteResult = CartService.deleteById(CartItemId,user.getUserId());
        //삭제 성공
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //삭제 실패
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/cart/settle")
    public String settlePage(HttpServletRequest request,
                             HttpSession httpSession) {
        int priceTotal = 0;
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<CartItemVO> myCartItems = CartService.getMyCartItems(user.getUserId());
        if (CollectionUtils.isEmpty(myCartItems)) {
            // 데이터가 없으면 결제 페이지로 이동하지 않습니다
            return "/cart";
        } else {
            //총 주문금액
            for (CartItemVO CartItemVO : myCartItems) {
                priceTotal += CartItemVO.getProductCount() * CartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                ShopException.fail("주문 가격이 비정상입니다");
            }
        }
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myCartItems", myCartItems);
        return "mall/order-settle";
    }
}
