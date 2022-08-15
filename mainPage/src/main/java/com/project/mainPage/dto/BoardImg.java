package com.project.mainPage.dto;

import lombok.Data;

/*
 *  desc board_img;
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| board_img_no | int          | NO   | PRI | NULL    | auto_increment |
| board_no     | int          | NO   | MUL | NULL    |                |
| img_path     | varchar(255) | NO   |     | NULL    |                |
+--------------+--------------+------+-----+---------+----------------+
 * */
@Data
public class BoardImg {	
	private int board_img_no;
	private int board_no;
	private String img_path;
}
