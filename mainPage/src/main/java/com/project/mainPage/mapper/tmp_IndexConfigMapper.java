package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_IndexConfig;

@Mapper
public interface tmp_IndexConfigMapper {
    int deleteByPrimaryKey(Long configId);

    int insert(tmp_IndexConfig record);

    int insertSelective(tmp_IndexConfig record);

    tmp_IndexConfig selectByPrimaryKey(Long configId);

    tmp_IndexConfig selectByTypeAndProductId(@Param("configType") int configType, @Param("productId") Long productId);

    int updateByPrimaryKeySelective(tmp_IndexConfig record);

    int updateByPrimaryKey(tmp_IndexConfig record);
    
    // 일괄삭제
    int deleteBatch(Long[] ids);

    List<tmp_IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);
}