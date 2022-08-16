package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Board;


// com.project.mainPage.mapper.BoardMapper
@Mapper
public interface BoardMapper {
	List<Board> selectPageAll();
	Board selectDetailOneAll(int boardNo);
	int detailUpdateViews(int boardNo);
	
}
