package edu.pnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;

@Service
public class OAuth2userDetailsService extends DefaultOAuth2UserService {
	
	@Autowired
	private MemberRepository memRepo;
	
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	OAuth2User ouser = super.loadUser(userRequest);
	
	// 자동 회원가입
	String name = ouser.getAttribute("name");
	Optional<Member> member = memRepo.findById(name);
	if (!member.isPresent()) {
		memRepo.save(Member.builder()
				.username(name)
				.role(Role.ROLE_MEMBER)
				.email(ouser.getAttribute("email").toString())
				.build());
	}
	return ouser;
	}
	
}
