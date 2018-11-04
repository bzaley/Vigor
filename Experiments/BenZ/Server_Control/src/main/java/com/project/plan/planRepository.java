package com.project.plan;

import org.springframework.data.repository.CrudRepository;

public interface planRepository extends CrudRepository<plan, Integer> {

	public plan findByUserIdAndPlanName(int userId, String planName);
	
}
