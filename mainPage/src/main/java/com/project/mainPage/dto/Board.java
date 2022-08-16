package com.project.mainPage.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/*
 * mysql> desc board;
+-----------+--------------+------+-----+-------------------+-------------------+
| Field     | Type         | Null | Key | Default           | Extra             |
+-----------+--------------+------+-----+-------------------+-------------------+
| board_no  | int          | NO   | PRI | NULL              | auto_increment    |
| title     | varchar(255) | NO   |     | NULL              |                   |
| contents  | varchar(255) | YES  |     |                   |                   |
| post_time | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
| userid    | varchar(255) | NO   | MUL | NULL              |                   |
| good      | int          | NO   |     | 0                 |                   |
| bad       | int          | NO   |     | 0                 |                   |
| views     | int          | NO   |     | 0                 |                   |
+-----------+--------------+------+-----+-------------------+-------------------+
*/

@Data
public class Board {
	private int board_no;
	private String title;
	private String contents;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date post_time;
	private UsersDto users;  // UsersDto.userid : fk 
	private int good;
	private int bad;
	private int views;
	private List<Reply> replys;  // 1:N Reply.board_no  : fk
	private List<BoardImg> boardImgs; // 1:N  BOARD_IMG.board_no fk
	private int replys_size;
}
