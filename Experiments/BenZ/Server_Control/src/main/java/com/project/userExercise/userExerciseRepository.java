package com.project.userExercise;

import java.util.List;
import com.project.Exercise.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface userExerciseRepository extends JpaRepository<userExercise, Integer>, CrudRepository<userExercise, Integer> {
	
	public List<userExercise> findAllByUserIdAndPlanNameAndDay(int userId, String planName, int day);
	
	public List<userExercise> findAllByUserIdAndPlanName(int userId, String planName);
	
	/*
	@Modifying
	@Transactional
	@Query(value = "UPDATE user")
	public void updateUserExercise(@Param("userId") int user_id, @Param("exerciseId") int exercise_id);
	*/
	
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO user_exercise VALUES (entry, :day, :exerciseId, :planName, :reps, :sets, :userId)", nativeQuery = true)
	public void addUserExercise(@Param("userId") int userId,
			@Param("planName") String planName,
			@Param("day") int day,
			@Param("exerciseId") int exerciseId,
			@Param("sets") int sets,
			@Param("reps") int reps);
	
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM user_exercise WHERE (user_id = :userId AND exercise_id = :exerciseId)", nativeQuery = true)
	public void removeExercise(@Param("userId") int userId, @Param("exerciseId") int exerciseId);
}
