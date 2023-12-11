package edu.pnu.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.Diet;
import edu.pnu.domain.DietRequest;
import edu.pnu.domain.Member;
import edu.pnu.domain.Nutrient;
import edu.pnu.domain.enums.Slot;
import edu.pnu.persistence.DatehistoryRepository;
import edu.pnu.persistence.DietRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.NutrientRepository;

@Service
public class FoodService {

	@Autowired
	private DietRepository dietRepo;

	@Autowired
	private DatehistoryRepository dateRepo;

	@Autowired
	private MemberRepository memRepo;

	@Autowired
	private NutrientRepository nutrientRepo;

	public void addFood(@RequestBody DietRequest dietRequest, Authentication auth) {
		List<Diet> dietlist = dietRequest.getDietList();
		Date date = dietRequest.getDate();
		Slot slot = dietRequest.getSlot();
		String img = dietRequest.getImg();
		Nutrient nutrient = dietRequest.getNutrient();
		Optional<Member> member = memRepo.findById(auth.getName());
		Float weight = dietRequest.getWeight();

		// 이미 존재하는 식단기록인지 체크하기
		Optional<Datehistory> existingHistory = dateRepo.findByDateAndSlotAndMember(date, slot, member.get());

		Datehistory history = null;

		// 존재하는 식단기록이면 기존 식단 삭제 및 식단기록 업데이트
		if (existingHistory.isPresent()) {
			List<Diet> diet_list = dietRepo.findByDatehistory(existingHistory.get());
			Nutrient targetNutrient = nutrientRepo.findByDatehistory(existingHistory.get());
			dietRepo.deleteAll(diet_list);
			if (!Objects.isNull(targetNutrient))
				nutrientRepo.delete(targetNutrient);
			history = existingHistory.get();
		} else {
			// 존재하지 않으면 신규 등록
			history = new Datehistory();
		}

		history.setDate(date);
		history.setSlot(slot);
		history.setMember(member.get());
		history.setImg(img);
		history.setNutrient(nutrient);
		history.setWeight(weight);
		nutrient.setDatehistory(history);
		dateRepo.save(history);

		for (Diet diet : dietlist) {
			diet.setDatehistory(history);
			dietRepo.save(diet);
		}
  	}

	public Datehistory getAllDiet(@RequestBody String temp, Authentication auth) {

		ObjectMapper objectMapper = new ObjectMapper();
		String username = auth.getName();

		try {

			JsonNode jsonNode = objectMapper.readTree(temp);
			String StringDate = jsonNode.get("date").asText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(StringDate);
			String slot = jsonNode.get("slot").asText();

			Optional<Datehistory> datehistory = dateRepo.findByDateAndSlotAndMemberUsername(date, Slot.valueOf(slot),username);

			if (datehistory.isPresent()) {
				Datehistory datehistorys = datehistory.get();
				List<Diet> list = dietRepo.findByDatehistory(datehistorys);

				datehistorys.setDiets(list);
				return datehistorys;

			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Nutrient> getTodayTotalNutrient(@RequestBody String date, Authentication auth) {
		ObjectMapper objectMapper = new ObjectMapper();
		String username = auth.getName();
		try {

			JsonNode jsonNode = objectMapper.readTree(date);

			String StringDate = jsonNode.get("date").asText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date targetDate = dateFormat.parse(StringDate);
			
			Optional<List<Nutrient>> nutrients = nutrientRepo.findAllBydate(targetDate,username);
			
			return nutrients.get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<String, Object> getMonthlyData(@RequestBody String date, Authentication auth){
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();
		String username = auth.getName();
		try {
			JsonNode jsonNode = objectMapper.readTree(date);
			String StringDate = jsonNode.get("targetDate").asText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date targetDate = dateFormat.parse(StringDate);
			
			Float calorie = nutrientRepo.findCalorieBydate(targetDate,username);
			if(Objects.isNull(calorie)) calorie = 0f;
			
			List<Datehistory> targetHistorys = dateRepo.findByDateAndMemberUsername(targetDate,username);
			System.out.println(targetHistorys);
			Float morningWeight = 0f;
			Float lunchWeight = 0f;
			Float dinnerWeight = 0f;
			for(Datehistory d : targetHistorys) {
				if(d.getSlot().equals(Slot.아침)) morningWeight = d.getWeight();
				if(d.getSlot().equals(Slot.점심)) lunchWeight = d.getWeight();
				if(d.getSlot().equals(Slot.저녁)) dinnerWeight = d.getWeight();
			}
			List<Float> arr = new ArrayList<>();
			arr.add(morningWeight);
			arr.add(lunchWeight);
			arr.add(dinnerWeight);
			
			map.put("date", StringDate);
			map.put("calorie", calorie);
			map.put("weights",arr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
	
	public Map<String, Object> getMonthlyCaloriesAndWeights(@RequestBody String date, Authentication auth){
		
		Map<String, Object> map = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		String username = auth.getName();
		
		try {
			JsonNode jsonNode = objectMapper.readTree(date);
			
			String StringDate = jsonNode.get("today").asText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date targetDate = dateFormat.parse(StringDate);
			
			List<java.sql.Date> dates = dateRepo.findDateByDate(targetDate,username);
			List<Float> weights = dateRepo.findWeightByDate(targetDate,username);
			List<Float> calories = nutrientRepo.findTotalKcalByDate(targetDate,username);
			
			map.put("dates", dates);
			map.put("weights", weights);
			map.put("calories", calories);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return map;
	}

}
