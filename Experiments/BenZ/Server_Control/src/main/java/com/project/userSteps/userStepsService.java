package com.project.userSteps;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class userStepsService {
	
	private userStepsRepository stepsRepo;
	
	public List<userSteps> getPastDays(int user_id, int numDays){
		List<userSteps> pastSteps = new ArrayList();
		//TODO
		return pastSteps;
	}
	
	public userSteps getToday(int user_id, int date) {
		this.checkForEntry(user_id, date);
		return stepsRepo.findByDate(user_id, date);
		
	}
	
	public void updateStepEntry(userSteps updated) {
		stepsRepo.save(updated);
	}
	
	public void addNewEntry(userSteps newEntry) {
		stepsRepo.save(newEntry);
	}
	
	public void checkForEntry(int user_id, int date) {
		if(!stepsRepo.existsByDate(user_id, date)) {
			userSteps u = new userSteps(user_id, date, 0);
			this.addNewEntry(u);
		}
	}
	
}
