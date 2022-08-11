package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.Notice;

// com.project.mainPage.mapper.NoticeMapper
@Mapper
public interface NoticeMapper {
	List<Notice> selectPageAll();
	Notice selectDetailOne(int noticeNo);
	int updateViews(int noticeNo);
	int insertOne(Notice notice);
}
