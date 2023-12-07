package edu.pnu.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.Gender;
import edu.pnu.domain.HealthInformation;
import edu.pnu.domain.Member;
import edu.pnu.domain.Slot;
import edu.pnu.persistence.DatehistoryRepository;
import edu.pnu.persistence.HealthInformationRepository;
import edu.pnu.persistence.MemberRepository;

@Service
public class HealthInformationService {

	@Autowired
    private HealthInformationRepository healthInformationRepository;
	
	@Autowired
	private MemberRepository memRepo;
	
	@Autowired
	private DatehistoryRepository dateHistoryRepo;
    
    public HealthInformation createHealthInformation(String HI, Authentication auth) {
		Optional<Member> member = memRepo.findById(auth.getName());
        Optional<HealthInformation> existingHealthInformation = healthInformationRepository.findByMember(member.get());
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			JsonNode jsonNode = objectMapper.readTree(HI);
			
			//출생연도
			int age = jsonNode.get("age").asInt();
			LocalDate currentDate = LocalDate.now();
			int currentYear = currentDate.getYear();
			int year = currentYear - age;
			
			//성별
			int stringGender = jsonNode.get("gender").asInt();
			Gender gender = null;
			if(stringGender == 1) gender = Gender.MAN;
			else if(stringGender == 2) gender = Gender.WOMAN;
			
			//키
			float height = (float)jsonNode.get("height").asDouble();
			
			//몸무게
			float weight = (float)jsonNode.get("weight").asDouble();
			
			//활동계수
			int activityFactor = jsonNode.get("activityFactor").asInt();
			
	        if (existingHealthInformation.isPresent()) {
	            HealthInformation healthInformationToUpdate = existingHealthInformation.get();
	            healthInformationToUpdate.setYear(year);
	            healthInformationToUpdate.setGender(gender);
	            healthInformationToUpdate.setHeight(height);
	            healthInformationToUpdate.setActivityFactor(activityFactor);
	            return healthInformationRepository.save(healthInformationToUpdate);
	        } else {
	            // 해당 ID에 해당하는 HealthInformation이 없을 경우 생성
	        	return healthInformationRepository.save(HealthInformation.builder()
	        			.member(member.get())
	        			.year(year)
	        			.gender(gender)
	        			.height(height)
	        			.activityFactor(activityFactor).build());
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }


    public HealthInformation updateHealthInformation(HealthInformation updatedHealthInformation, Authentication auth) {
		Optional<Member> member = memRepo.findById(auth.getName());
        Optional<HealthInformation> existingHealthInformation = healthInformationRepository.findByMember(member.get());
        if (existingHealthInformation.isPresent()) {
            HealthInformation healthInformationToUpdate = existingHealthInformation.get();
            healthInformationToUpdate.setYear(updatedHealthInformation.getYear());
            healthInformationToUpdate.setGender(updatedHealthInformation.getGender());
            healthInformationToUpdate.setHeight(updatedHealthInformation.getHeight());
            healthInformationToUpdate.setActivityFactor(updatedHealthInformation.getActivityFactor());
            return healthInformationRepository.save(healthInformationToUpdate);
        } else {
            // 해당 ID에 해당하는 HealthInformation이 없을 경우 예외 처리
            throw new RuntimeException("HealthInformation not found with member: " + member.get().getUsername());
        }
    }

    public void deleteHealthInformation(Long healthInformationId) {
        healthInformationRepository.deleteById(healthInformationId);
    }

	public Optional<HealthInformation> findbyMember(Authentication auth) {
		Optional<Member> member = memRepo.findById(auth.getName());
		Optional<HealthInformation> Hi = healthInformationRepository.findByMember(member.get());
		if (Hi.isPresent()) return Hi;
		return null;
	}
	
	public void addUserWeightDataHistory(@RequestBody String HI, Authentication auth) {
		ObjectMapper objectMapper = new ObjectMapper();
		String username = auth.getName();
		Member m = memRepo.findByUsername(username);
		try {
			JsonNode jsonNode = objectMapper.readTree(HI);
			
			//몸무게 구하기
			float weight = (float)jsonNode.get("weight").asDouble();
				
			//날짜, 시간대 구하기
			String stringSlot = jsonNode.get("slot").asText();
			Slot slot = null;
			if(stringSlot.equals("아침")) slot = Slot.아침;
			if(stringSlot.equals("점심")) slot = Slot.점심;
			if(stringSlot.equals("저녁")) slot = Slot.저녁;
		
			String StringDate = jsonNode.get("date").asText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date targetDate = dateFormat.parse(StringDate);

			Optional<Datehistory> optionalHistory = dateHistoryRepo.findByDateAndSlotAndMemberUsername(targetDate,slot,username);
			Datehistory targetHistory = null;
			if(optionalHistory.isPresent()) {
				targetHistory = optionalHistory.get();
				targetHistory.setWeight(weight);
				dateHistoryRepo.save(targetHistory);
			}
			else {
				targetHistory = new Datehistory();
				targetHistory.setDate(targetDate);
				targetHistory.setSlot(slot);
				targetHistory.setWeight(weight);
				targetHistory.setMember(m);
				dateHistoryRepo.save(targetHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}