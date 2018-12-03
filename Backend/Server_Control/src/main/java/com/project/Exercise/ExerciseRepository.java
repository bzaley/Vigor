package com.project.Exercise;

import org.springframework.data.repository.CrudRepository;
/**
 * 
 * @author Ryan Ingram
 *
 */
public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {

	/**
	 * 
	 * @param exerciseId
	 * @return
	 */
	public Exercise findByExerciseId(int exerciseId);
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Exercise findByName(String name);
}
