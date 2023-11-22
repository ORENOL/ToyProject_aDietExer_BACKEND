package edu.pnu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import edu.pnu.domain.HealthInformation;
import edu.pnu.domain.Member;
import edu.pnu.persistence.HealthInformationRepository;
import edu.pnu.persistence.MemberRepository;

@Service
public class HealthInformationService {

	@Autowired
    private HealthInformationRepository healthInformationRepository;
	
	@Autowired
	private MemberRepository memRepo;

//    public HealthInformation createHealthInformation(HealthInformation healthInformation, Authentication auth) {
//		Optional<Member> member = memRepo.findById(auth.getName());
//		healthInformation.setMember(member.get());
//        return healthInformationRepository.save(healthInformation);
//    }
    
    public HealthInformation createHealthInformation(HealthInformation healthInformation, Authentication auth) {
		Optional<Member> member = memRepo.findById(auth.getName());
        Optional<HealthInformation> existingHealthInformation = healthInformationRepository.findByMember(member.get());
        if (existingHealthInformation.isPresent()) {
            HealthInformation healthInformationToUpdate = existingHealthInformation.get();
            healthInformationToUpdate.setAge(healthInformation.getAge());
            healthInformationToUpdate.setGender(healthInformation.getGender());
            healthInformationToUpdate.setHeight(healthInformation.getHeight());
            healthInformationToUpdate.setWeight(healthInformation.getWeight());
            healthInformationToUpdate.setActivityFactor(healthInformation.getActivityFactor());
            return healthInformationRepository.save(healthInformationToUpdate);
        } else {
            // 해당 ID에 해당하는 HealthInformation이 없을 경우 생성
        	healthInformation.setMember(member.get());
        	return healthInformationRepository.save(healthInformation);
        }
    }


    public HealthInformation updateHealthInformation(HealthInformation updatedHealthInformation, Authentication auth) {
		Optional<Member> member = memRepo.findById(auth.getName());
        Optional<HealthInformation> existingHealthInformation = healthInformationRepository.findByMember(member.get());
        if (existingHealthInformation.isPresent()) {
            HealthInformation healthInformationToUpdate = existingHealthInformation.get();
            healthInformationToUpdate.setAge(updatedHealthInformation.getAge());
            healthInformationToUpdate.setGender(updatedHealthInformation.getGender());
            healthInformationToUpdate.setHeight(updatedHealthInformation.getHeight());
            healthInformationToUpdate.setWeight(updatedHealthInformation.getWeight());
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
		if (Hi.isPresent()) {
			return Hi;
		}{
			return null;
		}
	
	}
}