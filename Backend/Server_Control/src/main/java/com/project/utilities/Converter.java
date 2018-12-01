package com.project.utilities;

import com.project.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.Exercise.*;

public class Converter {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ExerciseRepository exRepo;
	
	
	public int convertEmailToId(String email) {
		
		User user = userRepo.findByUserEmail(email);
		return user.getuserId();
		
	}
	
	public String convertIdToEmail(int id) {
		
		User user = userRepo.findByUserId(id);
		return user.getuserEmail();
		
	}
	
	
	public String convertIdToExercise(int id) {
		
		Exercise exercise = exRepo.findByExerciseId(id);
		return exercise.getName();
		
	}
	
	public int convertExerciseToId(String name) {
		
		Exercise exercise = exRepo.findByName(name);
		return exercise.getExerciseId();
		
	}
	
}
