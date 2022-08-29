package com.project.mainPage.controller.mall;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mainPage.common.Constants;
import com.project.mainPage.common.IndexConfigTypeEnum;
import com.project.mainPage.common.ShopException;
import com.project.mainPage.controller.vo.CategoryVO;
import com.project.mainPage.controller.vo.IndexConfigProductVO;
import com.project.mainPage.service.tmp_CategoryService;
import com.project.mainPage.service.tmp_IndexConfigService;

@Controller
@RequestMapping("/mall")
public class tmp_IndexController {
    @Resource
    private tmp_IndexConfigService IndexConfigService;

    @Resource
    private tmp_CategoryService CategoryService;

    @GetMapping({"", "/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<CategoryVO> categories = CategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            ShopException.fail("카테고리 데이터가 불완전합니다");
        }
        List<IndexConfigProductVO> hotProducts = IndexConfigService.getConfigProductesForIndex(IndexConfigTypeEnum.INDEX_PRODUCT_HOT.getType(), Constants.INDEX_PRODUCT_HOT_NUMBER);
        List<IndexConfigProductVO> newProducts = IndexConfigService.getConfigProductesForIndex(IndexConfigTypeEnum.INDEX_PRODUCT_NEW.getType(), Constants.INDEX_PRODUCT_NEW_NUMBER);
        List<IndexConfigProductVO> recommendProducts = IndexConfigService.getConfigProductesForIndex(IndexConfigTypeEnum.INDEX_PRODUCT_RECOMMOND.getType(), Constants.INDEX_PRODUCT_RECOMMOND_NUMBER);
        
        request.setAttribute("categories", categories); // 카테고리
        request.setAttribute("hotProducts", hotProducts); // 히트 상품
        request.setAttribute("newProducts", newProducts); // 신상품
        request.setAttribute("recommendProducts", recommendProducts); // 추천 상품
        return "mall/index";
    }
}
