package edu.pnu.persistence;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.Nutrient;

public interface NutrientRepository extends JpaRepository<Nutrient, Long> {
	Nutrient findByDatehistory(Datehistory datehistory);
	
	@Query(value = "select nutrient.* from nutrient, datehistory where datehistory.member_username = :username and datehistory.date = :date and (datehistory.nutrient_id = nutrient.nutrient_id);", nativeQuery = true)
	Optional<List<Nutrient>> findAllBydate(@Param("date") Date date, @Param("username") String username);
	
	@Query(value = "select sum(total_kcal) from nutrient n, datehistory d where d.member_username = :username and (n.nutrient_id = d.nutrient_id) and d.date = :date", nativeQuery = true)
	Float findCalorieBydate(@Param("date") Date date, @Param("username") String username);
	
	@Query(value = "select sum(n.total_kcal) from nutrient n, datehistory d where d.member_username = :username and (n.nutrient_id = d.nutrient_id) and d.date BETWEEN DATE_SUB(:date, INTERVAL 7 DAY) AND DATE_ADD(:date, INTERVAL 7 DAY) GROUP by d.date order by d.date", nativeQuery = true)
	List<Float> findTotalKcalByDate(@Param("date") Date date, @Param("username") String username);
}
