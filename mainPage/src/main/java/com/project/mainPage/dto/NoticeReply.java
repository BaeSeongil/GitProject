package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class NoticeReply {
	private int noticeReply_no;
	private String title;
	private String contents;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date post_time;
	private String img_path;
	private int good;
	private int bad;
	private int notice_no;
	private UsersDto uesrs;
}
