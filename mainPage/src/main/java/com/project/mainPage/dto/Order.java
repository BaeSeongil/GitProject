package com.project.mainPage.dto;
/*
  desc orders;
+------------+--------------+------+-----+-------------------+-------------------+
| Field      | Type         | Null | Key | Default           | Extra             |
+------------+--------------+------+-----+-------------------+-------------------+
| orderid    | int          | NO   | PRI | NULL              | auto_increment    |
| userid     | varchar(255) | NO   | MUL | NULL              |                   |
| productid  | int          | NO   | MUL | NULL              |                   |
| orderdate  | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
| orderPrice | int          | NO   |     | NULL              |                   |
| orderState | varchar(255) | YES  |     | NULL              |                   |
+------------+--------------+------+-----+-------------------+-------------------+
 */

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class Order {
	private int orderid;	// 주문 번호
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderdate;	// 주문 일자
	private int orderPrice; // 주문 총 금액
	private String orderState; //주문 상태 ex) 배송 준비, 배송 중, 배송 완료
	private String orderAdd; //상품을 받을 주소
	private String orderOption; //결제 방법
	
	private UsersDto users; // N:1
	private Product products;
}
