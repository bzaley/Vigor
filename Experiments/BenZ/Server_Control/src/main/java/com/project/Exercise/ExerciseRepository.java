package com.project.Exercise;

import org.springframework.data.repository.CrudRepository;

public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {

	
	public Exercise findByExerciseId(int exerciseId);
	
	public Exercise findByName(String name);
}
