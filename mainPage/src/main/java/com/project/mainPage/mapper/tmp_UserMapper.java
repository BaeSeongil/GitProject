package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.mainPage.dto.tmp_User;
import com.project.mainPage.util.PageQueryUtil;

@Mapper
public interface tmp_UserMapper {
    // 삭제
    int deleteByPrimaryKey(Long userId);
    // 등록
    int insert(tmp_User record);
    // 등록
    int insertSelective(tmp_User record);
    // 호출
    tmp_User selectByPrimaryKey(Long userId);
    // ID로 호출
    tmp_User selectByLoginName(String loginName);
    // ID와 Password로 호출
    tmp_User selectByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);
    // 업데이트
    int updateByPrimaryKeySelective(tmp_User record);
    // 업데이트
    int updateByPrimaryKey(tmp_User record);
    // 사용자 일괄 차단
    int lockUserBatch(@Param("ids") Integer[] ids, @Param("lockStatus") int lockStatus);
    List<tmp_User> findUserList(PageQueryUtil pageUtil);
    int getTotalUsers(PageQueryUtil pageUtil);
}