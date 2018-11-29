package com.project.dayExercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface dayExerciseRepository extends JpaRepository<dayExercise, Integer>, CrudRepository<dayExercise, Integer> {
	
	public List<dayExercise> findAllByUserId(int userId);
	
	public List<dayExercise> findAllByUserIdAndPlanName(int userId, String planName);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE day_exercise de SET de.sets = :sets, de.reps = :reps, de.complete = :complete WHERE(de.user_id = :userId AND de.plan_name = :planName AND de.exercise_id = :exerciseId)", nativeQuery = true)
	public void markComplete(
			@Param("userId") int userId,
			@Param("planName") String planName,
			@Param("complete") boolean complete,
			@Param("exerciseId") int exerciseId,
			@Param("sets") int sets,
			@Param("reps") int reps);
	/*
	@Modifying
	@Transactional
	@Query(value = "UPDATE day_exercise de SET de.complete = :complete WHERE(de.user_id = :userId AND de.plan_name = :planName AND de.exercise_id = :exerciseId", nativeQuery = true)
	public void test(
			@Param("userId") int userId,
			@Param("planName") String planName,
			@Param("complete") boolean complete,
			@Param("exerciseId") int exerciseId);*/
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO day_exercise VALUES(entry, :complete, :exerciseId, :planName, :reps, :sets, :userId)", nativeQuery = true)
	public void addExercise(
			@Param("userId") int userId,
			@Param("planName") String planName,
			@Param("complete") boolean complete,
			@Param("exerciseId") int exerciseId,
			@Param("sets") int sets,
			@Param("reps") int reps);
	

}
