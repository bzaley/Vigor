package com.project.userExercise;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Exercise.*;

import com.project.Exercise.Exercise;

@Service
public class userExerciseService {
	
	
	@Autowired
	private userExerciseRepository userExerciseRepo;
	
	@Autowired
	private ExerciseRepository exerciseRepo;
	
	
	/*
	 *  Adds a userExercise entry to the userExercise table.
	 *  Must first convert the given exercise to its corresponding id.
	 */
	public void addUserSingleExercise(userAddEntry userAddEntry) {
		Exercise exercise = exerciseRepo.findByName(userAddEntry.getExercise()); // Retrieves exercise object based off the userAddEntry
		int id = exercise.getExerciseId(); // Stores the exerciseId of the exercise object 
		userExercise userExercise = new userExercise( // Creates a userExercise object with the correct info
				userAddEntry.getUserId(),
				userAddEntry.getPlanName(),
				userAddEntry.getDay(),
				id,
				userAddEntry.getSets(),
				userAddEntry.getReps(),
				userAddEntry.getSaveDate());
		userExerciseRepo.save(userExercise); // Adds the userExercise to the table
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
					tmp.getReps(),
					tmp.getSaveDate());
			
			userExerciseRepo.save(userExercise);
		}
	}
	
	/*
	 *  Takes in users id and the day that they want. Converts the userExercise to a way that the
	 *  front end can interpret. Returns a list of user entries
	 */
	public List<userEntry> getExercisesForDay(int userId, String planName) {
		
		// Get the current day from the plan table by userid and planname
		int current_day = 1; // temporary till plan table is built
		
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
					tmp.getReps(),
					tmp.getSaveDate());
		}
	}
	
	/*
	 * Removes a userExercise
	 */
	public void removeUserExercise(userEntry userEntry) {
		
		Exercise exercise = exerciseRepo.findByName(userEntry.getExercise());
		int id = exercise.getExerciseId();
		userExercise userExercise = new userExercise(
				userEntry.getUserId(),
				userEntry.getDate(),
				id,
				userEntry.getSets(),
				userEntry.getReps(),
				userEntry.isComplete());
		
		userExerciseRepo.delete(userExercise);
	}
	
	
	@RequestBody /userExercise/update
	public void updateUserExercise(userEntry userEntry) {
		
		
	}
	
	
	public void markComplete(userExercise userExercise) {
		
		// Step 1: Retrieve all needed data from userExercise
		// Step 2: Delete the entry in userExercise table
		// Step 3: Put all retrieved data into new object for historian
		// Step 4: Insert into historian
		
	}
}
