package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Board;
import com.project.mainPage.dto.Criteria;


// com.project.mainPage.mapper.BoardMapper
@Mapper
public interface BoardMapper {
	List<Board> selectPageAll(int startRow, int pageSize);
	int selectPageAllCount();
	Board selectDetailOneAll(int boardNo);
	int detailUpdateViews(int boardNo);
	int deleteOne(int boardNo);
	int insertOne(Board board);
	int updateOne(Board board);
	
	//검색
	public List<Board> searchBoard(Criteria cri);
	//검색 갯수
	public int boardGetTotal(Criteria cri);
	
	//통합검색용
	public List<Board> searchAllBoard(Criteria cri);
	public int boardAllGetTotal(Criteria cri);
}
