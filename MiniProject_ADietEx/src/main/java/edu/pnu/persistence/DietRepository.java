package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.Diet;
import java.util.List;


public interface DietRepository extends JpaRepository<Diet, Long> {
	
	List<Diet> findByDatehistory(Datehistory datehistory);
	
}
