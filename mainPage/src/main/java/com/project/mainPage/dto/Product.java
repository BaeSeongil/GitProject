package com.project.mainPage.dto;

import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/*
<<<<<<< HEAD
=======
Product
>>>>>>> 26b73debe604b41fc4c83ec15f5d662d492d962d
>>>>>>> 70ebc118004c54c816a290a61334f48107ac1e5e
+--------------+-------------+------+-----+---------+-------+
| Field        | Type        | Null | Key | Default | Extra |
+--------------+-------------+------+-----+---------+-------+
| productid    | int         | NO   | PRI | NULL    |       |
| productName  | varchar(45) | NO   |     | NULL    |       |
| productSize  | varchar(45) | NO   |     | NULL    |       |
| productColor | varchar(45) | NO   |     | NULL    |       |
| productGroup | varchar(45) | NO   |     | NULL    |       |
| productInfo  | text        | YES  |     | NULL    |       |
| productStock | int         | YES  |     | NULL    |       |
| productDate  | date        | YES  |     | NULL    |       |
| price        | int         | NO   |     | NULL    |       |
| categoryid   | int         | YES  | MUL | NULL    |       |
+--------------+-------------+------+-----+---------+-------+
<<<<<<< HEAD
*/

@Data
public class Product {
	private int productid;
	private String productName;
	private String productSize;
	private String productColor;
	private String productGroup;
	private String productInfo;
	private int productStock;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date productDate;
	private int price;
	
	private Category cate;


}
