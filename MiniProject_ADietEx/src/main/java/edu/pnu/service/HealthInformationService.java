package edu.pnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.HealthInformation;
import edu.pnu.persistence.HealthInformationRepository;

@Service
public class HealthInformationService {

    private final HealthInformationRepository healthInformationRepository;

    @Autowired
    public HealthInformationService(HealthInformationRepository healthInformationRepository) {
        this.healthInformationRepository = healthInformationRepository;
    }

    public Optional<HealthInformation> getHealthInformationById(Long healthInformationId) {
        return healthInformationRepository.findById(healthInformationId);
    }

    public HealthInformation createHealthInformation(HealthInformation healthInformation) {
        return healthInformationRepository.save(healthInformation);
    }

    public HealthInformation updateHealthInformation(Long healthInformationId, HealthInformation updatedHealthInformation) {
        Optional<HealthInformation> existingHealthInformation = healthInformationRepository.findById(healthInformationId);
        if (existingHealthInformation.isPresent()) {
            HealthInformation healthInformationToUpdate = existingHealthInformation.get();
            healthInformationToUpdate.setAge(updatedHealthInformation.getAge());


            return healthInformationRepository.save(healthInformationToUpdate);
        } else {
            // 해당 ID에 해당하는 HealthInformation이 없을 경우 예외 처리
            throw new RuntimeException("HealthInformation not found with id: " + healthInformationId);
        }
    }

    public void deleteHealthInformation(Long healthInformationId) {
        healthInformationRepository.deleteById(healthInformationId);
    }
}