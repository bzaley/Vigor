package com.project.classHistory;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface classHistoryRepository extends CrudRepository<classHistory, Integer> {
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<classHistory> findAllByUserId(int userId);
	/**
	 * 
	 * @param userId
	 * @param date
	 * @return
	 */
	public List<classHistory> findAllByUserIdAndDate(int userId, String date);
	/**
	 * 
	 * @param userId
	 */
	@Transactional
	public void deleteByUserId(int userId);
}
