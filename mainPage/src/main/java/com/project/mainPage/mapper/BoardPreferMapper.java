package com.project.mainPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.BoardPrefer;

// com.project.mainPage.mapper.BoardPreferMapper
@Mapper
public interface BoardPreferMapper {
	BoardPrefer selectFindUserIdAndBoardNo(String userId, int boardNo);
	int insertOne(BoardPrefer boardPrefer);
	int deleteOne(int boardPreferNo);
	int updateOne(BoardPrefer boardPrefer);
}
