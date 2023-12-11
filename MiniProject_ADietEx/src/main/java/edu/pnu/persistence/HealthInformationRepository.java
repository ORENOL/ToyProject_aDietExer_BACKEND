package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.HealthInformation;
import edu.pnu.domain.Member;


public interface HealthInformationRepository extends JpaRepository<HealthInformation, Long> {

	Optional<HealthInformation> findByMember(Member member);
}
