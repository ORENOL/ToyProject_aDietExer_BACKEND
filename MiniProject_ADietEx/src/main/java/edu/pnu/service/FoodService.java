package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Diet;
import edu.pnu.domain.Member;
import edu.pnu.persistence.DietRepository;

@Service
public class FoodService {
	
	@Autowired
	private DietRepository dietRepo;

	public void addFood(Diet diet) {
		dietRepo.save(diet);
	}

	public void deleteDiet(Diet diet) {
		dietRepo.delete(diet);
	}

	public List<Diet> getAllDiet() {
		//dietRepo.findByMember_id(username);
		return null;
	}

}
