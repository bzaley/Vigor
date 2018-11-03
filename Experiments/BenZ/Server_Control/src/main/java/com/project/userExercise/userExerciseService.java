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
	public void addUserExercise(userEntry userEntry) {
		Exercise exercise = exerciseRepo.findByName(userEntry.getExercise());
		int id = exercise.getExerciseId();
		userExercise userExercise = new userExercise(
				userEntry.getUserId(),
				userEntry.getDate(),
				id,
				userEntry.getSets(),
				userEntry.getReps(),
				userEntry.isComplete());
		userExerciseRepo.save(userExercise);
	}
	
	
	/*
	 *  Takes in users id and the day that they want. Converts the userExercise to a way that the
	 *  front end can interpret. Returns a list of user entries
	 */
	public List<userEntry> getExercisesForDay(int userId, String date) {
		List<userExercise> wrong = userExerciseRepo.findAllByUserIdAndDate(userId, date);
		
		List<userEntry> right = new ArrayList<userEntry>();
		
		Exercise exercise;
		String name;
		
		for (userExercise tmp : wrong) {
			
			exercise = exerciseRepo.findByExerciseId(tmp.getExerciseId());
			name = exercise.getName();
			userEntry userEntry = new userEntry(
					tmp.getUserId(),
					tmp.getDate(),
					name,
					tmp.getSets(),
					tmp.getReps(),
					tmp.isComplete());
			right.add(userEntry);
		}
		return right;
	}
	
	/*
	public void updateUserExercise(userEntry userEntry) {
		int id = userExerciseRepo.getExerciseIdFromName(userEntry.getExercise());
		userExercise userExercise = new userExercise(
				userEntry.getUser_id(),
				userEntry.getDay_of_week(),
				id,
				userEntry.getSets(),
				userEntry.getReps(),
				userEntry.getComplete_date());
		
	}*/
	
	/*
	public void markComplete(userExercise userExercise) {
		
		// Step 1: Retrieve all needed data from userExercise
		// Step 2: Delete the entry in userExercise table
		// Step 3: Put all retrieved data into new object for historian
		// Step 4: Insert into historian
		
	}*/
}
