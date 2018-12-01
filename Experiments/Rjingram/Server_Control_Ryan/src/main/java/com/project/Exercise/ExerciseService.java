package com.project.Exercise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {

	@Autowired
	private ExerciseRepository exerciseRepo;

	
	public Exercise getExercise(int exerciseId) {
		return exerciseRepo.findByExerciseId(exerciseId);
	}
	
	public List<Exercise> getAllExercises() {
		List<Exercise> exercises = new ArrayList<Exercise>();
		exerciseRepo.findAll().forEach(exercises::add);
		return exercises;
	}
	
	public void addExercise(Exercise exercise) {
		exerciseRepo.save(exercise);
	}
	
	public boolean existsByName(String exerciseName) {
		Exercise exercise = exerciseRepo.findByName(exerciseName);
		
		if (exercise == null) {
			return false;
		} else {
			return true;
		}
		
	}
	
}
