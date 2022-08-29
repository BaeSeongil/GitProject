package com.project.mainPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.ReplyPrefer;
@Mapper
public interface ReplyPreferMapper {
	int insertOne(ReplyPrefer replyPrefer);
	int updateOne(ReplyPrefer replyPrefer);
	int deleteOne(ReplyPrefer replyPrefer);
}
