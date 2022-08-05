package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.UsersDto;

@Mapper
public interface UsersMapper {
	List<UsersDto> selectAll(int page);
}
