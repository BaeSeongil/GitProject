package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
/*
+----------------+--------------+------+-----+-------------------+-------------------+
| Field          | Type         | Null | Key | Default           | Extra             |
+----------------+--------------+------+-----+-------------------+-------------------+
| qaReplyNo      | int          | NO   | PRI | NULL              | auto_increment    |
| qaReplyTitle   | varchar(255) | NO   |     | NULL              |                   |
| qaReplyContent | text         | YES  |     | NULL              |                   |
| qaReplyDate    | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
| qaBoardNo      | int          | YES  | MUL | NULL              |                   |
+----------------+--------------+------+-----+-------------------+-------------------+
 */

import lombok.Data;
@Data
public class QaReply {
	private int qaReplyNo;
	private String qaReplyTitle;
	private String qaReplyContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date qaReplyDate;
	private int qaBoardNo;
}
