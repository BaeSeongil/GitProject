package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Reply;
@Mapper
public interface ReplyMapper {

	List<Reply> selectBoardNo(int boardNo);

}
