package com.project.userSteps;




import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.websocket.WebSocketServer;
import com.project.utilities.DateController;

@Service
public class userStepsService {

	@Autowired
	private userStepsRepository stepsRepo;

	public userSteps getToday(int userId, String date) {
		this.checkForEntry(userId, date);
		return stepsRepo.findByUserIdAndDate(userId, date);
	}

	public void updateStepEntry(int userId, String date, int steps) {
		stepsRepo.updateSteps(userId, date, steps);
	}
	
	public void updateStepGoal(int userId, String date, int stepGoal) {
		stepsRepo.updateStepGoal(userId, date, stepGoal);
	}

	public void addNewEntry(userSteps newEntry) {
		stepsRepo.save(newEntry);
	}
	
	public void updateIsGoalMet(int userId, String date, boolean accomplished) {
		stepsRepo.setGoalsMet(userId, date, accomplished);
	}

	public void checkForEntry(int userId, String date) {
		if(!stepsRepo.existsByUserIdAndDate(userId, date)) {
			userSteps u = new userSteps(userId, date, 0, 1000);
			this.addNewEntry(u);
		}
	}

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
	
	public boolean compareStepsVsGoal(int userId, String date) {
		
		userSteps user = stepsRepo.findByUserIdAndDate(userId, date);
		if(user.getSteps()>=user.getStepGoal()) {
			return true;
		}else {
			return false;
		}
		
		
	}
}
