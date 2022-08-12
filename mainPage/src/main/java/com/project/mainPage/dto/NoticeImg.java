package com.project.mainPage.dto;

import lombok.Data;
/*
 * mysql> desc NOTICE_IMG;
+---------------+--------------+------+-----+---------+----------------+
| Field         | Type         | Null | Key | Default | Extra          |
+---------------+--------------+------+-----+---------+----------------+
| notice_img_no | int          | NO   | PRI | NULL    | auto_increment |
| notice_no     | int          | NO   | MUL | NULL    |                |
| img_path      | varchar(255) | NO   |     | NULL    |                |
+---------------+--------------+------+-----+---------+----------------+
 * */
@Data
public class NoticeImg {
	private int notice_img_no;
	private int notice_no;
	private String img_path;
}
