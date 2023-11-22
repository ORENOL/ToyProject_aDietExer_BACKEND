package edu.pnu.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.HealthInformation;
import edu.pnu.service.HealthInformationService;

@RestController
@RequestMapping("/health")
public class HealthinformationController {

	@Autowired
	private HealthInformationService HIService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addInfo(@RequestBody HealthInformation HI, Authentication auth) {
		System.out.println(HI.toString());
		HIService.createHealthInformation(HI, auth);
		return ResponseEntity.ok("멤버 신체정보 등록 성공");
	}
	
	@PostMapping("/get")
	public ResponseEntity<?> getInfo(Authentication auth) {
		Optional<HealthInformation> HI = HIService.findbyMember(auth);
		return ResponseEntity.ok(HI);
	}
}
