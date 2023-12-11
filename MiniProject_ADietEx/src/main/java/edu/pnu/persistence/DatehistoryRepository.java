package edu.pnu.persistence;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.Datehistory;
import edu.pnu.domain.Member;
import edu.pnu.domain.enums.Slot;



public interface DatehistoryRepository extends JpaRepository<Datehistory, Long> {	
	
	Optional<Datehistory> findByDateAndSlotAndMemberUsername(Date date, Slot slot, String username);
	
	Optional<Datehistory> findByDateAndSlotAndMember(Date date, Slot slot, Member member);
	
	List<Datehistory> findByDateAndMemberUsername(Date date, String username);
	
	@Query(value = "select d.date from datehistory d where d.member_username = :username and d.date BETWEEN DATE_SUB(:date, INTERVAL 7 DAY) AND DATE_ADD(:date, INTERVAL 7 DAY) GROUP by d.date order by d.date", nativeQuery = true)
	List<java.sql.Date> findDateByDate(@Param("date") Date date, @Param("username") String username);
	
	@Query(value = "select max(d.weight) from datehistory d where d.member_username = :username and d.date BETWEEN DATE_SUB(:date, INTERVAL 7 DAY) AND DATE_ADD(:date, INTERVAL 7 DAY) GROUP by d.date order by d.date", nativeQuery = true)
	List<Float> findWeightByDate(@Param("date") Date date, @Param("username") String username);
}
