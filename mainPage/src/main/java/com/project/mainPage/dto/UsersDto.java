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
	private String userid;
	private String username;
	private String userpw;
	private String phone;
	private String email;
	@DateTimeFormat(pattern =  "yyyy-MM-dd")
	private Date birth;
	private String address;
	@DateTimeFormat(pattern =  "yyyy-MM-dd'T'HH:mm")
	private Date signup;
	
}
