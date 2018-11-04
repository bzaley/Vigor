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
	
	public List<userExercise> findAllByUserIdAndPlanNameAndDay(int userId, String planName, int day);
	
	/*
	@Modifying
	@Transactional
	@Query(value = "UPDATE user")
	public void updateUserExercise(@Param("userId") int user_id, @Param("exerciseId") int exercise_id);
	*/
	
}
