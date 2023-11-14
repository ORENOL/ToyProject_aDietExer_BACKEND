package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;


@RestController
public class SecurityController {

	@Autowired
	private MemberService memService;
	
	@GetMapping("login")
	public void loginView() {}

	@PostMapping("/login")
	public void login(Member member) {
		 memService.login(member);
		return;
	}
	
	@GetMapping("/signUp")
	public void registView() {}
	
	@PostMapping("/signUp")
	public void regist(Member member) {
		memService.signUp(member);
		return;
	}
}
