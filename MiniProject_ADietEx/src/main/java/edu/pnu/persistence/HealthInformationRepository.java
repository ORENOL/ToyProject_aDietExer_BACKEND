package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;


import edu.pnu.domain.HealthInformation;
import java.util.List;
import java.util.Optional;

import edu.pnu.domain.Member;


public interface HealthInformationRepository extends JpaRepository<HealthInformation, Long> {

	Optional<HealthInformation> findByMember(Member member);
}
