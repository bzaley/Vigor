package com.project.userSteps;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



public interface userStepsRepository extends JpaRepository<userSteps, Integer>, CrudRepository<userSteps, Integer> {
	
	public userSteps findByUserIdAndDate(int userId, String date);
	
	public boolean existsByUserIdAndDate(int userId, String date);
	
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE user_steps us SET us.steps = :newSteps WHERE(us.user_id = :userID AND us.date = :uDate)", nativeQuery = true)
	public void updateSteps(@Param("userID")int userId, @Param("uDate")String date, @Param("newSteps")int steps);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE user_steps us SET us.step_goal = :newStepGoal WHERE(us.user_id = :userID AND us.date = :uDate)", nativeQuery = true)
	public void updateStepGoal(@Param("userID")int userId, @Param("uDate")String date, @Param("newStepGoal")int stepGoal);
}
