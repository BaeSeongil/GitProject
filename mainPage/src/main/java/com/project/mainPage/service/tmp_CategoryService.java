package com.project.mainPage.service;

import com.project.mainPage.controller.vo.CategoryVO;
import com.project.mainPage.controller.vo.SearchPageCategoryVO;
import com.project.mainPage.dto.tmp_Category;
import com.project.mainPage.util.PageQueryUtil;
import com.project.mainPage.util.PageResult;

import java.util.List;

public interface tmp_CategoryService {
    // 백엔드
    // 카테고리 페이지 형식으로 호출
    PageResult getCategorisPage(PageQueryUtil pageUtil);
    // 카테고리 업로드
    String saveCategory(tmp_Category Category);
    // 카테고리 업데이트
    String updateCategory(tmp_Category Category);
    // Id로 카테고리 정보 가져오기
    tmp_Category getCategoryById(Long id);
    // 일괄삭제
    Boolean deleteBatch(Integer[] ids);

    // 카테고리 데이터 반환 (홈페이지 호출)
    List<CategoryVO> getCategoriesForIndex();
    // 카테고리 데이터 반환 (검색 페이지 호출)
    SearchPageCategoryVO getCategoriesForSearch(Long categoryId);
    // parentId와 level을 기준으로 카테고리 목록 가져오기
    List<tmp_Category> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);
}
