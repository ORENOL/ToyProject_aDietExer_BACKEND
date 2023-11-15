package edu.pnu.controller;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Diet;
import edu.pnu.domain.Member;

@RestController
public class DietController {

	
	@GetMapping("/getAllDiet")
	public List<Diet> getAllDiet(Member member) {
		// 멤버가 등록한 모든 식단을 DB로부터 가져옴
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
	
	@PostMapping("/addDiet")
	public void addDiet(Member member, Diet diet) {
		// 멤버가 식단을 DB에 등록함
		return;
	}
	
	@PutMapping("/updateDiet")
	public void updateDiet(Diet diet) {
		// 멤버가 식단을 DB에서 수정함
	}
	
	@DeleteMapping("/deleteDiet")
	public void deleteDiet(Diet diet) {
		// 멤버가 식단을 DB에서 삭제함
	}
}

