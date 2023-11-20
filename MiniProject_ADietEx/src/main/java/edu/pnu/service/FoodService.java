package edu.pnu.service;

import java.sql.Blob;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.Diet;
import edu.pnu.domain.FoodList;
import edu.pnu.domain.Member;
import edu.pnu.domain.Slot;
import edu.pnu.persistence.DatehistoryRepository;
import edu.pnu.persistence.DietRepository;

@Service
public class FoodService {
	
	@Autowired
	private DietRepository dietRepo;
	
	@Autowired
	private DatehistoryRepository dateRepo;

//	public void addFood(Diet diet, Authentication auth) {
//		String member_id = auth.getName();
//		diet.setMember_username(member_id);
//		dietRepo.save(diet);
//	}
	
	// 나중에 member연결 잊기 말기 Authentication
	public void addFood(@RequestBody String diet) {
		System.out.println(diet);
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			
		
		JsonNode jsonNode = objectMapper.readTree(diet);
		
		// 날짜 추출
		String FoodDate = jsonNode.get("date").asText();
		
		// 아점저녁 추출
		String slot = jsonNode.get("slot").asText();
		
		// 음식리스트 추출
		JsonNode FoodList = jsonNode.get("dietList");
		

		// 사진 추출
//		String baseimg = jsonNode.get("img").asText();
//		byte[] decodeByte = Base64.getDecoder().decode(baseimg);
//        Blob blob = SerialBlob(decodeByte);

		
		Datehistory history = new Datehistory();
		history.setDate(FoodDate);
		history.setSlot(Slot.valueOf(slot));
		dateRepo.save(history);
		
		// 음식리스트를 잘라서 한 음식마다 날짜,시간,유저 붙여서 식단 등록
		for (JsonNode food : FoodList) {
			
			Diet diets = new Diet();
			diets.set_1회제공량(food.get("_1회제공량").asText());
			diets.set식품명(food.get("식품명").asText());
			diets.set나트륨(food.get("나트륨").asText());
			diets.set단백질(food.get("단백질").asText());
			diets.set당류(food.get("당류").asText());
			diets.set섭취량(food.get("섭취량").asText());
			diets.set지방(food.get("지방").asText());
			diets.set칼로리(food.get("칼로리").asText());
			diets.set콜레스테롤(food.get("콜레스테롤").asText());
			diets.set탄수화물(food.get("탄수화물").asText());
			diets.set트랜스지방(food.get("트랜스지방산").asText());
			diets.set포화지방(food.get("포화지방산").asText());
			diets.setDatehistory(history);
			dietRepo.save(diets);
			
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void deleteDiet(Diet diet) {
		dietRepo.delete(diet);
	}

	public Datehistory getAllDiet(@RequestBody String temp) {

		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			
		JsonNode jsonNode = objectMapper.readTree(temp);
		String date = jsonNode.get("date").asText();

		
		String slot = jsonNode.get("slot").asText();
		
		Datehistory datehistory = dateRepo.findByDateAndAndSlot(date, Slot.valueOf(slot));
		
		List<Diet> list = dietRepo.findByDatehistory(datehistory);
		
		datehistory.setDiets(list);
		
		System.out.println("list:" + list);
		System.out.println("date:" + datehistory);
		
		return datehistory;
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Diet> getDietOnDates(Diet diet, Authentication auth) {
		String member_id = auth.getName();
		//Date date = diet.getDate();
		//List<Diet> list = dietRepo.findByMember_usernameAndDate(member_id, date);
		return null;
	}



	public Date getAllDate() {
		Optional<Diet> diet = dietRepo.findById((long)1);
		Diet diets = diet.get();
		//Date date = diets.getDate();
		return null;
	}

}
