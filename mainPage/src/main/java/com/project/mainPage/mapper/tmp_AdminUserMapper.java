package com.project.mainPage.mapper;

import com.project.mainPage.dto.tmp_AdminUser;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface tmp_AdminUserMapper {
    // 등록
    int insert(tmp_AdminUser record);
    // 등록(모든 필드를 삽입하지 않음)
    int insertSelective(tmp_AdminUser record);
    // 로그인
    tmp_AdminUser login(@Param("userName") String userName, @Param("password") String password);
    // Id로 AdminUser 데이터 호출
    tmp_AdminUser selectByPrimaryKey(Integer adminUserId);
    // AdminUser 데이터 업데이트
    int updateByPrimaryKey(tmp_AdminUser record);
    // AdminUser 데이터 업데이트(모든 필드를 삽입하지 않음)
    int updateByPrimaryKeySelective(tmp_AdminUser record);
}