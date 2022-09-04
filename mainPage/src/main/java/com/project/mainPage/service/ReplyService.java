package com.project.mainPage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mainPage.dto.Reply;
import com.project.mainPage.mapper.ReplyMapper;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyMapper replyMapper;
	
	
	public int registReply(Reply reply) throws Exception {
		int regist=0;
		// useGeneratedKeys="true" keyProperty="board_no"  :  등록한 pk 를 board에 저장함
		regist=replyMapper.insertOne(reply);
		System.out.println("댓글 등록 :"+regist);
		return regist;
	}
}
