package com.example.app.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Student {
	
	private Integer id;
	
	@NotBlank
	@Size(max = 30)
	private String name;
	
	@NonNull
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private LocalDate birthday;
	
	@NotBlank
	@Size(max = 30)
	private String loginId;
	
	@NotBlank
	private String loginPass;
	
	private String status;
	
}
