package com.project.mainPage.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/*
+-----------------+--------------+------+-----+-------------------+-------------------+
| Field           | Type         | Null | Key | Default           | Extra             |
+-----------------+--------------+------+-----+-------------------+-------------------+
| qaBoardNo       | int          | NO   | PRI | NULL              | auto_increment    |
| qaBoardKind     | varchar(255) | NO   |     | NULL              |                   |
| qaBoardTitle    | varchar(255) | NO   |     | NULL              |                   |
| qaBoardContents | text         | YES  |     | NULL              |                   |
| qaBoardAnswer   | tinyint(1)   | YES  |     | 0                 |                   |
| qaBoardDate     | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
| userid          | varchar(45)  | YES  | UNI | NULL              |                   |
| productid       | int          | YES  | MUL | NULL              |                   |
+-----------------+--------------+------+-----+-------------------+-------------------+
*/
@Data
public class QaBoard {
	private int qaBoardNo;
	private int qaBoardAnswer;
	private String qaBoardKind;
	private String qaBoardTitle;
	private String qaBoardContents;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date qaBoardDate;
	
	private UsersDto users;  // fk 
	private Product product; // fk 
	private QaReply qaReply;
}
