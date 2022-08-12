package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.UsersDto;

@Mapper
public interface UsersMapper {
	List<UsersDto> selectPageAll(int startRow, int pageSize);
	int selectPageAllCount();
	UsersDto selectIdPwOne(String userId, String userPw);
	UsersDto selectOne(String userId);
	int deleteOne(String userId);
	int updateOne(UsersDto user);
	int insertOne(UsersDto user);
	
	
}
