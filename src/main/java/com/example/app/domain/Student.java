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
	
	@NotBlank(groups={RegisterGroup.class})
	@Size(max = 30, groups={RegisterGroup.class})
	private String name;
	
	@NonNull
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private LocalDate birthday;
	
	@NotBlank(groups={RegisterGroup.class,LoginGroup.class})
	@Size(max = 30,groups={RegisterGroup.class})
	private String loginId;
	
	@NotBlank(groups={RegisterGroup.class,LoginGroup.class})
	private String loginPass;
	
	private String status;
	
}
