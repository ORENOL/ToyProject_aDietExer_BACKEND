package edu.pnu.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;


@RestController
@RequestMapping("/api/public")
public class SecurityController {

	@Autowired
	private MemberService memService;
	
	// 테스트용 코드
	@GetMapping("getMember")
	public Member getMember(Member member) {
		return memService.getMember(member);
	}
	
	// 로그인 세션 정보 확인용 URL
	@GetMapping("/auth")
	public @ResponseBody String auth(@AuthenticationPrincipal OAuth2User user, Authentication auth) {
		
		if (user != null) {
			return user.getName();
		}
		
		if (auth != null) {
			return auth.getName();
		}
		return "임시";
	}
	
	// 구글 로그인
	@PostMapping("/oauth")
	public ResponseEntity<?> oauth() {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("http://healthyfit3-env.eba-hmvcyftc.ap-northeast-2.elasticbeanstalk.com/oauth2/authorization/google"));
		return new ResponseEntity<>(headers, HttpStatus.OK);
	}
	
	// 회원가입
	@PostMapping("/signup")
	public void regist(@RequestBody Member member) {
		memService.signUp(member);
		return;
	}
	
	// 회원가입시 중복 아이디 체크
	@PostMapping("/searchDuplicatedName")
	public ResponseEntity<?> searchDuplicatedName(@RequestBody Member member) {
		System.out.println(member);
		return memService.searchDuplicatedName(member);
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public void logout() {}
	
}
