package edu.pnu.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;


@RestController
public class SecurityController {

	@Autowired
	private MemberService memService;
	
	// 테스트용 코드
	@GetMapping("getMember")
	public Member getMember(Member member) {
		return memService.getMember(member);
	}
		
	@GetMapping("/test")
	public String test(HttpServletResponse resp) {
		resp.addHeader("Authorization", "ABCD");
		return "하이";
	}
	
	// 로그인 세션 정보 확인용 URL
	@GetMapping("/auth")
	public @ResponseBody String auth(@AuthenticationPrincipal OAuth2User user) {
		if (user == null) {
			return "user is Null";
		}
		return user.toString();
	}
	
	@PostMapping("/oauth")
	public ResponseEntity<?> oauth() {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/login/oauth2/code/google"));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@GetMapping("/oauth")
	public ResponseEntity<?> oauths() {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/login/oauth2/code/google"));
		headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		headers.add("Access-Control-Allow-Headers", "Authorization, Content-Type");
		headers.add("Access-Control-Allow-Credentials", "true");
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
	
//    @Autowired
//    private OAuth2Service oAuth2Service; // OAuth2Service는 실제 OAuth2 플로우를 처리하는 서비스 클래스
//
//    @GetMapping("/auth/google")
//    public OAuth2Response getGoogleAuthUrl() {
//        // 1. 프론트엔드에게 구글 로그인 요청을 보냄
//        String authUrl = oAuth2Service.getGoogleAuthUrl();
//        return new OAuth2Response(authUrl);
//    }
	
	@PostMapping("/signUp")
	public void regist(Member member) {
		memService.signUp(member);
		return;
	}
	
	@GetMapping("/logout")
	public void logout() {}
	
}
