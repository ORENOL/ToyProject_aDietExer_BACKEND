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
	
	// 테스트용 코드
	@GetMapping("getMember")
	public Member getMember(Member member) {
		return memService.getMember(member);
	}
	
	@PostMapping("/login")
	public void login(Member member) {
		 memService.login(member);
		return;
	}
	
	@PostMapping("/signUp")
	public void regist(Member member) {
		memService.signUp(member);
		return;
	}
	
	@GetMapping("/logout")
	public void logout() {}
	
}
