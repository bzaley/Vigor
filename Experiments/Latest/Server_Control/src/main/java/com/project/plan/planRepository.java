package com.project.plan;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
public interface planRepository extends CrudRepository<plan, Integer> {

	public plan findByUserIdAndPlanName(int userId, String planName);
	
	public List<plan> findAllByUserIdAndActive(int userId, boolean active);
	
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE plan p SET p.current_day = :newDay WHERE(p.user_id = :userID AND p.plan_name = :planName)", nativeQuery = true)
	public void updateDay(@Param("userID")int userId, @Param("planName")String planName, @Param("newDay")int day);
	
	
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
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE plan p SET p.active = :newActive WHERE(p.user_id = :userId AND p.plan_name = :planName)", nativeQuery = true)
	public void updateActive(@Param("userId") int userId, @Param("planName") String planName, @Param("newActive") boolean newActive);
}
