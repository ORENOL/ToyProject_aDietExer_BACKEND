package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Favorites;
import edu.pnu.domain.FoodList;
import edu.pnu.service.FavoritesService;
import edu.pnu.service.FoodListService;

@RestController
@RequestMapping("/api")
public class FoodListController {
	
	@Autowired
	private FoodListService foodListService;
	
	@Autowired
	private FavoritesService favorService;
	
	// 테스트용
//	@GetMapping("/main/getFoodListAll")
//	public List<FoodList> getFoodListAlls() {
//		List<FoodList> list = FLservice.FoodListAll();
//		return list;
//	}
//
//	@PostMapping("/getFoodListAll")
//	public List<FoodList> getFoodListAll() {
//		List<FoodList> list = FLservice.FoodListAll();
//		return list;
//	}
	
	@PostMapping("/searchFoodList")
	public ResponseEntity<?> getFoodListByname(@RequestBody String foodname) {
		List<FoodList> list = foodListService.getFoodListByname(foodname);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("searchFavorites")
	public ResponseEntity<?> searchFavorites(Authentication auth) {
		List<Favorites> list = favorService.searchFavorites(auth);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("addFavorites")
	public ResponseEntity<?> addFavorites(@RequestBody String foodname, Authentication auth) {
		favorService.addFavorites(foodname, auth);
		return ResponseEntity.ok().build();
	}
}
