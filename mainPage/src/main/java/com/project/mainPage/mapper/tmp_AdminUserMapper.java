package com.project.mainPage.mapper;

import com.project.mainPage.dto.tmp_AdminUser;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface tmp_AdminUserMapper {
    int insert(tmp_AdminUser record);

    int insertSelective(tmp_AdminUser record);

    // 로그인
    tmp_AdminUser login(@Param("userName") String userName, @Param("password") String password);

    tmp_AdminUser selectByPrimaryKey(Integer adminUserId);

    int updateByPrimaryKeySelective(tmp_AdminUser record);

    int updateByPrimaryKey(tmp_AdminUser record);
}