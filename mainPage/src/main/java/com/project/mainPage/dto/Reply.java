package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/*
 * mysql> desc reply;
+-----------+--------------+------+-----+-------------------+-------------------+
| Field     | Type         | Null | Key | Default           | Extra             |
+-----------+--------------+------+-----+-------------------+-------------------+
| reply_no  | int          | NO   | PRI | NULL              | auto_increment    |
| title     | varchar(255) | NO   |     | NULL              |                   |
| contents  | varchar(255) | YES  |     |                   |                   |
| post_time | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
| img_path  | varchar(255) | YES  |     | NULL              |                   |
| good      | int          | NO   |     | 0                 |                   |
| bad       | int          | NO   |     | 0                 |                   |
| board_no  | int          | NO   | MUL | NULL              |                   |
| userid    | varchar(255) | NO   | MUL | NULL              |                   |
+-----------+--------------+------+-----+-------------------+-------------------+
 * 
 */
@Data
public class Reply {
	private int reply_no;
	private String title;
	private String contents;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date post_time;
	private String img_path;
	private int board_no;
	private int good;
	private int bad;
	private UsersDto users;  // UsersDto userid : fk
}
