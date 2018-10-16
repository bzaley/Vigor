package com.project.userExercise;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


public class userExerciseService {

	@Autowired
	private userExerciseRepository userExerciseRepository;
	
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
		userExerciseRepository.save(userExercise);
	}

	

}
