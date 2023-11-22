package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;


import edu.pnu.domain.HealthInformation;

public interface HealthInformationRepository extends JpaRepository<HealthInformation, Long> {

}
