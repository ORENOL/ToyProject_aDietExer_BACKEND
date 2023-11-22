package edu.pnu.controller;

import java.util.List;

import java.util.Optional;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.Diet;
import edu.pnu.domain.Member;
import edu.pnu.persistence.DatehistoryRepository;
import edu.pnu.persistence.DietRepository;
import edu.pnu.service.FoodService;

@RestController
public class DietController {
	
	@Autowired
	private FoodService foodService;
	

	
	// 테스트용
	@GetMapping("/username")
	public String username(Authentication auth) {
		return auth.getName();
	}
	
	@Autowired
	private DatehistoryRepository dateRepo;
	
	@Autowired
	private DietRepository dietRepo;
	
	// 테스트용
    @GetMapping("/date")
    public ResponseEntity<Datehistory> getDateHistoryById() {
        Optional<Datehistory> dateHistory = dateRepo.findById((long) 1);
        Datehistory date = dateHistory.get();
        
		List<Diet> list = dietRepo.findByDatehistory(date);
		
		date.setDiets(list);
        return ResponseEntity.ok(date);
    }
		
	
	@PostMapping("/getFoodList")
	public Datehistory getAllDiet(@RequestBody String temp) {
		Datehistory history = foodService.getAllDiet(temp);
		return history;
	}
	
	
	@GetMapping("/addFood")
	public void addDietView() {}
	
	@PostMapping("/addFood")
	public void addDiets(@RequestBody String diet, Authentication auth) {
		foodService.addFood(diet, auth);
		
	}
	
	@PutMapping("/updateDiet")
	public void updateDiet(Diet diet1, Diet diet2) {
		// 멤버가 식단을 DB에서 수정함
	}
	
	@DeleteMapping("/deleteDiet")
	public void deleteDiet(Diet diet) {
		foodService.deleteDiet(diet);
	}
}

