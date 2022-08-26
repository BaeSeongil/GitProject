package com.project.mainPage.dto;

import lombok.Data;

/*
+----------------+--------------+------+-----+---------+----------------+
| Field          | Type         | Null | Key | Default | Extra          |
+----------------+--------------+------+-----+---------+----------------+
| product_img_no | int          | NO   | PRI | NULL    | auto_increment |
| productid      | int          | NO   | MUL | NULL    |                |
| img_path       | varchar(255) | NO   |     | NULL    |                |
+----------------+--------------+------+-----+---------+----------------+
 */

@Data
public class ProductImg {

	private int product_img_no;
	private int productid;     
	private String img_path;     
	
}
