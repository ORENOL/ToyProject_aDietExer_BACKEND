package edu.pnu.controller;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;


@Controller
public class SecurityController {

	@Autowired
	private MemberService memService;
	
	// 테스트용 코드
	@GetMapping("getMember")
	public Member getMember(Member member) {
		return memService.getMember(member);
	}
	
//	@GetMapping("/login")
//	public ModelAndView loginView() {
//		ModelAndView mv = new ModelAndView("redirect:http://localhost:3000/main");
//		return mv;
//	}
	
	
	@PostMapping("/login")
	public void login(Member member) {
		return;
	}
	
	@GetMapping("/login")
	public void loginView() {}
	
	
	@PostMapping("/signUp")
	public void regist(Member member) {
		memService.signUp(member);
		return;
	}
	
	@GetMapping("/logout")
	public void logout() {}
	
}
