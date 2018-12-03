package com.project.userSteps;




import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.websocket.WebSocketServer;
import com.project.utilities.DateController;
/**
 * 
 * @author Ben Zaley
 *
 */
@Service
public class userStepsService {

	@Autowired
	private userStepsRepository stepsRepo;
	/**
	 * return steps for current day
	 * @param userId
	 * @param date
	 * @return
	 */
	public userSteps getToday(int userId, String date) {
		this.checkForEntry(userId, date);
		return stepsRepo.findByUserIdAndDate(userId, date);
	}
	/**
	 * update step entry for given date
	 * @param userId
	 * @param date
	 * @param steps
	 */
	public void updateStepEntry(int userId, String date, int steps) {
		stepsRepo.updateSteps(userId, date, steps);
	}
	/**
	 * update step goal for given date
	 * @param userId
	 * @param date
	 * @param stepGoal
	 */
	public void updateStepGoal(int userId, String date, int stepGoal) {
		stepsRepo.updateStepGoal(userId, date, stepGoal);
	}
	/**
	 * add new step entry
	 * @param newEntry
	 */
	public void addNewEntry(userSteps newEntry) {
		stepsRepo.save(newEntry);
	}
	/**
	 * update that goal is met for a given date
	 * @param userId
	 * @param date
	 * @param accomplished
	 */
	public void updateIsGoalMet(int userId, String date, boolean accomplished) {
		stepsRepo.setGoalsMet(userId, date, accomplished);
	}
	/**
	 * check given date for step entry
	 * @param userId
	 * @param date
	 */
	public void checkForEntry(int userId, String date) {
		if(!stepsRepo.existsByUserIdAndDate(userId, date)) {
			userSteps u = new userSteps(userId, date, 0, 1000);
			this.addNewEntry(u);
		}
	}
	/**
	 * return a list of multiple step goals for a given amount of days starting from current day.
	 * @param userId
	 * @param numDays
	 * @return
	 */
	public ArrayList<userSteps> getMultipleDays(int userId, int numDays){
		ArrayList<userSteps> stepsOfUser = new ArrayList<userSteps>();
		DateController dateController = new DateController();
		String properDate;
		for(int i = 0; i<numDays; i++) {
			properDate=dateController.returnWorkingDateAsString();
			stepsOfUser.add(stepsRepo.findByUserIdAndDate(userId, properDate));
			dateController.subtractDay();
		}
		return stepsOfUser;
	}
	/**
	 * check current steps vs daily step goal
	 * @param userId
	 * @param date
	 * @return
	 */
	public boolean compareStepsVsGoal(int userId, String date) {
		
		userSteps user = stepsRepo.findByUserIdAndDate(userId, date);
		if(user.getSteps()>=user.getStepGoal()) {
			return true;
		}else {
			return false;
		}
		
		
	}
}
