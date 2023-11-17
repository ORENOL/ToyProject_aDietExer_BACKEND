package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.FoodList;

public interface FoodListRepository extends JpaRepository<FoodList, String> {
	
	List<FoodList> findAllBy식품명Containing(String foodname);
	
	List<FoodList> findFirst100By식품명Containing(String foodname);

}
