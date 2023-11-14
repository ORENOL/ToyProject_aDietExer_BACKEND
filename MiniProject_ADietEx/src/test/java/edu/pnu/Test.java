package edu.pnu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class Test {

	@Autowired
	private MemberRepository memRepo;
	
	@org.junit.jupiter.api.Test
	public void InsertMember() {
		memRepo.save(Member.builder()
				.id("user")
				.password("1234")
				.nickname("사람")
				.build());
	}
}
