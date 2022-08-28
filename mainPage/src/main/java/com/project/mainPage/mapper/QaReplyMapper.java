package com.project.mainPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.QaReply;

@Mapper
public interface QaReplyMapper {
	int insertOne(QaReply qaReply);
	int deleteOne(int qaReplyNo);
	QaReply selectOne(int qaBoardNo);
}
