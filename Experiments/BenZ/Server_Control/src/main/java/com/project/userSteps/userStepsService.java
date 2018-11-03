package com.project.userSteps;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userStepsService {
	
	@Autowired
	private userStepsRepository stepsRepo;
	
	/*public List<userSteps> getPastDays(int userId, int numDays){
		List<userSteps> pastSteps = new ArrayList();
		//TODO
		return pastSteps;
	}*/
	
	public userSteps getToday(int userId, int date) {
		this.checkForEntry(userId, date);
		return stepsRepo.findByUserIdAndDate(userId, date);
		
	}
	
	public void updateStepEntry(int userId, int date, int steps) {
		stepsRepo.updateSteps(userId, date, steps);
		
	}
	
	public void addNewEntry(userSteps newEntry) {
		stepsRepo.save(newEntry);
	}
	
	public void checkForEntry(int userId, int date) {
		if(!stepsRepo.existsByUserIdAndDate(userId, date)) {
			userSteps u = new userSteps(userId, date, 0);
			this.addNewEntry(u);
		}
	}
	
}
