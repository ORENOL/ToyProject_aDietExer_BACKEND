package edu.pnu.config;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authManager;
	
//	@Value("${jwt.secret}") 
//	private String jwt_secret;
	
		public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		ObjectMapper mapper = new ObjectMapper();
		Member member = null;
	
		try {
			// 요청을 읽고 파라미터를 멤버클래스에 맞게 등록
			member = mapper.readValue(request.getInputStream(), Member.class);
			System.out.println(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 등록된 정보로 인증절차를 수행할 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
		
		if (authManager == null) {
			System.out.println("auth 없음");
		}
		
		// 인증 절차 수행
		Authentication auth = authManager.authenticate(authToken);
		System.out.println("auth: " + auth);
		return auth;
	}
	
	// 인증이 성공한 경우
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		// 인증 성공한 객체를 유저로 등록
		User user = (User) authResult.getPrincipal();
		// 인증이 성공했음을 증명할 증명서(토큰)를 제작
		String token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*10*100))
				.withClaim("username", user.getUsername())
				.sign(Algorithm.HMAC256("jwt_edu_temp"));
		
		// 증명서(토큰)을 헤더에 담아 클라이언트에게 전달
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//		response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Access-Control-Expose-Headers", "username");
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("username", authResult.getName());
	}


}