package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.BoardImg;
@Mapper
public interface BoardImgMapper {

	List<BoardImg> selectBoardNo(int boardNo);

	BoardImg selectOne(int no);

	int deleteOne(int no);

	int insertOne(BoardImg boardImg);

}
