package com.project.Exercise;

import org.springframework.data.repository.CrudRepository;

public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {

	Object findByExercise();

}
