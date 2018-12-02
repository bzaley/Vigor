package com.project.Exercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

	@Autowired
	private ExerciseService exerciseService;
	
	/*
	 * Retrieves the exercise name given the exercise ID 
	 */
	@RequestMapping("/{exerciseId}")
	public Exercise getExercise(@PathVariable int exerciseId) {
		return exerciseService.getExercise(exerciseId);
	}
	
	/*
	 * Returns true if the exercise name is already in the table
	 */
	@RequestMapping("/check/{exerciseName}")
	public exerciseResponse existsByName(@PathVariable String exerciseName) {
		return exerciseService.existsByName(exerciseName);
	}
	
	/*
	 * Retrieves all the exercises we have available
	 */
	@RequestMapping("/all")
	public List<Exercise> getAllExercises() {
		return exerciseService.getAllExercises();
	}
	
	/*
	 * Adds an exercise
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addExercise(@RequestBody Exercise exercise) {
		exerciseService.addExercise(exercise);
	}
}
