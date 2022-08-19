package com.project.mainPage.dto;

import java.util.Date;
import java.util.List;

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
	private String title;   // 댓글 제목
	private String contents;  // 댓글 내용 
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date post_time;   // 댓글 등록일 
	private String img_path;  // 댓글 이미지 
	private int board_no;
	private int good;
	private int bad;
	private UsersDto users;  // UsersDto userid : fk

	private Boolean prefer_active = null; // null : 누른적이 없는 , true : good를 누른것 , false: bad를 누른것 
	private List<ReplyPrefer> good_prefers;
	private List<ReplyPrefer> bad_prefers;
}
