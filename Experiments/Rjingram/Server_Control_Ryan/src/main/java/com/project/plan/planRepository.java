package com.project.plan;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface planRepository extends CrudRepository<plan, Integer> {

	public plan findByUserIdAndPlanName(int userId, String planName);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE plan p SET p.current_day = :newDay WHERE(p.user_id = :userID AND p.plan_name = :planName)", nativeQuery = true)
	public void updateDay(@Param("userID")int userId, @Param("planName")String planName, @Param("newDay")int day);
}
