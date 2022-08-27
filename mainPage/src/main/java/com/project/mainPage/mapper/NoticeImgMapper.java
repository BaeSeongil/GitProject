package com.project.mainPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.mainPage.dto.NoticeImg;


// com.project.mainPage.mapper.NoticeImgMapper
@Mapper
public interface NoticeImgMapper {
	int insertOne(NoticeImg noticeImg);
	List<NoticeImg> selectNoticeNo(int noticeNo);
	int selectCountNoticeNo(int noticeNo);
	NoticeImg selectOne(int noticeImgNo);
	int deleteOne(int noticeImgNo);
}
