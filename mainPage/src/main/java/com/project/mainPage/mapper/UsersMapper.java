package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.UsersDto;

@Mapper
public interface UsersMapper {
	List<UsersDto> selectPageAll(int startRow, int pageSize);
	int selectPageAllCount();
	UsersDto selectIdPwOne(String userId, String userPw);
	UsersDto selectOne(String userId); //아이디 중복검사
	int deleteOne(String userId);
	int updateOne(UsersDto user);
	int insertOne(UsersDto user);
	
	//검색
	public List<UsersDto> searchUsers(Criteria cri);
	//검색 갯수
	public int usersGetTotal(Criteria cri);
	
	public List<UsersDto> selectSearchAll(Criteria cri);
}
