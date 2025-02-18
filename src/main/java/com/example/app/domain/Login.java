package com.example.app.domain;

import org.mindrot.jbcrypt.BCrypt;

import com.example.app.validation.LoginGroup;
import com.example.app.validation.RegisterGroup;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Login {
	
	@NotBlank(groups = {RegisterGroup.class, LoginGroup.class})   // 登録、ログイン
	private String loginId;

	@NotBlank(groups = {RegisterGroup.class, LoginGroup.class})   // 登録、ログイン
	private String loginPass;

	public boolean isCorrectPassword(String hashedPassword) {
		return BCrypt.checkpw(loginPass, hashedPassword);
	}

	

}

