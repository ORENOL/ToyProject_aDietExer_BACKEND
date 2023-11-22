package edu.pnu.persistence;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.FoodList;

public interface FoodListRepository extends JpaRepository<FoodList, String> {
	
	List<FoodList> findAllBy식품명Containing(String foodname);
	
	List<FoodList> findFirst100By식품명Containing(String foodname);
	
	List<FoodList> findFirst100By식품명ContainingOrderBy식품명Asc(String foodname);
	
	List<FoodList> findBy식품명StartingWith(String 식품명);
	
	@Query(value = "SELECT * FROM food_list WHERE REPLACE(식품명, ' ', '') LIKE %:foodname% ORDER BY LENGTH(식품명) LIMIT 100", nativeQuery = true)
	List<FoodList> findFirst100By식품명ContainingOrderByLength식품명(@Param("foodname") String foodname);

}
