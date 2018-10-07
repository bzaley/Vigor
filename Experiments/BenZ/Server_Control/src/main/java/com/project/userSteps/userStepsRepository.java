package com.project.userSteps;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface userStepsRepository extends CrudRepository<userSteps, Integer> {
	
	public userSteps findByDate(Integer userID, Integer date);
	
	public boolean existsByDate(Integer userID, Integer date);
}
