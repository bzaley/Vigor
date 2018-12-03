package com.project.userPlan;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author Ryan Ingram
 *
 */
public interface userPlanRepository  extends CrudRepository<userPlan, Integer> {
	
	/**
	 *
	 * @param userId
	 * @param planName
	 * @param day
	 * @return
	 */
	public List<userPlan> findAllByUserIdAndPlanNameAndDay(int userId, String planName, int day);
	/**
	 *
	 * @param userId
	 * @param planName
	 * @return
	 */
	public List<userPlan> findAllByUserIdAndPlanName(int userId, String planName);
    @Transactional
    public void deleteByUserId(int userId);
	/**
	 * add exercise to userPlan table.
	 * @param userId
	 * @param planName
	 * @param day
	 * @param exerciseId
	 * @param sets
	 * @param reps
	 */
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
