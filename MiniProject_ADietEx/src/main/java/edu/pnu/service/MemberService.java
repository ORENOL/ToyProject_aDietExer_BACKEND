package edu.pnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberService{
	
	@Autowired
	private MemberRepository memRepo;
	
	@Autowired
	private PasswordEncoder encoder;


	public Member getMember(Member member) {
		Optional<Member> mem = memRepo.findById(member.getUsername());
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


	public void signUp(@RequestBody Member member) {
		Member m = new Member();
		m.setUsername(member.getUsername());
		m.setPassword(encoder.encode(member.getPassword()));
		m.setRole(Role.ROLE_MEMBER);
		memRepo.save(m);
		
	}

	public ResponseEntity<?> searchDuplicatedName(@RequestBody Member members) {
		Optional<Member> member = memRepo.findById(members.getUsername());
		System.out.println(member);
		if(member.isPresent()) {
			return ResponseEntity.status(226).build();
		} else {
			return ResponseEntity.ok().build();
		}
	}


	public Member findMember(String name) {
		return memRepo.findById(name).get();
	}
}
