package com.project.mainPage.service.impl;

import com.project.mainPage.common.Constants;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.controller.vo.CartItemVO;
import com.project.mainPage.mapper.tmp_ProductMapper;
import com.project.mainPage.mapper.tmp_CartItemMapper;
import com.project.mainPage.dto.tmp_Product;
import com.project.mainPage.dto.tmp_CartItem;
import com.project.mainPage.service.tmp_CartService;
import com.project.mainPage.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class tmp_CartServiceImpl implements tmp_CartService {

    @Autowired
    private tmp_CartItemMapper CartItemMapper;

    @Autowired
    private tmp_ProductMapper ProductMapper;

    @Override
    public String saveCartItem(tmp_CartItem CartItem) {
        tmp_CartItem temp = CartItemMapper.selectByUserIdAndProductId(CartItem.getUserId(), CartItem.getProductId());
        if (temp != null) {
            // 데이터가 이미 있다면 수정
            temp.setProductCount(CartItem.getProductCount());
            return updateCartItem(temp);
        }
        tmp_Product Product = ProductMapper.selectByPrimaryKey(CartItem.getProductId());
        // 상품이 비어 있음
        if (Product == null) {
            return ServiceResultEnum.PRODUCT_NOT_EXIST.getResult();
        }
        int totalItem = CartItemMapper.selectCountByUserId(CartItem.getUserId()) + 1;
        // 단일상품의 최대수량 초과
        if (CartItem.getProductCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        // 최대 수량을 초과
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        // 데이터 업데이트
        if (CartItemMapper.insertSelective(CartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCartItem(tmp_CartItem CartItem) {
        tmp_CartItem CartItemUpdate = CartItemMapper.selectByPrimaryKey(CartItem.getCartItemId());
        if (CartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        // 단일상품의 최대수량 초과
        if (CartItem.getProductCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        // 현재 로그인 계정의 userId가 수정하고자 하는 cartItem의 userId와 다르다면 error 반환
        if (!CartItemUpdate.getUserId().equals(CartItem.getUserId())) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        // 값이 같으면 데이터 연산을 수행 중단
        if (CartItem.getProductCount().equals(CartItemUpdate.getProductCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        CartItemUpdate.setProductCount(CartItem.getProductCount());
        CartItemUpdate.setUpdateTime(new Date());
        // 데이터 업데이트
        if (CartItemMapper.updateByPrimaryKeySelective(CartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public tmp_CartItem getCartItemById(Long CartItemId) {
        return CartItemMapper.selectByPrimaryKey(CartItemId);
    }

    @Override
    public Boolean deleteById(Long CartItemId, Long userId) {
        tmp_CartItem CartItem = CartItemMapper.selectByPrimaryKey(CartItemId);
        if (CartItem == null) {
            return false;
        }
        // userId가 다르면 삭제할 수 없습니다
        if (!userId.equals(CartItem.getUserId())) {
            return false;
        }
        return CartItemMapper.deleteByPrimaryKey(CartItemId) > 0;
    }

    @Override
    public List<CartItemVO> getMyCartItems(Long UserId) {
        List<CartItemVO> CartItemVOS = new ArrayList<>();
        List<tmp_CartItem> CartItems = CartItemMapper.selectByUserId(UserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(CartItems)) {
            // 상품 정보 조회 및 데이터 변환
            List<Long> ProductIds = CartItems.stream().map(tmp_CartItem::getProductId).collect(Collectors.toList());
            List<tmp_Product> ProductList = ProductMapper.selectByPrimaryKeys(ProductIds);
            Map<Long, tmp_Product> ProductMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(ProductList)) {
                ProductMap = ProductList.stream().collect(Collectors.toMap(tmp_Product::getProductId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (tmp_CartItem CartItem : CartItems) {
                CartItemVO CartItemVO = new CartItemVO();
                BeanUtil.copyProperties(CartItem, CartItemVO);
                if (ProductMap.containsKey(CartItem.getProductId())) {
                    tmp_Product ProductTemp = ProductMap.get(CartItem.getProductId());
                    CartItemVO.setProductCoverImg(ProductTemp.getProductCoverImg());
                    String productName = ProductTemp.getProductName();
                    // 문자열이 너무 길 경우
                    if (productName.length() > 28) {
                        productName = productName.substring(0, 28) + "...";
                    }
                    CartItemVO.setProductName(productName);
                    CartItemVO.setSellingPrice(ProductTemp.getSellingPrice());
                    CartItemVOS.add(CartItemVO);
                }
            }
        }
        return CartItemVOS;
    }
}
