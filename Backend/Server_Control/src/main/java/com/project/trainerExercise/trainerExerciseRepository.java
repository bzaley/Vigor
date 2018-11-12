package com.project.trainerExercise;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface trainerExerciseRepository extends CrudRepository<trainerExercise, Integer> {

	
	public List<trainerExercise> findAllByUserIdAndPlanNameAndDay(int userId, String planName, int day);
	
	public List<trainerExercise> findAllByUserIdAndPlanName(int userId, String planName);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO trainer_exercise VALUES (entry, :day, :exerciseId, :planName, :reps, :sets, :trainerId, :userId)", nativeQuery = true)
	public void addTrainerExercise(
			@Param("trainerId") int trainerId,
			@Param("userId") int userId,
			@Param("planName") String planName,
			@Param("day") int day,
			@Param("exerciseId") int exerciseId,
			@Param("sets") int sets,
			@Param("reps") int reps);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM trainer_exercise WHERE (user_id = :userId AND exercise_id = :exerciseId AND plan_name = :planName)", nativeQuery = true)
	public void removeTrainerExercise(@Param("userId") int userId, @Param("exerciseId") int exerciseId, @Param("planName") String planName);
	
}
