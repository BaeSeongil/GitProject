package com.project.mainPage.dto;

import lombok.Data;
/*
 +-----------+-------------+------+-----+---------+----------------+
| Field     | Type        | Null | Key | Default | Extra          |
+-----------+-------------+------+-----+---------+----------------+
| basket_id | int         | NO   | PRI | NULL    | auto_increment |
| count     | int         | NO   |     | NULL    |                |
| productid | int         | YES  | MUL | NULL    |                |
| userid    | varchar(45) | YES  | MUL | NULL    |                |
+-----------+-------------+------+-----+---------+----------------+
 * 
 * */
@Data
public class ShoppingBasket {
	private int basket_id;		// 장바구니 번호 
	private int count;  		// 상품 개수 
	private int productid; 
	private String userid;  
	// 이미 담겨있는 물건 또 담을 경우 수량 증가
	public void addCount(int count) {  
		this.count += count;
	}
}