package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.FoodList;
import edu.pnu.service.FavoritesService;
import edu.pnu.service.FoodListService;

@RestController
@RequestMapping("/api/private")
public class FoodListController {
	
	@Autowired
	private FoodListService foodListService;
	
	@Autowired
	private FavoritesService favorService;
	
	// 음식리스트를 검색함
	@PostMapping("/searchFoodList")
	public ResponseEntity<?> getFoodListByname(@RequestBody String foodname) {
		List<FoodList> list = foodListService.getFoodListByname(foodname);
		return ResponseEntity.ok(list);
	}
	
	// 즐겨찾기된 음식리스트를 가져옴
	@PostMapping("/searchFavoriteFoods")
	public ResponseEntity<?> searchFavorites(Authentication auth) {
		List<FoodList> list = favorService.searchFavorites(auth);
		return ResponseEntity.ok(list);
	}
	
	// 음식리스트를 단어마다 검색함 (검색 자동완성기능)
	@PostMapping("/fastSearch2")
	public ResponseEntity<?> fastSearch2(@RequestBody FoodList foodname) {
		List<FoodList> list = foodListService.fastSearch2(foodname.getFoodname());
		return ResponseEntity.ok(list);
	}
	
	// 음식을 즐겨찾기에 추가함
	@PostMapping("/addFavoriteFood")
	public ResponseEntity<?> addFavorites(@RequestBody FoodList foodname, Authentication auth) {
		return favorService.addFavorite(foodname.getFoodname(), auth);
	}
	
	// 음식을 즐겨찾기에서 제거함
	@DeleteMapping("/deleteFavoriteFood")
	public ResponseEntity<?> deleteFavorite(@RequestBody FoodList foodname, Authentication auth) {
		favorService.deleteFavorite(foodname.getFoodname(), auth);
		return ResponseEntity.ok().build();
	}
}
