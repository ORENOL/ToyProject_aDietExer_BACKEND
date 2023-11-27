package edu.pnu.persistence;



import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Favorites;
import java.util.List;
import edu.pnu.domain.Member;




public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
	
	List<Favorites> findByMember(Member member);
	
}
