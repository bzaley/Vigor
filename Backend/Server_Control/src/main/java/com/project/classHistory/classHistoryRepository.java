package com.project.classHistory;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface classHistoryRepository extends CrudRepository<classHistory, Integer> {

	public List<classHistory> findAllByUserId(int userId);
	
	public List<classHistory> findAllByUserIdAndDate(int userId, String date);
	
	@Transactional
	public void deleteByUserId(int userId);
}
