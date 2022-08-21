package com.project.mainPage.dto;

import java.util.List;

import lombok.Data;
/*
 
+--------------+-------------+------+-----+---------+-------+
| Field        | Type        | Null | Key | Default | Extra |
+--------------+-------------+------+-----+---------+-------+
| categoryid   | int         | NO   | PRI | NULL    |       |
| categoryName | varchar(45) | YES  |     | NULL    |       |
| productid    | int         | YES  | MUL | NULL    |       |
+--------------+-------------+------+-----+---------+-------+
  
 */


@Data
public class Category {
	private int categoryId;
	private String categoryName;
	

	private List<Product> products; 
}
