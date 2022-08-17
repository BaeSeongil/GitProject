package com.project.mainPage.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class IdCheck {
	public boolean idCheck=false;
	public UsersDto user=null;
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
}
