package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.FoodList;
import edu.pnu.persistence.FoodListRepository;

@Service
public class FoodListService{
	
	@Autowired
	private FoodListRepository foodListRepo;

	public List<FoodList> FoodListAll() {
		List<FoodList> list = foodListRepo.findAll();
		return list;
	}

	public List<FoodList> getFoodListByname(String foodname) {
		System.out.println(foodname);
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
		JsonNode jsonNode = objectMapper.readTree(foodname);
		String foodName = jsonNode.get("foodname").asText();
		foodName = foodName.replace(" ", "");

		System.out.println(foodName);
		
		List<FoodList> list = foodListRepo.findFirst100ByFood_nameContainingOrderByLengthfood_name(foodName);
		
		return list;
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<FoodList> fastSearch2(String foodname) {
		List<FoodList> list = foodListRepo.findFirst10ByFood_nameContainingOrderByLengthfood_name(foodname);
		return list;
	}

}
