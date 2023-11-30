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
	
	@Query(value = "select nutrient.* from nutrient, datehistory where datehistory.date = :date and (datehistory.nutrient_id = nutrient.nutrient_id);", nativeQuery = true)
	Optional<List<Nutrient>> findAllBydate(@Param("date") Date date);
}
