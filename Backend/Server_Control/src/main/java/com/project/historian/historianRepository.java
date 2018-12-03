package com.project.historian;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface historianRepository extends JpaRepository<historian, Integer>, CrudRepository<historian, Integer> {

	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO historian VALUES (entry, :exerciseId, :reps, :saveDate, :sets, :userId)", nativeQuery = true)
	public void addHistory(@Param("userId") int userId,
			@Param("exerciseId") int exerciseId,
			@Param("sets") int sets,
			@Param("reps") int reps,
			@Param("saveDate") String saveDate);

	public List<historian> findAllByUserIdAndSaveDate(int userId, String saveDate);
	
	@Transactional
	public void deleteByUserId(int userId);
}
