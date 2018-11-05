package com.project.trainerExercise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Exercise.*;
import com.project.plan.*;
import com.project.user.*;
import com.project.userExercise.good;
import com.project.utilities.*;
import com.project.historian.*;

@Service
public class trainerExerciseService {

	@Autowired
	private trainerExerciseRepository trainerExerciseRepo;
	
	@Autowired
	private ExerciseRepository exerciseRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private planRepository planRepo;
	
	@Autowired
	private historianRepository historianRepo;

	
	
	
	public void addTrainerExercise(trainerAddEntry trainerAddEntry) {
		
		Exercise exercise = exerciseRepo.findByName(trainerAddEntry.getExercise()); // Retrieves exercise object based off the userAddEntry
		int exid = exercise.getExerciseId();
		
		User user = userRepo.findByUserEmail(trainerAddEntry.getEmail());
		int uid = user.getuserId();
		
		
		trainerExercise trainerExercise = new trainerExercise( // Creates a userExercise object with the correct info
				trainerAddEntry.getTrainerId(),
				uid,
				"",
				-1,
				exid,
				trainerAddEntry.getSets(),
				trainerAddEntry.getReps());
		
		trainerExerciseRepo.addTrainerExercise(trainerExercise.getTrainerId(), trainerExercise.getUserId(), trainerExercise.getPlanName(), trainerExercise.getDay(), trainerExercise.getExerciseId(), trainerExercise.getSets(), trainerExercise.getReps());
		
	}

	
	
	
	public void addTrainerPlan(List<trainerAddEntry> plan) {
		
		int max = 0;
		
		for (trainerAddEntry tmp : plan) {
			
			Exercise exercise = exerciseRepo.findByName(tmp.getExercise()); // Retrieves exercise object based off the userAddEntry
			int exid = exercise.getExerciseId();
			
			User user = userRepo.findByUserEmail(tmp.getEmail());
			int uid = user.getuserId();
			
			
			trainerExercise trainerExercise = new trainerExercise( // Creates a userExercise object with the correct info
					tmp.getTrainerId(),
					uid,
					tmp.getPlanName(),
					tmp.getDay(),
					exid,
					tmp.getSets(),
					tmp.getReps());
			
			trainerExerciseRepo.addTrainerExercise(trainerExercise.getTrainerId(), trainerExercise.getUserId(), trainerExercise.getPlanName(), trainerExercise.getDay(), trainerExercise.getExerciseId(), trainerExercise.getSets(), trainerExercise.getReps());
			
		}
		
		User user = userRepo.findByUserEmail(plan.get(0).getEmail());
		int uid = user.getuserId();
		
		// Get the value of maxDay
		max = plan.get(plan.size()-1).getDay();
		
		// Enters in the new plan and starts the day at 1
		plan day_track = new plan(
				uid,
				plan.get(0).getPlanName(),
				1,
				max);
		planRepo.save(day_track);
		
		
	}
	
	
	
	
	

	public List<trainerEntry> getTrainerExercise(int userId) {
		
		List<trainerExercise> wrong = trainerExerciseRepo.findAllByUserIdAndPlanName(userId, "");
		
		Exercise exercise;
		String exercise_name;
		User user;
		String email;
		List<trainerEntry> right = new ArrayList<trainerEntry>();
		
		for (trainerExercise tmp : wrong) {
			
			exercise = exerciseRepo.findByExerciseId(tmp.getExerciseId());
			exercise_name = exercise.getName();
			
			user = userRepo.findByUserId(tmp.getUserId());
			email = user.getuserEmail();
			
			trainerEntry trainerEntry = new trainerEntry(
					tmp.getTrainerId(),
					email,
					tmp.getPlanName(),
					exercise_name,
					tmp.getSets(),
					tmp.getReps());
			
			right.add(trainerEntry);
		}
		
		return right;
	}

	public List<trainerEntry> getTrainerPlanExercises(int userId, String planName) {
		
		
		// Get the current day from the plan table by userid and planname
		int current_day = planRepo.findByUserIdAndPlanName(userId, planName).getCurrentDay();
				
		Exercise exercise;
		String exercise_name;
		User user;
		String email;
		List<trainerEntry> right = new ArrayList<trainerEntry>();
				
		List<trainerExercise> wrong = trainerExerciseRepo.findAllByUserIdAndPlanNameAndDay(userId, planName, current_day);
		
		for(trainerExercise tmp : wrong) {
					
			exercise = exerciseRepo.findByExerciseId(tmp.getExerciseId());
			exercise_name = exercise.getName();
			
			user = userRepo.findByUserId(tmp.getUserId());
			email = user.getuserEmail();
					
			trainerEntry trainerEntry = new trainerEntry(
					tmp.getTrainerId(),
					email,
					tmp.getPlanName(),
					exercise_name,
					tmp.getSets(),
					tmp.getReps());
					
				right.add(trainerEntry);
			}
				
		return right;
	}




	public void removeTrainerExercise(trainerEntry trainerEntry) {
		
		Exercise exercise = exerciseRepo.findByName(trainerEntry.getExercise()); // Retrieves exercise object based off the userAddEntry
		int exid = exercise.getExerciseId(); // Stores the exerciseId of the exercise object
		
		User user = userRepo.findByUserEmail(trainerEntry.getEmail());
		int uid = user.getuserId();
		
		// userId, exerciseId
		trainerExerciseRepo.removeTrainerExercise(uid, exid);
		
	}




	public void markSave(trainerEntry trainerEntry) {
		
		// Date controller saves current date in saveDate
		DateController controller = new DateController();
		String saveDate = controller.returnWorkingDateAsString();
				
		Exercise exercise = exerciseRepo.findByName(trainerEntry.getExercise()); // Retrieves exercise object based off the userAddEntry
		int exid = exercise.getExerciseId(); // Stores the exerciseId of the exercise object

		User user = userRepo.findByUserEmail(trainerEntry.getEmail());
		int uid = user.getuserId();
		
		String plan = trainerEntry.getPlanName();
				
		if (plan == "") {
			historianRepo.addHistory(uid, exid, trainerEntry.getSets(), trainerEntry.getReps(), saveDate);
			trainerExerciseRepo.removeTrainerExercise(uid, exid);
		} else {
			historianRepo.addHistory(uid, exid, trainerEntry.getSets(), trainerEntry.getReps(), saveDate);
		}
		
	}



	/*
	 * Increments the current day the user is on of the plan
	 */
	public Object nextDay(int userId, String planName) {

		int new_day = 0;
		plan plan = planRepo.findByUserIdAndPlanName(userId, planName);
		
		if (plan.getCurrentDay() == plan.getMaxDay()) {
			new_day = 1;
		} else {
			new_day = plan.getCurrentDay() + 1;
		}
		
		
		planRepo.updateDay(userId, planName, new_day);
		
		good sendBack = new good("success");
		return sendBack;
	}
	
	/*
	 * Decrements the current day the user is on of the plan
	 */
	public good prevDay(int userId, String planName) {

		int new_day = 0;
		plan plan = planRepo.findByUserIdAndPlanName(userId, planName);
		
		if (plan.getCurrentDay() == 1) {
			new_day = plan.getMaxDay();
		} else {
			new_day = plan.getCurrentDay() - 1;
		}
		
		planRepo.updateDay(userId, planName, new_day);
		
		good sendBack = new good("success");
		
		return sendBack;
	}
	
	
}
		

	

