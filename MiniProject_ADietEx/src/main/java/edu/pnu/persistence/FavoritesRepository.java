package edu.pnu.persistence;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Favorites;
import edu.pnu.domain.FoodList;
import edu.pnu.domain.Member;





public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
	
	List<Favorites> findByMember(Member member);
	
	void deleteByFoodlistAndMember(FoodList foodlist, Member member);
	
	Optional<Favorites> findByFoodlistAndMember(FoodList foodlist, Member member);
	
}
