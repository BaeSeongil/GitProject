package com.project.mainPage.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.mainPage.dto.Notice;
import com.project.mainPage.dto.NoticeImg;
import com.project.mainPage.mapper.NoticeImgMapper;
import com.project.mainPage.mapper.NoticeMapper;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private NoticeImgMapper noticeImgMapper;
	
	@Value("${spring.servlet.multipart.location}")
	String savePath;
	
	public Notice noticeUpdateView(int noticeNo) throws Exception{
		noticeMapper.updateViews(noticeNo);
		return noticeMapper.selectDetailOne(noticeNo);
	}
	
	public int NoticeAndNoticeImg(Notice notice) throws Exception{
		int regist = 0;
		regist = noticeMapper.insertOne(notice);
		int imgRegist = 0;
		if(regist>0 && notice.getNoticeImgs() != null) {
			for (NoticeImg noticeImg : notice.getNoticeImgs()) {
				noticeImg.setNotice_no(notice.getNotice_no());
				imgRegist += noticeImgMapper.insertOne(noticeImg);
			}
		}
		System.out.println("Notice 등록 : " + regist);
		System.out.println("Notice 이미지 등록 : " + imgRegist);
		return regist;
	}
	
	public int removeNotice(int noticeNo) throws Exception{
		int remove = 0;
		List<NoticeImg> noticeImgs = noticeImgMapper.selectNoticeNo(noticeNo);
		if(noticeImgs != null) {
			noticeImgs.stream()
				.map(NoticeImg :: getImg_path) // map :  요소들을 특정조건에 해당하는 값으로 변환
				.forEach((img)->{
					File file = new File(savePath+"/"+img);
					System.out.printf("notice 이미지 삭제"+ file.delete());
				});
		}
		remove = noticeMapper.deleteOne(noticeNo);
		return remove;
	}
	
	
	
	
}


