package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RentalController {
	

	@GetMapping({"/", "/rental"})
	public String rental() {
		return "list-rental";
	}

}
