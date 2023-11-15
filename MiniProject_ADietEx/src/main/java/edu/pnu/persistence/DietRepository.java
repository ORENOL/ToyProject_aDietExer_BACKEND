package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Diet;

public interface DietRepository extends JpaRepository<Diet, Long> {

}
