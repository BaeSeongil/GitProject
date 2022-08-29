package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_Category;
import com.project.mainPage.util.PageQueryUtil;

@Mapper
public interface tmp_CategoryMapper {
    int deleteByPrimaryKey(Long categoryId);

    int insert(tmp_Category record);

    int insertSelective(tmp_Category record);

    tmp_Category selectByPrimaryKey(Long categoryId);

    tmp_Category selectByLevelAndName(@Param("categoryLevel") Byte categoryLevel, @Param("categoryName") String categoryName);

    int updateByPrimaryKeySelective(tmp_Category record);

    int updateByPrimaryKey(tmp_Category record);

    // 일괄삭제
    int deleteBatch(Integer[] ids);

    List<tmp_Category> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);

    int getTotalProductCategories(PageQueryUtil pageUtil);

    List<tmp_Category> findCategoryList(PageQueryUtil pageUtil);
}