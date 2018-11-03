package com.project.userExercise;

import java.util.List;
import com.project.Exercise.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface userExerciseRepository extends JpaRepository<userExercise, Integer>, CrudRepository<userExercise, Integer> {
	
	public List<userExercise> findAllByUserIdAndDate(int userId, String date);
	
	/*
	@Modifying
	@Transactional
	@Query(value = "UPDATE user")
	public void updateUserExercise(@Param("userId") int user_id, @Param("exerciseId") int exercise_id);
	*/
	/*
	@Query(value = "SELECT t FROM Exercise WHERE t.name = :name", nativeQuery = true)
	public Exercise getExerciseIdFromName(@Param("name") String name);
	*/
	/*
	@Query(value = "SELECT name t FROM Exercise WHERE t.exerciseId = :id", nativeQuery = true)
	public Exercise getNameFromExerciseId(@Param("id") int id);
	*/
	
	
}
