package com.project.trainerPlan;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.project.userPlan.userPlan;


public interface trainerPlanRepository extends CrudRepository<trainerPlan, Integer> {

	
	public List<trainerPlan> findAllByUserIdAndPlanNameAndDay(int userId, String planName, int day);
	
	
	public List<trainerPlan> findAllByUserIdAndPlanName(int userId, String planName);
	
	public void deleteByUserId(int userId);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO trainer_plan VALUES (entry, :day, :exerciseId, :planName, :reps, :sets, :trainerId, :userId)", nativeQuery = true)
	public void addTrainerExercise(
			@Param("trainerId") int trainerId,
			@Param("userId") int userId,
			@Param("planName") String planName,
			@Param("day") int day,
			@Param("exerciseId") int exerciseId,
			@Param("sets") int sets,
			@Param("reps") int reps);
	
}
