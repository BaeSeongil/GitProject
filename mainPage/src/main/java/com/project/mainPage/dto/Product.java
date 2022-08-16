package com.project.mainPage.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/*
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
