package com.project.classHistory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class classHistoryService {

	
	@Autowired
	private classHistoryRepository classHistoryRepo;
	
	public void addHistory(classHistory classHistory) {
		classHistoryRepo.save(classHistory);
	}
	
	public List<classHistory> getUserHistoryAll(int userId) {
		return classHistoryRepo.findAllByUserId(userId);
	}
	
	public List<classHistory> getUserHistoryDay(int userId, String date) {
		return classHistoryRepo.findAllByUserIdAndDate(userId, date);
	}
	
}
