package com.project.userPlan;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface userPlanRepository  extends CrudRepository<userPlan, Integer> {
	
	
	public List<userPlan> findAllByUserIdAndPlanNameAndDay(int userId, String planName, int day);
	

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO user_plan VALUES (entry, :day, :exerciseId, :planName, :reps, :sets, :userId)", nativeQuery = true)
	public void addUserExercise(
			@Param("userId") int userId,
			@Param("planName") String planName,
			@Param("day") int day,
			@Param("exerciseId") int exerciseId,
			@Param("sets") int sets,
			@Param("reps") int reps);
}
