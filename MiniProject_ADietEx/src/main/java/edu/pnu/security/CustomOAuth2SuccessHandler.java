package edu.pnu.security;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		System.out.println(authentication.getPrincipal());
		
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String UserName = oAuth2User.getAttribute("name");
		
		// JWT 토큰 발행
		String token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*10*100))
				.withClaim("username", UserName)
				.sign(Algorithm.HMAC256("jwt_edu_temp"));
		
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("username", UserName);

		Cookie jwtCookie = new Cookie("JWT", token);
		jwtCookie.setHttpOnly(true);
		jwtCookie.setSecure(true); // HTTPS 사용 시
		jwtCookie.setPath("/");
		response.addCookie(jwtCookie);
		response.sendRedirect("http://localhost:3000/login?jwt="+"Bearer " + token + "&username=" + UserName);
	}	

}
