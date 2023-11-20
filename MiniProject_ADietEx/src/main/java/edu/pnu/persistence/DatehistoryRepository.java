package edu.pnu.persistence;



import org.springframework.data.jpa.repository.JpaRepository;


import edu.pnu.domain.Datehistory;
import java.util.List;
import edu.pnu.domain.Slot;


public interface DatehistoryRepository extends JpaRepository<Datehistory, Long> {	
	
	Datehistory findByDateAndAndSlot(String date, Slot slot);
}
