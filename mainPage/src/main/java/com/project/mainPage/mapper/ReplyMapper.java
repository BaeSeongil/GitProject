package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Reply;
@Mapper
public interface ReplyMapper {
	Reply selectOne(int replyNo);

	List<Reply> selectBoardNo(int boardNo);

	int insertOne(Reply reply);

	int updateOne(Reply reply);

	int selectBoardNoCount(int boardNo);

	List<Reply> selectBoardNoPage(int boardNo,int startRow,int pageSize);
	List<Reply> selectBoardNoPage(int boardNo, int startRow, int pageSize, String loginUsersId);

}
