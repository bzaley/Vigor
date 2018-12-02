package com.project.classHistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class classHistoryService {

	
	@Autowired
	private classHistoryRepository classHistoryRepo;
	
	public void addHistory(classHistory classHistory) {
		
		classHistoryRepo.save(classHistory);
		
	}
	
	
}
