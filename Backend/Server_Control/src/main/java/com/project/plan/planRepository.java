package com.project.plan;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
public interface planRepository extends CrudRepository<plan, Integer> {
	/**
	 *
	 * @param userId
	 * @param planName
	 * @return
	 */
	public plan findByUserIdAndPlanName(int userId, String planName);
	/**
	 *
	 * @param userId
	 * @param active
	 * @return
	 */
	public List<plan> findAllByUserIdAndActive(int userId, boolean active);
	/**
	 *
	 * @param userId
	 * @return
	 */
	public List<plan> findAllByUserId(int userId);
    @Transactional
    public void deleteByUserId(int userId);
	/**
	 * update plan with planName and userId.
	 * @param userId
	 * @param planName
	 * @param day
	 */

	@Modifying
	@Transactional
	@Query(value = "UPDATE plan p SET p.current_day = :newDay WHERE(p.user_id = :userID AND p.plan_name = :planName)", nativeQuery = true)
	public void updateDay(@Param("userID")int userId, @Param("planName")String planName, @Param("newDay")int day);
	
	/**
	 * add a plan.
	 * @param userId
	 * @param planName
	 * @param currentDay
	 * @param maxDay
	 * @param active
	 * @param assignedBy
	 */
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO plan VALUES (entry, :active, :assignedBy, :currentDay, :maxDay, :planName, :userId)", nativeQuery = true)
	public void addPlan(
			@Param("userId") int userId,
			@Param("planName") String planName,
			@Param("currentDay") int currentDay,
			@Param("maxDay") int maxDay,
			@Param("active") boolean active,
			@Param("assignedBy") String assignedBy);
	/**
	 * update a plan activity given the userId, planName, and new actvity.
	 * @param userId
	 * @param planName
	 * @param newActive
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE plan p SET p.active = :newActive WHERE(p.user_id = :userId AND p.plan_name = :planName)", nativeQuery = true)
	public void updateActive(@Param("userId") int userId, @Param("planName") String planName, @Param("newActive") boolean newActive);
}
