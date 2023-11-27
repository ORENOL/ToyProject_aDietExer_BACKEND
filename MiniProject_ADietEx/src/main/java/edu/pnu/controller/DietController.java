package edu.pnu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.Diet;
import edu.pnu.domain.DietRequest;
import edu.pnu.domain.HealthInformation;
import edu.pnu.persistence.DatehistoryRepository;
import edu.pnu.persistence.DietRepository;
import edu.pnu.service.FoodService;
import edu.pnu.service.HealthInformationService;

@RestController
@RequestMapping("/api")
public class DietController {
	
	@Autowired
	private FoodService foodService;
	
	@Autowired 
	private HealthInformationService HIService;
	
	@PostMapping("/getUserInformation")
	public ResponseEntity<?> getAllDiet(@RequestBody String temp, Authentication auth) {
		Datehistory history = foodService.getAllDiet(temp);
		Optional<HealthInformation> HI = HIService.findbyMember(auth);
		
		Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("history", history);
        objectMap.put("HI", HI);

		return ResponseEntity.ok(objectMap);
	}
	
	
//	@GetMapping("/addFood")
//	public void addDietView() {}
//	
	@PostMapping("/addFoodList")
	public ResponseEntity<?> addDiets(@RequestBody DietRequest dietRequest, Authentication auth) {
		System.out.println(dietRequest);
		foodService.addFood(dietRequest, auth);
		return ResponseEntity.ok("식단이 저장되었습니다.");
	}
}

