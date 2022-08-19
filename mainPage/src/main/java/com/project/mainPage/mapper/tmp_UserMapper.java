package com.project.mainPage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_User;

@Mapper
public interface tmp_UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(tmp_User record);

    int insertSelective(tmp_User record);

    tmp_User selectByPrimaryKey(Long userId);

    tmp_User selectByLoginName(String loginName);

    tmp_User selectByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);

    int updateByPrimaryKeySelective(tmp_User record);

    int updateByPrimaryKey(tmp_User record);

    int lockUserBatch(@Param("ids") Integer[] ids, @Param("lockStatus") int lockStatus);
}