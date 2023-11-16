package edu.pnu.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Diet;
import edu.pnu.domain.Member;
import edu.pnu.service.FoodService;

@RestController
public class DietController {
	
	@Autowired
	private FoodService foodService;

	
	@GetMapping("/getAllDiet")
	public List<Diet> getAllDiet() {
		return null;
	}
	
	@GetMapping("/getDiet")
	public List<Diet> getDiet(Member member, Diet diet) {
		// 멤버가 등록한 특정 식단을 DB로부터 가져옴
		return null;
	}
	
	@GetMapping("/getDietOnDates")
	public List<Diet> getDietOnDates(Member member, Date date){
		// 멤버가 등록한 특정일의 모든 식단을 DB로부터 가져옴
		return null;
	}
	
	@GetMapping("/addFood")
	public void addDietView() {}
	
	@PostMapping("/addFood")
	public void addDiet(Diet diet) {
		foodService.addFood(diet);
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

