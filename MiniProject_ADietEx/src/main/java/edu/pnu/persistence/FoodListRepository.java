package edu.pnu.persistence;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.FoodList;

public interface FoodListRepository extends JpaRepository<FoodList, String> {
	
//	List<FoodList> findAllByFood_nameContaining(String foodname);
//	
//	List<FoodList> findFirst100ByFood_nameContaining(String foodname);
//	
//	List<FoodList> findFirst100ByFood_nameContainingOrderByfood_nameAsc(String foodname);

	// Limit 100
	@Query(value = "SELECT * FROM food_list WHERE REPLACE(food_name, ' ', '') LIKE %:food_name% ORDER BY LENGTH(food_name) LIMIT 100", nativeQuery = true)
	List<FoodList> findFirst100ByFood_nameContainingOrderByLengthfood_name(@Param("food_name") String foodname);

	// Limit 10
	@Query(value = "SELECT * FROM food_list WHERE REPLACE(food_name, ' ', '') LIKE %:food_name% ORDER BY LENGTH(food_name) LIMIT 10", nativeQuery = true)
	List<FoodList> findFirst10ByFood_nameContainingOrderByLengthfood_name(@Param("food_name") String foodname);
	
	FoodList findByFoodname(String foodname);
}
