package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.HealthInformationService;

@RestController
@RequestMapping("/api/private")
public class HealthinformationController {

	@Autowired
	private HealthInformationService HIService;
	
	// 유저의 신체정보를 등록함
	@PostMapping("/addUserInformation")
	public ResponseEntity<?> addInfo(@RequestBody String HI, Authentication auth) {
		HIService.createHealthInformation(HI, auth);
		HIService.addUserWeightDataHistory(HI, auth);
		return ResponseEntity.ok("멤버 신체정보 등록 성공");
	}
	
}
