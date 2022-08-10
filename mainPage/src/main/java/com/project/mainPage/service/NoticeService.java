package com.project.mainPage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mainPage.dto.Notice;
import com.project.mainPage.mapper.NoticeImgMapper;
import com.project.mainPage.mapper.NoticeMapper;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private NoticeImgMapper noticeImgMapper;
	
	public Notice noticeUpdateView(int noticeNo) throws Exception{
		noticeMapper.updateViews(noticeNo);
		return noticeMapper.selectDetailOne(noticeNo);
	}
}
