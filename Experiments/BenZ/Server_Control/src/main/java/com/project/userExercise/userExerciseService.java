package com.project.userExercise;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userExerciseService {

	@Autowired
	private userExerciseRepository exerciseRepo;
	
	/*
	 * Get everything from exercises
	 * Useless but good example
	 */
	/*public List<userExercise> getAllExercises() {
		// TO-DO
		// Get all exercises for given date
		List<userExercise> userExercises = new ArrayList<>();
		userExerciseRepository.findAll()
		.forEach(userExercises::add);
		return userExercises;
	}*/
	
	public void addExercise(userExercise userExercise) {
		exerciseRepo.save(userExercise);
	}

	public void removeExercise(userExercise userExercise) {
		exerciseRepo.delete(userExercise);
	}
	
	public void updateExercise(userExercise userExercise){
		exerciseRepo.save(userExercise);
	}
	/*public userExercise getExercises(int date, int userId) {
		userExerciseRepository.getUserExercises(date, userId);
	}*/

	

}
