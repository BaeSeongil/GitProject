package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Board;


// com.project.mainPage.mapper.BoardMapper
@Mapper
public interface BoardMapper {
	List<Board> selectPageAll();

	int deleteOne(int boardNo);

	int insertOne(Board board);

	void updateViews(int boardNo);

	Board selectOne(int boardNo);

	int updateOne(Board board);
}
