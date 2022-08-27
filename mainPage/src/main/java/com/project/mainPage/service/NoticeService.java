package com.project.mainPage.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	//@Transactional : 함수 내부의 db 실행을 한 트랙젝션으로 보고 중간에 실패시 db 실행을 취소 (roll back);
		@Transactional
		public int modifyBoardRemoveBoardImg(Notice notice,int[] noticeImgNos) throws Exception{
			int modify=0;
			if(noticeImgNos!=null) { //선택한 삭제될 notice_img.notice_img_no
				for(int noticeNo : noticeImgNos) {
					NoticeImg noticeImg= noticeImgMapper.selectOne(noticeNo);
					File f=new File(savePath+"/"+ noticeImg.getImg_path());
					System.out.println("notice의 이미지 파일 삭제: "+f.delete());
					int removeNoticeImg=noticeImgMapper.deleteOne(noticeNo);
					System.out.println("notice의 notice_img 삭제: "+removeNoticeImg);
				}			
			}
			if(notice.getNoticeImgs()!=null) { //이미지가 1개 이상 저장되면 null 이 아니다.
				for(NoticeImg noticeImg : notice.getNoticeImgs()) {
					int registNotieceImg=noticeImgMapper.insertOne(noticeImg);
					System.out.println("notice의 notice_img 등록 :"+registNotieceImg);
				}
			}
			modify=noticeMapper.updateOne(notice);
			return modify;
		}
	
	
}


