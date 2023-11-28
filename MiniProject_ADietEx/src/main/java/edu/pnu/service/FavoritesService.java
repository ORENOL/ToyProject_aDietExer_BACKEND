package edu.pnu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Favorites;
import edu.pnu.domain.FoodList;
import edu.pnu.domain.Member;
import edu.pnu.persistence.FavoritesRepository;
import edu.pnu.persistence.FoodListRepository;
import edu.pnu.persistence.MemberRepository;
import jakarta.transaction.Transactional;

@Service
public class FavoritesService {
	
	@Autowired
	private FoodListRepository foodListRepo;
	
	@Autowired
	private MemberRepository memRepo;
	
	@Autowired
	private FavoritesRepository favorRepo;

	public List<FoodList> searchFavorites(Authentication auth) {
		Optional<Member> member = memRepo.findById(auth.getName());
		List<Favorites> favor = favorRepo.findByMember(member.get());
		List<FoodList> foodlist = new ArrayList<>();
		for(Favorites list : favor)
			foodlist.add(list.getFoodlist());
		return foodlist;
	}

	public ResponseEntity<?> addFavorite(String foodname, Authentication auth) {
		Favorites favorite = new Favorites();
		System.out.println("foodname:" + foodname);
		FoodList foodlist = foodListRepo.findByFoodname(foodname);
		System.out.println("FoodList:" + foodlist);
		favorite.setFoodlist(foodlist);
		Optional<Member> member = memRepo.findById(auth.getName());
		favorite.setMember(member.get());
		
		if (favorRepo.findByFoodlistAndMember(foodlist, member.get()).isPresent()) {
			// 중복되는 즐겨찾기가 있으면 등록 거부
			return ResponseEntity.status(226).build();
		} else {
			// 증복되는 즐겨찾기가 없으면 등록 허용 
			favorRepo.save(favorite);
			return ResponseEntity.ok().build();
		}
			
		
	}
	
	@Transactional
	public void deleteFavorite(String foodname, Authentication auth) {
		FoodList foodlist = foodListRepo.findByFoodname(foodname);
		Optional<Member> member = memRepo.findById(auth.getName());
		favorRepo.deleteByFoodlistAndMember(foodlist, member.get());
	}

}
