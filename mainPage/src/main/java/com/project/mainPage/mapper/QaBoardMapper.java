package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Criteria;
import com.project.mainPage.dto.QaBoard;
// com.project.mainPage.mapper.QaBoardMapper
@Mapper
public interface QaBoardMapper {
	List<QaBoard> selectPageAll(int startRow, int pageSize);
	int selectPageAllCount();
	QaBoard selectOne(int qaBoardNo);
	int insertOne(QaBoard qaBoard);
	int deleteOne(int qaBoardNo);
	int answerOne(QaBoard qaBoard);
	
	public List<QaBoard> searchQaBoard(Criteria cri);
	public int QaBoardGetTotal(Criteria cri);
}
