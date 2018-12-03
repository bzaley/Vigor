package com.project.trainerPlan;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.project.userPlan.userPlan;

/**
 *
 * @author Ryan Ingram
 *
 */
public interface trainerPlanRepository extends CrudRepository<trainerPlan, Integer> {

	/**
	 *
	 * @param userId
	 * @param planName
	 * @param day
	 * @return
	 */
	public List<trainerPlan> findAllByUserIdAndPlanNameAndDay(int userId, String planName, int day);
	
	/**
	 *
	 * @param userId
	 * @param planName
	 * @return
	 */
	public List<trainerPlan> findAllByUserIdAndPlanName(int userId, String planName);
   /**
    * 
    * @param userId
    */
	@Transactional
    public void deleteByUserId(int userId);
	/**
	 * Add trainer exercise to table.
	 * @param trainerId
	 * @param userId
	 * @param planName
	 * @param day
	 * @param exerciseId
	 * @param sets
	 * @param reps
	 */
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
