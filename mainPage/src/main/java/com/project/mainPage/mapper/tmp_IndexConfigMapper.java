package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_IndexConfig;
import com.project.mainPage.util.PageQueryUtil;

@Mapper
public interface tmp_IndexConfigMapper {
    // ID를 통해 삭제
    int deleteByPrimaryKey(Long configId);
    // 인덱스 등록
    int insert(tmp_IndexConfig record);
    // 인덱스 등록
    int insertSelective(tmp_IndexConfig record);
    // 조회
    tmp_IndexConfig selectByPrimaryKey(Long configId);
    // type과 제품 id로 조회
    tmp_IndexConfig selectByTypeAndProductId(@Param("configType") int configType, @Param("productId") Long productId);
    // 업데이트
    int updateByPrimaryKeySelective(tmp_IndexConfig record);
    int updateByPrimaryKey(tmp_IndexConfig record);
    // 일괄삭제
    int deleteBatch(Long[] ids);
    // 조회
    List<tmp_IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);
    List<tmp_IndexConfig> findIndexConfigList(PageQueryUtil pageUtil);
    int getTotalndexConfigs(PageQueryUtil pageUtil);
}