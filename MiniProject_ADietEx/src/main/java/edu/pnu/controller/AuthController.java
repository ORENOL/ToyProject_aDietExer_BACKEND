package edu.pnu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

	@CrossOrigin
	@GetMapping("/index")
	public String index() {
		return "Index";
	}
}
