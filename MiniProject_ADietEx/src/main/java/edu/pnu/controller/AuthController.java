package edu.pnu.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@CrossOrigin
	@GetMapping("/index")
	public String index() {
		return "index";
	}
}
