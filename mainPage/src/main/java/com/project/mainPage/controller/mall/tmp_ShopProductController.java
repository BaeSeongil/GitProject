package com.project.mainPage.controller.mall;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.mainPage.common.Constants;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.common.ShopException;
import com.project.mainPage.controller.vo.ProductDetailVO;
import com.project.mainPage.controller.vo.SearchPageCategoryVO;
import com.project.mainPage.dto.tmp_Product;
import com.project.mainPage.service.tmp_CategoryService;
import com.project.mainPage.service.tmp_ProductService;
import com.project.mainPage.util.BeanUtil;
import com.project.mainPage.util.PageQueryUtil;

@Controller
@RequestMapping("/mall")
public class tmp_ShopProductController {

    @Resource
    private tmp_ProductService ProductService;
    @Resource
    private tmp_CategoryService CategoryService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (!StringUtils.hasText((CharSequence) params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.PRODUCT_SEARCH_PAGE_LIMIT);
        // 카테고리 데이터 캡슐화
        if (params.containsKey("CategoryId") && !!StringUtils.hasText(params.get("CategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("CategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = CategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("CategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        // 프론트 엔드 디스플레이를 위한 인자를 캡슐화
        if (params.containsKey("orderBy") && !!StringUtils.hasText(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        // 키워드를 필터링하여 공백 제거
        if (params.containsKey("keyword") && !!StringUtils.hasText((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        // 진열된 상태의 상품을 검색하다
        params.put("productSellStatus", Constants.SELL_STATUS_UP);
        // 제품 데이터 패키지
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", ProductService.searchProduct(pageUtil));
        return "mall/search";
    }

    @GetMapping("/product/detail/{productId}")
    public String detailPage(@PathVariable("productId") Long productId, HttpServletRequest request) {
        if (productId < 1) {
            ShopException.fail("파라미터가 비정상입니다");
        }
        tmp_Product product = ProductService.getProductById(productId);
        if (Constants.SELL_STATUS_UP != product.getProductSellStatus()) {
            ShopException.fail(ServiceResultEnum.PRODUCT_PUT_DOWN.getResult());
        }
        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtil.copyProperties(product, productDetailVO);
        //productDetailVO.setProductCarouselList(product.getProductCarousel().split(","));
        request.setAttribute("productDetail", productDetailVO);
        return "mall/detail";
    }

}
