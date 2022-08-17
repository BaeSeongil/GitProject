package com.project.mainPage.dto;
/*
+----------+--------------+------+-----+-------------------+-------------------+
| Field    | Type         | Null | Key | Default           | Extra             |
+----------+--------------+------+-----+-------------------+-------------------+
| userid   | varchar(45)  | NO   | PRI | NULL              |                   |
| username | varchar(45)  | NO   |     | NULL              |                   |
| userpw   | varchar(45)  | NO   |     | NULL              |                   |
| phone    | varchar(20)  | NO   | UNI | NULL              |                   |
| email    | varchar(45)  | NO   | UNI | NULL              |                   |
| birth    | date         | NO   |     | NULL              |                   |
| add1     | varchar(100) | NO   |     | NULL              |                   |
| add2     | varchar(100) | NO   |     | NULL              |                   |
| add3     | varchar(100) | YES  |     | NULL              |                   |
| adminCk  | int          | YES  |     | 0                 |                   |
| signup   | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
+----------+--------------+------+-----+-------------------+-------------------+
*/

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class UsersDto {
	private String userid; //회원아이디
	private String username; //회원이름
	private String userpw; //회원패스워드
	private String phone; //회원 전화번호
 	private String email; //회원 이메일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth; //회원 생일
	private int adminCk; //관리자 체크
	private String add1; //회원 주소(우편번호)
	private String add2; //회원 주소(주소)
	private String add3; //회원 주소(상세주소)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date signup; //회원 가입일
	
}

