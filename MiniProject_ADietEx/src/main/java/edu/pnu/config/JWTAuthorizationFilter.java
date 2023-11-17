package edu.pnu.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	
	private final MemberRepository memRepo;
	
	// 임시 전역 변수 (개선 필요)
	static String usernames;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		// 클라이언트에게서 접속 요청을 받았을 경우 증명서(토큰)을 검사
		String srcToken = request.getHeader("Authorization");
		if (srcToken == null || !srcToken.startsWith("Bearer ")) {
		chain.doFilter(request, response);
		return;
		}

	String jwtToken = srcToken.replace("Bearer ", "");
	
	// 토큰에서 username 추출
	String username = JWT.require(Algorithm.HMAC256("edu.pnu.jwt")).build().verify(jwtToken).getClaim("username").asString();
	
	usernames = username;
	
	// 증명서에 담긴 username으로 해당하는 권한을 검색
	Optional<Member> opt = memRepo.findById(username);
	
	if(!opt.isPresent()) {
		System.out.println("해당없음");
		chain.doFilter(request, response);
		return;
	}
	
	Member findmember = opt.get();
	
	// DB에서 읽은 사용자 정보로 UserDetails 타입 객체 생성
	User user = new User(findmember.getUsername(), findmember.getPassword(),
			AuthorityUtils.createAuthorityList(findmember.getRole().toString()));
	
	// Authentication 객체 생성 , 사용자명과 권한 정보 보유
	Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

	// 시큐리티 세션에 등록
	SecurityContextHolder.getContext().setAuthentication(auth);
	
	chain.doFilter(request, response);
	}
}
