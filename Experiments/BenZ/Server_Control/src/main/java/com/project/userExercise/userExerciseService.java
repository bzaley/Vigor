package com.project.userExercise;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import com.project.Exercise.*;
import com.project.plan.*;
import com.project.historian.*;

import com.project.Exercise.Exercise;

@Service
public class userExerciseService {
	
	
	@Autowired
	private userExerciseRepository userExerciseRepo;
	
	@Autowired
	private ExerciseRepository exerciseRepo;
	
	@Autowired
	private planRepository planRepo;
	
	@Autowired
	private historianRepository historianRepo;
	
	
	/*
	 *  Adds a userExercise entry to the userExercise table.
	 *  Must first convert the given exercise to its corresponding id.
	 */
	public void addUserSingleExercise(userAddEntry userAddEntry) {
		Exercise exercise = exerciseRepo.findByName(userAddEntry.getExercise()); // Retrieves exercise object based off the userAddEntry
		int id = exercise.getExerciseId(); // Stores the exerciseId of the exercise object 
		userExercise userExercise = new userExercise( // Creates a userExercise object with the correct info
				userAddEntry.getUserId(),
				"",
				-1,
				id,
				userAddEntry.getSets(),
				userAddEntry.getReps());
		userExerciseRepo.addUserExercise(userExercise.getUserId(), userExercise.getPlanName(), userExercise.getDay(), userExercise.getExerciseId(), userExercise.getSets(), userExercise.getReps()); // Adds the userExercise to the table
	}
	
	
	
	public void addUserPlanExercises(List<userAddEntry> plan) {
		
		for(userAddEntry tmp : plan) { // Selects each individual exercise in plan and puts them into tmp
			
			Exercise exercise = exerciseRepo.findByName(tmp.getExercise()); // Retrieves exercise object based off the userAddEntry
			int id = exercise.getExerciseId(); // Stores the exerciseId of the exercise object 
			userExercise userExercise = new userExercise( // Creates a userExercise object with the correct info
					tmp.getUserId(),
					tmp.getPlanName(),
					tmp.getDay(),
					id,
					tmp.getSets(),
					tmp.getReps());
			
			userExerciseRepo.addUserExercise(userExercise.getUserId(), userExercise.getPlanName(), userExercise.getDay(), userExercise.getExerciseId(), userExercise.getSets(), userExercise.getReps());
		}
		
		// Enters in the new plan and starts the day at 1
		plan day_track = new plan(
				plan.get(0).getUserId(),
				plan.get(0).getPlanName(),
				1);
		planRepo.save(day_track);
	}
	
	/*
	 *  Takes in users id and the planName. Using this information the current day of the plan is retrieved
	 *  the execises are then converted to the front end form
	 *  
	 *  For now I am saying plan name matters and will only show on phone what exercises for single
	 *  ****** Plan Name does matter for future possibly?? For case that you are following 2 plans ******
	 */
	public List<userEntry> getExercisesForPlan(int userId, String planName) {
		
		// Get the current day from the plan table by userid and planname
		int current_day = planRepo.findByUserIdAndPlanName(userId, planName).getCurrentDay();
		
		Exercise exercise;
		String exercise_name;
		List<userEntry> right = new ArrayList<userEntry>();
		
		List<userExercise> wrong = userExerciseRepo.findAllByUserIdAndPlanNameAndDay(userId, planName, current_day);
		
		for(userExercise tmp : wrong) {
			
			exercise = exerciseRepo.findByExerciseId(tmp.getExerciseId());
			exercise_name = exercise.getName();
			
			userEntry userEntry = new userEntry(
					tmp.getUserId(),
					tmp.getPlanName(),
					exercise_name,
					tmp.getSets(),
					tmp.getReps());
			
			right.add(userEntry);
		}
		
		return right;
	}
	
	
	public List<userEntry> getExercises(int userId) {
		
		List<userExercise> wrong = userExerciseRepo.findAllByUserIdAndPlanName(userId, "");
		
		Exercise exercise;
		String exercise_name;
		List<userEntry> right = new ArrayList<userEntry>();
		
		for (userExercise tmp : wrong) {
			
			exercise = exerciseRepo.findByExerciseId(tmp.getExerciseId());
			exercise_name = exercise.getName();
			
			userEntry userEntry = new userEntry(
					tmp.getUserId(),
					tmp.getPlanName(),
					exercise_name,
					tmp.getSets(),
					tmp.getReps());
			
			right.add(userEntry);
		}
		
		return right;
	}
	
	/*
	 * Removes a userExercise **** Need the day from user *****
	 * int userId, int exerciseId
	 */
	public void removeUserExercise(userEntry userEntry) {
		
		Exercise exercise = exerciseRepo.findByName(userEntry.getExercise()); // Retrieves exercise object based off the userAddEntry
		int id = exercise.getExerciseId(); // Stores the exerciseId of the exercise object
		// userId, exerciseId
		userExerciseRepo.removeExercise(userEntry.getUserId(), id);
	}
	
	
	//@RequestBody /userExercise/update
	//public void updateUserExercise(userEntry userEntry) {
		
		
	//}
	
	// pull from bens project
	public void markComplete(userEntry userEntry, boolean remove) { // uses dateController
		
		// Date controller saves current date in saveDate
		String saveDate = "yyyy-mm-dd";
		
		Exercise exercise = exerciseRepo.findByName(userEntry.getExercise()); // Retrieves exercise object based off the userAddEntry
		int id = exercise.getExerciseId(); // Stores the exerciseId of the exercise object 
		
		if (remove) {
			historianRepo.addHistory(userEntry.getUserId(), id, userEntry.getSets(), userEntry.getReps(), saveDate);
			
			//removeUserExercise(userEntry.getUserId(), id);
			removeUserExercise(userEntry);
			
		} else {
			historianRepo.addHistory(userEntry.getUserId(), id, userEntry.getSets(), userEntry.getReps(), saveDate);
		}
		
	}
	
	public void nextDay() {
		
	}
	
	public void prevDay() {
		
	}
	

}
