package com.project.mainPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.NoticeImg;


// com.project.mainPage.mapper.NoticeImgMapper
@Mapper
public interface NoticeImgMapper {
	int insertOne(NoticeImg noticeImg);
}
