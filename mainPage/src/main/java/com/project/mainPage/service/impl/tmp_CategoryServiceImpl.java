package com.project.mainPage.service.impl;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.mainPage.common.CategoryLevelEnum;
import com.project.mainPage.common.Constants;
import com.project.mainPage.common.ServiceResultEnum;
import com.project.mainPage.controller.vo.CategoryVO;
import com.project.mainPage.controller.vo.SearchPageCategoryVO;
import com.project.mainPage.controller.vo.SecondLevelCategoryVO;
import com.project.mainPage.controller.vo.ThirdLevelCategoryVO;
import com.project.mainPage.dto.tmp_Category;
import com.project.mainPage.mapper.tmp_CategoryMapper;
import com.project.mainPage.service.tmp_CategoryService;
import com.project.mainPage.util.BeanUtil;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.PageResult;

@Service
public class tmp_CategoryServiceImpl implements tmp_CategoryService {

    @Autowired
    private tmp_CategoryMapper CategoryMapper;

    @Override
    public PageResult getCategorisPage(PageQueryUtil pageUtil) {
        List<tmp_Category> productCategories = CategoryMapper.findCategoryList(pageUtil);
        int total = CategoryMapper.getTotalProductCategories(pageUtil);
        PageResult pageResult = new PageResult(productCategories, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveCategory(tmp_Category Category) {
        tmp_Category temp = CategoryMapper.selectByLevelAndName(Category.getCategoryLevel(), Category.getCategoryName());
        if (temp != null) {
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
        if (CategoryMapper.insertSelective(Category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCategory(tmp_Category Category) {
        tmp_Category temp = CategoryMapper.selectByPrimaryKey(Category.getCategoryId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        tmp_Category temp2 = CategoryMapper.selectByLevelAndName(Category.getCategoryLevel(), Category.getCategoryName());
        if (temp2 != null && !temp2.getCategoryId().equals(Category.getCategoryId())) {
            // 같은 이름과 다른 ID는 계속 수정할 수 없습니다.
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
        Category.setUpdateTime(new Date());
        if (CategoryMapper.updateByPrimaryKeySelective(Category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public tmp_Category getCategoryById(Long id) {
        return CategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        // 카테고리 삭제
        return CategoryMapper.deleteBatch(ids) > 0;
    }

    @Override
    public List<CategoryVO> getCategoriesForIndex() {
        List<CategoryVO> CategoryVOS = new ArrayList<>();
        // 대분류에 대한 고정된 양의 데이터 불러오기
        List<tmp_Category> firstLevelCategories = CategoryMapper.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), CategoryLevelEnum.LEVEL_ONE.getLevel(), Constants.INDEX_CATEGORY_NUMBER);
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            List<Long> firstLevelCategoryIds = firstLevelCategories.stream().map(tmp_Category::getCategoryId).collect(Collectors.toList());
            // 중분류에 대한 데이터 불러오기
            List<tmp_Category> secondLevelCategories = CategoryMapper.selectByLevelAndParentIdsAndNumber(firstLevelCategoryIds, CategoryLevelEnum.LEVEL_TWO.getLevel(), 0);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                List<Long> secondLevelCategoryIds = secondLevelCategories.stream().map(tmp_Category::getCategoryId).collect(Collectors.toList());
                // 소분류에 대한 데이터 불러오기
                List<tmp_Category> thirdLevelCategories = CategoryMapper.selectByLevelAndParentIdsAndNumber(secondLevelCategoryIds, CategoryLevelEnum.LEVEL_THREE.getLevel(), 0);
                if (!CollectionUtils.isEmpty(thirdLevelCategories)) {
                    // parentId를 기반으로 하는 thirdLevelCategories 그룹화
                    Map<Long, List<tmp_Category>> thirdLevelCategoryMap = thirdLevelCategories.stream().collect(groupingBy(tmp_Category::getParentId));
                    List<SecondLevelCategoryVO> secondLevelCategoryVOS = new ArrayList<>();
                    // 소분류 처리
                    for (tmp_Category secondLevelCategory : secondLevelCategories) {
                        SecondLevelCategoryVO secondLevelCategoryVO = new SecondLevelCategoryVO();
                        BeanUtil.copyProperties(secondLevelCategory, secondLevelCategoryVO);
                        // 중분류에 데이터가 있으면
                        // secondLevelCategoryVOS 객체에 넣습니다.
                        if (thirdLevelCategoryMap.containsKey(secondLevelCategory.getCategoryId())) {
                            // 소분류의 id에 따라 thirdLevelCategoryMap 패킷의 상세분류 list를 추출
                            List<tmp_Category> tempProductCategories = thirdLevelCategoryMap.get(secondLevelCategory.getCategoryId());
                            secondLevelCategoryVO.setThirdLevelCategoryVOS((BeanUtil.copyList(tempProductCategories, ThirdLevelCategoryVO.class)));
                            secondLevelCategoryVOS.add(secondLevelCategoryVO);
                        }
                    }
                    // 중분류 처리
                    if (!CollectionUtils.isEmpty(secondLevelCategoryVOS)) {
                        // parentId를 기반으로 하는 thirdLevelCategories 그룹화
                        Map<Long, List<SecondLevelCategoryVO>> secondLevelCategoryVOMap = secondLevelCategoryVOS.stream().collect(groupingBy(SecondLevelCategoryVO::getParentId));
                        for (tmp_Category firstCategory : firstLevelCategories) {
                            CategoryVO CategoryVO = new CategoryVO();
                            BeanUtil.copyProperties(firstCategory, CategoryVO);
                            //대분류 카테고리에 데이터가 있으면 CategoryVOS 개체에 넣습니다.
                            if (secondLevelCategoryVOMap.containsKey(firstCategory.getCategoryId())) {
                                // 중분류의 id에 따라 secondLevelCategoryVOMap 패킷의 소분류 list를 추출
                                List<SecondLevelCategoryVO> tempProductCategories = secondLevelCategoryVOMap.get(firstCategory.getCategoryId());
                                CategoryVO.setSecondLevelCategoryVOS(tempProductCategories);
                                CategoryVOS.add(CategoryVO);
                            }
                        }
                    }
                }
            }
            return CategoryVOS;
        } else {
            return null;
        }
    }

    @Override
    public SearchPageCategoryVO getCategoriesForSearch(Long categoryId) {
        SearchPageCategoryVO searchPageCategoryVO = new SearchPageCategoryVO();
        tmp_Category thirdLevelCategory = CategoryMapper.selectByPrimaryKey(categoryId);
        if (thirdLevelCategory != null && thirdLevelCategory.getCategoryLevel() == CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            // 현재 소분류의 중분류를 불러오기
            tmp_Category secondLevelCategory = CategoryMapper.selectByPrimaryKey(thirdLevelCategory.getParentId());
            if (secondLevelCategory != null && secondLevelCategory.getCategoryLevel() == CategoryLevelEnum.LEVEL_TWO.getLevel()) {
                // 현재 중분류의 소분류 리스트를 불러오기
                List<tmp_Category> thirdLevelCategories = CategoryMapper.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategory.getCategoryId()), CategoryLevelEnum.LEVEL_THREE.getLevel(), Constants.SEARCH_CATEGORY_NUMBER);
                searchPageCategoryVO.setCurrentCategoryName(thirdLevelCategory.getCategoryName());
                searchPageCategoryVO.setSecondLevelCategoryName(secondLevelCategory.getCategoryName());
                searchPageCategoryVO.setThirdLevelCategoryList(thirdLevelCategories);
                return searchPageCategoryVO;
            }
        }
        return null;
    }

    @Override
    public List<tmp_Category> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel) {
        return CategoryMapper.selectByLevelAndParentIdsAndNumber(parentIds, categoryLevel, 0);  // 0은 전체
    }
}
