package edu.pnu.service;

import java.sql.Blob;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.Diet;
import edu.pnu.domain.DietRequest;
import edu.pnu.domain.FoodList;
import edu.pnu.domain.Member;
import edu.pnu.domain.Slot;
import edu.pnu.persistence.DatehistoryRepository;
import edu.pnu.persistence.DietRepository;
import edu.pnu.persistence.MemberRepository;

@Service
public class FoodService {

	@Autowired
	private DietRepository dietRepo;

	@Autowired
	private DatehistoryRepository dateRepo;

	@Autowired
	private MemberRepository memRepo;

	public void addFood(@RequestBody DietRequest dietRequest, Authentication auth) {
		List<Diet> dietlist = dietRequest.getDietList();
		Date date = dietRequest.getDate();
		Slot slot = dietRequest.getSlot();
		String img = dietRequest.getImg();
		Optional<Member> member = memRepo.findById(auth.getName());

		// 이미 존재하는 식단기록인지 체크하기
		Optional<Datehistory> existingHistory = dateRepo.findByDateAndSlotAndMember(date, slot, member.get());

		Datehistory history = null;

		// 존재하는 식단기록이면 기존 식단 삭제 및 식단기록 업데이트
		if (existingHistory.isPresent()) {
			List<Diet> diet_list = dietRepo.findByDatehistory(existingHistory.get());
			dietRepo.deleteAll(diet_list);

			history = existingHistory.get();
		} else {
			// 존재하지 않으면 신규 등록
			history = new Datehistory();
		}

		history.setDate(date);
		history.setSlot(slot);
		history.setMember(member.get());
		history.setImg(img);
		dateRepo.save(history);

		for (Diet diet : dietlist) {
			diet.setDatehistory(history);
			dietRepo.save(diet);
		}
	}
/*
	public void temp_addFood(@RequestBody DietRequest dietRequest, Authentication auth) {
		System.out.println(dietRequest);
		ObjectMapper objectMapper = new ObjectMapper();

		List<Diet> dietList = dietRequest.getDietList();

		try {
			JsonNode jsonNode = objectMapper.readTree(dietRequest);

			// 사용자 정보 추출
			Optional<Member> member = memRepo.findById(auth.getName());
			System.out.println(member.toString());
			// 날짜 추출
			String StringDate = jsonNode.get("date").asText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date FoodDate = dateFormat.parse(StringDate);
			// 아침, 점심, 저녁 추출
			String slot = jsonNode.get("slot").asText();
			// 음식리스트 추출
			JsonNode FoodList = jsonNode.get("dietList");
			// 이미지 추출
			String img_Temp = jsonNode.get("img").asText();
//		byte[] buff = img_Temp.getBytes();
//		Blob blob = new SerialBlob(buff);
//		System.out.println(buff.length);

			// 이미 존재하는 식단기록인지 체크하기
			Optional<Datehistory> existingHistory = dateRepo.findByDateAndSlotAndMember(FoodDate, Slot.valueOf(slot),
					member.get());

			Datehistory history = null;

			// 존재하는 식단기록이면 기존 식단 삭제 및 식단기록 업데이트
			if (existingHistory.isPresent()) {
				List<Diet> diet_list = dietRepo.findByDatehistory(existingHistory.get());
				dietRepo.deleteAll(diet_list);

				history = existingHistory.get();
			} else {
				// 존재하지 않으면 신규 등록
				history = new Datehistory();
			}

			history.setDate(FoodDate);
			history.setSlot(Slot.valueOf(slot));
			history.setMember(member.get());
			history.setImg(img_Temp);
			dateRepo.save(history);

			// 음식리스트를 잘라서 한 음식마다 날짜,시간,유저 붙여서 식단 등록
			for (JsonNode food : FoodList) {

				Diet diets = food;
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
				diets.set트랜스지방산(food.get("트랜스지방산").asText());
				diets.set포화지방산(food.get("포화지방산").asText());
				diets.setDatehistory(history);
				dietRepo.save(diets);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	public Datehistory getAllDiet(@RequestBody String temp) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {

			JsonNode jsonNode = objectMapper.readTree(temp);

			String StringDate = jsonNode.get("date").asText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(StringDate);
			String slot = jsonNode.get("slot").asText();
			
			Optional<Datehistory> datehistory = dateRepo.findByDateAndSlot(date, Slot.valueOf(slot));
			
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

}
