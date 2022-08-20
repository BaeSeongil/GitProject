package com.project.mainPage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.mainPage.common.CategoryLevelEnum;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.common.ShopException;
import com.project.mainPage.controller.vo.SearchProductVO;
import com.project.mainPage.dto.tmp_Category;
import com.project.mainPage.dto.tmp_Product;
import com.project.mainPage.mapper.tmp_CategoryMapper;
import com.project.mainPage.mapper.tmp_ProductMapper;
import com.project.mainPage.service.tmp_ProductService;
import com.project.mainPage.util.BeanUtil;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.PageResult;

@Service
public class tmp_ProductServiceImpl implements tmp_ProductService {

    @Autowired
    private tmp_ProductMapper productMapper;
    @Autowired
    private tmp_CategoryMapper CategoryMapper;

    @Override
    public PageResult getProductPage(PageQueryUtil pageUtil) {
        List<tmp_Product> productList = productMapper.findProductList(pageUtil);
        int total = productMapper.getTotalProduct(pageUtil);
        PageResult pageResult = new PageResult(productList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveProduct(tmp_Product product) {
        tmp_Category Category = CategoryMapper.selectByPrimaryKey(product.getCategoryId());
        // 카테고리가 존재하지 않거나 상세분류가 아닌 경우 매개변수 필드가 비정상입니다.
        if (Category == null || Category.getCategoryLevel().intValue() != CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.PRODUCT_CATEGORY_ERROR.getResult();
        }
        if (productMapper.selectByCategoryIdAndName(product.getProductName(), product.getCategoryId()) != null) {
            return ServiceResultEnum.SAME_PRODUCT_EXIST.getResult();
        }
        if (productMapper.insertSelective(product) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveProduct(List<tmp_Product> ProductList) {
        if (!CollectionUtils.isEmpty(ProductList)) {
            productMapper.batchInsert(ProductList);
        }
    }

    @Override
    public String updateProduct(tmp_Product product) {
        tmp_Category Category = CategoryMapper.selectByPrimaryKey(product.getCategoryId());
        // 카테고리가 존재하지 않거나 상세분류가 아닌 경우 매개변수 필드가 비정상입니다.
        if (Category == null || Category.getCategoryLevel().intValue() != CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.PRODUCT_CATEGORY_ERROR.getResult();
        }
        tmp_Product temp = productMapper.selectByPrimaryKey(product.getProductId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        tmp_Product temp2 = productMapper.selectByCategoryIdAndName(product.getProductName(), product.getCategoryId());
        if (temp2 != null && !temp2.getProductId().equals(product.getProductId())) {
            //name과 Category ID가 동일하고 다른 ID는 계속 수정할 수 없습니다
            return ServiceResultEnum.SAME_PRODUCT_EXIST.getResult();
        }
        product.setUpdateTime(new Date());
        if (productMapper.updateByPrimaryKeySelective(product) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public tmp_Product getProductById(Long id) {
        tmp_Product Product = productMapper.selectByPrimaryKey(id);
        if (Product == null) {
            ShopException.fail(ServiceResultEnum.PRODUCT_NOT_EXIST.getResult());
        }
        return Product;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return productMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult searchProduct(PageQueryUtil pageUtil) {
        List<tmp_Product> productList = productMapper.findProductListBySearch(pageUtil);
        int total = productMapper.getTotalProductBySearch(pageUtil);
        List<SearchProductVO> SearchProductVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(productList)) {
            SearchProductVOS = BeanUtil.copyList(productList, SearchProductVO.class);
            for (SearchProductVO SearchProductVO : SearchProductVOS) {
                String productName = SearchProductVO.getProductName();
                String productIntro = SearchProductVO.getProductIntro();
                // 문자열이 너무 길 경우
                if (productName.length() > 28) {
                    productName = productName.substring(0, 28) + "...";
                    SearchProductVO.setProductName(productName);
                }
                if (productIntro.length() > 30) {
                    productIntro = productIntro.substring(0, 30) + "...";
                    SearchProductVO.setProductIntro(productIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(SearchProductVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
