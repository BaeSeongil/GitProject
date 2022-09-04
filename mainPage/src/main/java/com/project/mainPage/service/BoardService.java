package com.project.mainPage.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.mainPage.dto.Board;
import com.project.mainPage.mapper.BoardMapper;


import java.io.File;
import java.util.List;


import org.springframework.transaction.annotation.Transactional;

import com.project.mainPage.dto.BoardImg;
import com.project.mainPage.mapper.BoardImgMapper;

@Service
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;

	@Autowired
	private BoardImgMapper boardImgMapper;
	
	@Value("${spring.servlet.multipart.location}")
	String savePath;
	
	
	public Board boardUpdateView(int boardNo, String userId) throws Exception{
		boardMapper.detailUpdateViews(boardNo);
		return boardMapper.selectOne(boardNo, userId);
	}
		
	public int removeBorad(int boardNo) throws Exception{
		int remove=0;
		//보드를 참조하는 리플의 이미지를 삭제하기위해 리플 리스트를 검색 
//		List<Reply> replies=replyMapper.selectBoardNo(boardNo);
//		if(replies!=null) {
//			replies.stream()
//				.filter((r)->(r.getImg_path()!=null))
//				.map(Reply::getImg_path).
//				forEach((img)->{
//					File f=new File(savePath+"/"+img);
//					System.out.println("리플 이미지 삭제:"+f.delete());
//				});
//		}
		//Board를 참조하는 Board_img도 삭제
		List<BoardImg> boardImgs=boardImgMapper.selectBoardNo(boardNo);
		if(boardImgs!=null) {
			boardImgs.stream()
				.map(BoardImg::getImg_path)
				.forEach((img)->{
					File f=new File(savePath+"/"+img);
					System.out.println("보드 이미지 삭제:"+f.delete());
				});
		}
		remove=boardMapper.deleteOne(boardNo);
		return remove;
	}
	
	
	public int registBoard(Board board) throws Exception {
		int regist=0;
		// useGeneratedKeys="true" keyProperty="board_no"  :  등록한 pk 를 board에 저장함
		regist=boardMapper.insertOne(board);
		int imgRegist=0;
		for(BoardImg boardImg : board.getBoardImgs()) {
			System.out.println(boardImg);
		}
		if(regist>0 && board.getBoardImgs()!=null) {
			for(BoardImg boardImg : board.getBoardImgs()) {
				boardImg.setBoard_no(board.getBoard_no()); //Auto Increment 로 저장된 대표키 값
				imgRegist+=boardImgMapper.insertOne(boardImg);
			}
		}
		System.out.println("보드 등록 :"+regist);
		System.out.println("보드 이미지 등록 :"+imgRegist);
		return regist;
	}
	
	//@Transactional : 함수 내부의 db 실행을 한 트랙젝션으로 보고 중간에 실패시 db 실행을 취소 (roll back);
	@Transactional
	public int modifyBoardRemoveBoardImg(Board board,int[] boardImgNos) throws Exception{
		int modify=0;
		if(boardImgNos!=null) { //선택한 삭제될 board_img.board_img_no
			for(int no : boardImgNos) {
				BoardImg boardImg=boardImgMapper.selectOne(no);
				File f=new File(savePath+"/"+boardImg.getImg_path());
				System.out.println("board의 이미지 파일 삭제: "+f.delete());
				int removeBoardImg=boardImgMapper.deleteOne(no);
				System.out.println("board의 Board_img 삭제: "+removeBoardImg);
			}			
		}
		if(board.getBoardImgs()!=null) { //이미지가 1개 이상 저장되면 null 이 아니다.
			for(BoardImg boardImg : board.getBoardImgs()) {
				int registBoardImg=boardImgMapper.insertOne(boardImg);
				System.out.println("board의 Board_img 등록 :"+registBoardImg);
			}
		}
		modify=boardMapper.updateOne(board);
		System.out.println(modify);
		return modify;
	}
	
}
