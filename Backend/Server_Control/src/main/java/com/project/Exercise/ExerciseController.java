package com.project.Exercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author Ryan Ingram
 *
 */
@RestController
@RequestMapping("/exercise")
public class ExerciseController {

	@Autowired
	private ExerciseService exerciseService;
	
	/**
	 * Retrieves an exercise given the exerciseId
	 * @param exerciseId
	 * @return
	 */
	@RequestMapping("/{exerciseId}")
	public Exercise getExercise(@PathVariable int exerciseId) {
		return exerciseService.getExercise(exerciseId);
	}
	
	/**
	 * Returns true of exerciseName is already in table
	 * @param exerciseName
	 * @return
	 */
	@RequestMapping("/check/{exerciseName}")
	public exerciseResponse existsByName(@PathVariable String exerciseName) {
		return exerciseService.existsByName(exerciseName);
	}
	
	/**
	 * Retrieves all avaiable exercises
	 * @return
	 */
	@RequestMapping("/all")
	public List<Exercise> getAllExercises() {
		return exerciseService.getAllExercises();
	}
	
	/**
	 * Adds an exercise
	 * @param exercise
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addExercise(@RequestBody Exercise exercise) {
		exerciseService.addExercise(exercise);
	}
}
