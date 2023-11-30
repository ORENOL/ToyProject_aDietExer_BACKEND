package edu.pnu.persistence;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.Member;
import edu.pnu.domain.Slot;



public interface DatehistoryRepository extends JpaRepository<Datehistory, Long> {	
	
	Optional<Datehistory> findByDateAndSlot(Date date, Slot slot);
	
	Optional<Datehistory> findByDateAndSlotAndMember(Date date, Slot slot, Member member);
	
	List<Datehistory> findByDate(Date date);
}
