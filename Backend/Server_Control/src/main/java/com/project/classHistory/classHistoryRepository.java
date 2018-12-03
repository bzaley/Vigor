package com.project.classHistory;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface classHistoryRepository extends CrudRepository<classHistory, Integer> {

	public List<classHistory> findAllByUserId(int userId);
	
	public List<classHistory> findAllByUserIdAndDate(int userId, String date);
	
}
