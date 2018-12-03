package com.project.classHistory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author Ryan Ingram
 *
 */
@Service
public class classHistoryService {

	
	@Autowired
	private classHistoryRepository classHistoryRepo;
	/**
	 * add class to user classHistory table.
	 * @param classHistory
	 */
	public void addHistory(classHistory classHistory) {
		classHistoryRepo.save(classHistory);
	}
	/**
	 * pull all class history for certain user
	 * @param userId
	 * @return
	 */
	public List<classHistory> getUserHistoryAll(int userId) {
		return classHistoryRepo.findAllByUserId(userId);
	}
	/**
	 * get a user's history for a specific day given a userId. 
	 * @param userId
	 * @param date
	 * @return
	 */
	public List<classHistory> getUserHistoryDay(int userId, String date) {
		return classHistoryRepo.findAllByUserIdAndDate(userId, date);
	}
	
}
