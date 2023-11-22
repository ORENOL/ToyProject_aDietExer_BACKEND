package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.FoodList;
import edu.pnu.service.FoodListService;

@RestController
public class FoodListController {
	
	@Autowired
	private FoodListService FLservice;
	
	// 테스트용
	@GetMapping("/main/getFoodListAll")
	public List<FoodList> getFoodListAlls() {
		List<FoodList> list = FLservice.FoodListAll();
		return list;
	}

	@PostMapping("/getFoodListAll")
	public List<FoodList> getFoodListAll() {
		List<FoodList> list = FLservice.FoodListAll();
		return list;
	}
	
	@PostMapping("/searchFoodList")
	public ResponseEntity<?> getFoodListByname(@RequestBody String foodname) {
		List<FoodList> list = FLservice.getFoodListByname(foodname);
		return ResponseEntity.ok(list);
	}
}
