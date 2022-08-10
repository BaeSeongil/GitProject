package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Notice {
	private int notice_no;
	private String title;
	private String contents;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date post_time;
	private UsersDto users;
	private int good;
	private int bad;
	private int views;
}
