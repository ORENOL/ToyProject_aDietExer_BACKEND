package edu.pnu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.DietRequest;
import edu.pnu.domain.FoodList;
import edu.pnu.domain.HealthInformation;
import edu.pnu.domain.Nutrient;
import edu.pnu.service.FavoritesService;
import edu.pnu.service.FoodService;
import edu.pnu.service.HealthInformationService;

@RestController
@RequestMapping("/api/private")
public class DietController {
	
	@Autowired
	private FoodService foodService;
	
	@Autowired 
	private HealthInformationService HIService;
	
	@Autowired
	private FavoritesService FavorService;
	
	@PostMapping("/getUserInformation")
	public ResponseEntity<?> getAllDiet(@RequestBody String temp, Authentication auth) {
		Datehistory history = foodService.getAllDiet(temp,auth);
		Optional<HealthInformation> HI = HIService.findbyMember(auth);
		List<FoodList> Favor = FavorService.searchFavorites(auth);
		
		Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("history", history);
        objectMap.put("HI", HI);
        objectMap.put("Favor", Favor);

		return ResponseEntity.ok(objectMap);
	}
	
	@PostMapping("/addFoodList")
	public ResponseEntity<?> addDiets(@RequestBody DietRequest dietRequest, Authentication auth) {
		foodService.addFood(dietRequest, auth);
		return ResponseEntity.ok("식단이 저장되었습니다.");
	}
	
	/* 구현 완료 */
	@PostMapping("/getTodayTotalNutrient")
	public ResponseEntity<?> getTodayTotalNutrient(@RequestBody String date, Authentication auth){
		List<Nutrient> list = foodService.getTodayTotalNutrient(date,auth);
		return ResponseEntity.ok(list);
	}
	
	/* 구현 완료 */
	@PostMapping("/getMonthlyData")
	public ResponseEntity<?> getMonthlyData(@RequestBody String date, Authentication auth){
		Map<String,Object> map = foodService.getMonthlyData(date,auth);
		return ResponseEntity.ok(map);
	}
	
	/* 구현 완료 */
	@PostMapping("/getMonthlyCaloriesAndWeights")
	public ResponseEntity<?> getMonthlyCaloriesAndWeights(@RequestBody String date, Authentication auth){
		Map<String,Object> map = foodService.getMonthlyCaloriesAndWeights(date,auth);
		return ResponseEntity.ok(map);
	}
	
}

