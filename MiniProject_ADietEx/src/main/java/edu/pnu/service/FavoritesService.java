package edu.pnu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import edu.pnu.domain.Favorites;
import edu.pnu.domain.FoodList;
import edu.pnu.domain.Member;
import edu.pnu.persistence.FavoritesRepository;
import edu.pnu.persistence.FoodListRepository;
import edu.pnu.persistence.MemberRepository;

public class FavoritesService {
	
	@Autowired
	private FoodListRepository foodListRepo;
	
	@Autowired
	private MemberRepository memRepo;
	
	@Autowired
	private FavoritesRepository favorRepo;

	public List<Favorites> searchFavorites(Authentication auth) {
		Optional<Member> member = memRepo.findById(auth.getName());
		return favorRepo.findByMember(member.get());
	}

	public void addFavorites(String foodname, Authentication auth) {
		Favorites favorite = new Favorites();
		FoodList foodlist = foodListRepo.findByFood_name(foodname);
		favorite.setFoodlist(foodlist);
		Optional<Member> member = memRepo.findById(auth.getName());
		favorite.setMember(member.get());
		favorRepo.save(favorite);
	}

}
