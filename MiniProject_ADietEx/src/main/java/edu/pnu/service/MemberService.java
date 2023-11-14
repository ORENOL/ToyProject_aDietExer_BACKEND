package edu.pnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberService{
	
	@Autowired
	private MemberRepository memRepo;


	public Member getMember(Member member) {
		Optional<Member> mem = memRepo.findById(member.getId());
		if (!mem.isPresent())
			return null;
		return mem.get();
	}
	

	public void insertMember(Member member) {
		// TODO Auto-generated method stub
		
	}
	
	public void login(Member member) {
		Member mem = getMember(member);
		System.out.println(mem.toString());
		return;
	}


	public void signUp(Member member) {
		memRepo.save(Member.builder()
				.id(member.getId())
				.password(member.getPassword())
				.email(member.getEmail())
				.nickname(member.getNickname())
				.build());
		
	}
}
