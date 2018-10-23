package com.project.Exercise;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class ExerciseController {

	
	private ExerciseService exerciseService;
	
	@RequestMapping("/{exercise")
	public void getExercise(@PathVariable Exercise exercise) {
		exerciseService.getExercise(exercise);
	}
}
