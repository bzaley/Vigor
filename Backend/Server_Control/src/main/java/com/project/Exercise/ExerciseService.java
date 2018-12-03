package com.project.Exercise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author Ryan Ingram
 *
 */
@Service
public class ExerciseService {
	
	@Autowired
	private ExerciseRepository exerciseRepo;

	/**
	 * get exercise based on the exerciseId
	 * @param exerciseId
	 * @return
	 */
	public Exercise getExercise(int exerciseId) {
		return exerciseRepo.findByExerciseId(exerciseId);
	}
	/**
	 * get ArrayList of all available exercises in the table
	 * @return
	 */
	public List<Exercise> getAllExercises() {
		List<Exercise> exercises = new ArrayList<Exercise>();
		exerciseRepo.findAll().forEach(exercises::add);
		return exercises;
	}
	/**
	 * Add an exercise to the database.
	 * @param exercise
	 */
	public void addExercise(Exercise exercise) {
		exerciseRepo.save(exercise);
	}
	/**
	 * Check if an exercise is present in the table by its name.
	 * @param exerciseName
	 * @return
	 */
	public boolean existsByName(String exerciseName) {
		Exercise exercise = exerciseRepo.findByName(exerciseName);

		if (exercise == null) {
			return false;
		} else {
			return true;
		}

	}
	
}
