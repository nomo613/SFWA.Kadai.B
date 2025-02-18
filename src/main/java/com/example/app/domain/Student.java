package com.example.app.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.app.validation.LoginGroup;
import com.example.app.validation.RegisterGroup;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Student {
	
	private Integer id;
	
	@NotBlank(groups = {RegisterGroup.class})                    // 登録
	@Size(max = 30, groups = {RegisterGroup.class})              // 登録
	private String name;
	
	@NonNull
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private LocalDate birthday;
	
	@NotBlank(groups = {RegisterGroup.class, LoginGroup.class})   // 登録、ログイン
	@Size(max = 30,groups={RegisterGroup.class})               // 登録
	private String loginId;
	
	@NotBlank(groups = {RegisterGroup.class, LoginGroup.class})   // 登録、ログイン
	@Size(max = 30, groups = {RegisterGroup.class})               // 登録
	private String loginPass;
	
	private String status;
	
}
