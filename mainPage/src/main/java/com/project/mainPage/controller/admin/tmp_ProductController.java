package com.project.mainPage.controller.admin;

import java.util.Collections;
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

import com.project.mainPage.common.CategoryLevelEnum;
import com.project.mainPage.common.Constants;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.common.ShopException;
import com.project.mainPage.dto.tmp_Category;
import com.project.mainPage.dto.tmp_Product;
import com.project.mainPage.service.tmp_CategoryService;
import com.project.mainPage.service.tmp_ProductService;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.Result;
import com.project.mainPage.util.ResultGenerator;


@Controller
@RequestMapping("/admin")
public class tmp_ProductController {

    @Resource
    private tmp_ProductService ProductService;
    @Resource
    private tmp_CategoryService CategoryService;

    @GetMapping("/product")
    public String productPage(HttpServletRequest request) {
        request.setAttribute("path", "product");
        return "admin/list-product";
    }

    @GetMapping("/product/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        // 모든 대분류 조회
        List<tmp_Category> firstLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), CategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            // 대분류 리스트 내 첫번째 데이터에 존재하는 모든 중분류
            List<tmp_Category> secondLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), CategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                // 중분류 리스트 내 첫번째 데이터에 존재하는 모든 소분류
                List<tmp_Category> thirdLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), CategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "product-edit");
                return "admin/list-product-edit";
            }
        }
        ShopException.fail("카테고리 데이터가 불완전합니다");
        return null;
    }

    @GetMapping("/product/edit/{productId}")
    public String edit(HttpServletRequest request, @PathVariable("productId") Long productId) {
        request.setAttribute("path", "edit");
        tmp_Product Product = ProductService.getProductById(productId);
        if (Product.getCategoryId() > 0) {
            if (Product.getCategoryId() != null || Product.getCategoryId() > 0) {
                // 카테고리 필드가 존재하는 경우
                // 해당 카테고리 데이터를 쿼리
                // 카테고리의 3단계 연계 표시를 위해 프론트엔드에 반환
                tmp_Category currentCategory = CategoryService.getCategoryById(Product.getCategoryId());
                // 상품 테이블에 저장된 카테고리id 필드는 소분류 카테고리의 id이고
                // 소분류가 아닌 것은 잘못된 데이터입니다
                if (currentCategory != null && currentCategory.getCategoryLevel() == CategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    // 전체 대분류 카테고리
                    List<tmp_Category> firstLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), CategoryLevelEnum.LEVEL_ONE.getLevel());
                    // parentId에 따라 현재 parentId 아래의 모든 소분류를 쿼리
                    List<tmp_Category> thirdLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentCategory.getParentId()), CategoryLevelEnum.LEVEL_THREE.getLevel());
                    // 현재 소분류의 상위 분류인 중분류
                    tmp_Category secondCategory = CategoryService.getCategoryById(currentCategory.getParentId());
                    if (secondCategory != null) {
                        //  parentId에 따라 현재 parentId 아래의 모든 중분류를 쿼리
                        List<tmp_Category> secondLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), CategoryLevelEnum.LEVEL_TWO.getLevel());
                        // 현재 중분류의 상위 분류인 대분류
                        tmp_Category firestCategory = CategoryService.getCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            // 모든 카테고리 데이터를 얻은 후 request 오브젝트에 넣어 프런트엔드에서 읽기
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (Product.getCategoryId() == 0) {
            // 전체 대분류
            List<tmp_Category> firstLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), CategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                // 대분류 리스트 내 첫번째 데이터에 존재하는 모든 중분류
                List<tmp_Category> secondLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), CategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    // 중분류 리스트 내 첫번째 데이터에 존재하는 모든 소분류
                    List<tmp_Category> thirdLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), CategoryLevelEnum.LEVEL_THREE.getLevel());
                    request.setAttribute("firstLevelCategories", firstLevelCategories);
                    request.setAttribute("secondLevelCategories", secondLevelCategories);
                    request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }
        request.setAttribute("product", Product);
        request.setAttribute("path", "product-edit");
        return "admin/list-product-edit";
    }

    // 목록
    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (!StringUtils.hasText((CharSequence) params.get("page")) || !StringUtils.hasText((CharSequence) params.get("limit"))) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(ProductService.getProductPage(pageUtil));
    }

    // 추가
    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody tmp_Product Product) {
        if (!StringUtils.hasText(Product.getProductName())
                || !StringUtils.hasText(Product.getProductIntro())
                || !StringUtils.hasText(Product.getTag())
                || Objects.isNull(Product.getOriginalPrice())
                || Objects.isNull(Product.getCategoryId())
                || Objects.isNull(Product.getSellingPrice())
                || Objects.isNull(Product.getStockNum())
                || Objects.isNull(Product.getProductSellStatus())
                || !StringUtils.hasText(Product.getProductCoverImg())
                || !StringUtils.hasText(Product.getProductDetailContent())) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        String result = ProductService.saveProduct(Product);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


 // 업데이트
    @RequestMapping(value = "/product/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody tmp_Product Product) {
        if (Objects.isNull(Product.getProductId())
                || !StringUtils.hasText(Product.getProductName())
                || !StringUtils.hasText(Product.getProductIntro())
                || !StringUtils.hasText(Product.getTag())
                || Objects.isNull(Product.getOriginalPrice())
                || Objects.isNull(Product.getSellingPrice())
                || Objects.isNull(Product.getCategoryId())
                || Objects.isNull(Product.getStockNum())
                || Objects.isNull(Product.getProductSellStatus())
                || !StringUtils.hasText(Product.getProductCoverImg())
                || !StringUtils.hasText(Product.getProductDetailContent())) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        String result = ProductService.updateProduct(Product);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

   // 상세 정보
    @GetMapping("/product/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        tmp_Product product = ProductService.getProductById(id);
        return ResultGenerator.genSuccessResult(product);
    }

    // 진열 상태 일괄 수정
    @RequestMapping(value = "/product/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("상태 이상");
        }
        if (ProductService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("업데이트 실패");
        }
    }

}