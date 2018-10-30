package com.project.userExercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface userExerciseRepository extends CrudRepository<userExercise, Integer> {

	
	// getExercises(int date, int user)
	// addExercise(int date, int user)
	// deleteExercise(int date, int user)
	// updateExercise(int date, int user)
	
	/*
	@Query(value = "SELECT * FROM userExercise WHERE(userId = :userId AND date = :date)", nativeQuery = true)
	public List<userExercise> getUserExercises(@Param("userId")int userId, @Param("date")int date);
	*/
	
	@Query(value = "SELECT exerciseId, sets, reps, complete FROM user_exercise ue WHERE (ue.day = :day) AND (ue.userId = :ID)", nativeQuery = true)
	public List<userExercise> findByDayUser(@Param("day") int day, @Param("ID") int userId);
}
