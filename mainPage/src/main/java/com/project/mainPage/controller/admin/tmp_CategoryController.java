package com.project.mainPage.controller.admin;

import java.util.Collections;
import java.util.HashMap;
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
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.common.ShopException;
import com.project.mainPage.dto.tmp_Category;
import com.project.mainPage.service.tmp_CategoryService;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.Result;
import com.project.mainPage.util.ResultGenerator;


@Controller
@RequestMapping("/admin")
public class tmp_CategoryController {

    @Resource
    private tmp_CategoryService CategoryService;

    @GetMapping("/categories")
    public String categoriesPage(HttpServletRequest request, @RequestParam("categoryLevel") Byte categoryLevel, @RequestParam("parentId") Long parentId, @RequestParam("backParentId") Long backParentId) {
        if (categoryLevel == null || categoryLevel < 1 || categoryLevel > 3) {
            ShopException.fail("파라미터가 비정상입니다");
        }
        request.setAttribute("path", "category");
        request.setAttribute("parentId", parentId);
        request.setAttribute("backParentId", backParentId);
        request.setAttribute("categoryLevel", categoryLevel);
        return "admin/list_category";
    }

    // 목록
    @RequestMapping(value = "/categories/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (!StringUtils.hasText((CharSequence) params.get("page")) || !StringUtils.hasText((CharSequence) params.get("limit")) || !StringUtils.hasText((CharSequence) params.get("categoryLevel")) || !StringUtils.hasText((CharSequence) params.get("parentId"))) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(CategoryService.getCategorisPage(pageUtil));
    }

    // 목록
    @RequestMapping(value = "/categories/listForSelect", method = RequestMethod.GET)
    @ResponseBody
    public Result listForSelect(@RequestParam("categoryId") Long categoryId) {
        if (categoryId == null || categoryId < 1) {
            return ResultGenerator.genFailResult("파라미터가 없습니다");
        }
        tmp_Category category = CategoryService.getCategoryById(categoryId);
        // 대분류도 중분류도 아닌 경우 데이터가 반환되지 않습니다.
        if (category == null || category.getCategoryLevel() == CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        Map categoryResult = new HashMap(4);
        if (category.getCategoryLevel() == CategoryLevelEnum.LEVEL_ONE.getLevel()) {
            // 대분류의 경우 현재 대분류의 모든 중분류와 중분류 리스트 내 첫번째 데이터에 존재하는 모든 소분류 리스트 반환
            // 대분류 리스트의 첫번째 데이터의 모든 대분류 리스트 조회
            List<tmp_Category> secondLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), CategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                // 중분류 리스트 내 첫번째 데이터에 존재하는 모든 소분류
                List<tmp_Category> thirdLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), CategoryLevelEnum.LEVEL_THREE.getLevel());
                categoryResult.put("secondLevelCategories", secondLevelCategories);
                categoryResult.put("thirdLevelCategories", thirdLevelCategories);
            }
        }
        if (category.getCategoryLevel() == CategoryLevelEnum.LEVEL_TWO.getLevel()) {
            // 중분류라면
            // 현재 카테고리에 있는 모든 소분류 리스트 반환
            List<tmp_Category> thirdLevelCategories = CategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), CategoryLevelEnum.LEVEL_THREE.getLevel());
            categoryResult.put("thirdLevelCategories", thirdLevelCategories);
        }
        return ResultGenerator.genSuccessResult(categoryResult);
    }

    // 추가
    @RequestMapping(value = "/categories/save", method = RequestMethod.POST)
    public @ResponseBody Result save(@RequestBody tmp_Category Category) {
        if (Objects.isNull(Category.getCategoryLevel())
                || !StringUtils.hasText(Category.getCategoryName())
                || Objects.isNull(Category.getParentId())
                || Objects.isNull(Category.getCategoryRank())) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        String result = CategoryService.saveCategory(Category);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


 // 업데이트
    @RequestMapping(value = "/categories/update", method = RequestMethod.POST)
    public @ResponseBody Result update(@RequestBody tmp_Category Category) {
        if (Objects.isNull(Category.getCategoryId())
                || Objects.isNull(Category.getCategoryLevel())
                || !StringUtils.hasText(Category.getCategoryName())
                || Objects.isNull(Category.getParentId())
                || Objects.isNull(Category.getCategoryRank())) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        String result = CategoryService.updateCategory(Category);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

   // 상세 정보
    @GetMapping("/categories/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        tmp_Category Category = CategoryService.getCategoryById(id);
        if (Category == null) {
            return ResultGenerator.genFailResult("데이터를 찾을 수 없음");
        }
        return ResultGenerator.genSuccessResult(Category);
    }

    // 삭제
    @RequestMapping(value = "/categories/delete", method = RequestMethod.POST)
    public @ResponseBody Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("파라미터가 비정상입니다！");
        }
        if (CategoryService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("삭제하지 못했습니다");
        }
    }


}