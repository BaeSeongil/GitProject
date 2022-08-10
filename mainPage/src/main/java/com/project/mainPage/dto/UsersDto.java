package com.project.mainPage.dto;
/*
+----------+-------------+------+-----+-------------------+-------------------+
| Field    | Type        | Null | Key | Default           | Extra             |
+----------+-------------+------+-----+-------------------+-------------------+
| userid   | varchar(45) | NO   | PRI | NULL              |                   |
| username | varchar(45) | NO   |     | NULL              |                   |
| userpw   | varchar(45) | NO   |     | NULL              |                   |
| phone    | varchar(20) | NO   | UNI | NULL              |                   |
| email    | varchar(45) | NO   | UNI | NULL              |                   |
| birth    | date        | NO   |     | NULL              |                   |
| address  | varchar(45) | NO   |     | NULL              |                   |
| signup   | datetime    | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
+----------+-------------+------+-----+-------------------+-------------------+
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
	private String address; //회원 주소
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date signup; //회원 가입일
	
}

//user 테이블에 추가?
//adminCheck 0 - 일반회원 / 1 - 관리자 default값 0 
//address1 우편번호
//address2 주소
//address3 상세주소 구분 ?
//
//CREATE TABLE USERS(
//  address1 VARCHAR(100) NOT NULL,
//  address2 VARCHAR(100) NOT NULL,
//  address3 VARCHAR(100) NOT NULL,
//  adminCk int NOT NULL ?