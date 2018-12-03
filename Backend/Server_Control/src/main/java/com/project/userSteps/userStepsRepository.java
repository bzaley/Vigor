package com.project.userSteps;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @author Ben Zaley
 *
 */
public interface userStepsRepository extends JpaRepository<userSteps, Integer>, CrudRepository<userSteps, Integer> {
	/**
	 * 
	 * @param userId
	 * @param date
	 * @return
	 */
	public userSteps findByUserIdAndDate(int userId, String date);
	/**
	 * 
	 * @param userId
	 * @param date
	 * @return
	 */
	public boolean existsByUserIdAndDate(int userId, String date);
	/**
	 * 
	 * @param userId
	 */
	@Transactional
	public void deleteByUserId(int userId);
	
	
	/**
	 * update step value for a given date and userId.
	 * @param userId
	 * @param date
	 * @param steps
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE user_steps us SET us.steps = :newSteps WHERE(us.user_id = :userID AND us.date = :uDate)", nativeQuery = true)
	public void updateSteps(@Param("userID")int userId, @Param("uDate")String date, @Param("newSteps")int steps);
	/**
	 * update step goal for a given userId and date.
	 * @param userId
	 * @param date
	 * @param stepGoal
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE user_steps us SET us.step_goal = :newStepGoal WHERE(us.user_id = :userID AND us.date = :uDate)", nativeQuery = true)
	public void updateStepGoal(@Param("userID")int userId, @Param("uDate")String date, @Param("newStepGoal")int stepGoal);
	/**
	 * set checkmark for daily step goal being met.
	 * @param userId
	 * @param date
	 * @param metGoal
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE user_steps us SET us.goal_met = :newMetGoal WHERE(us.user_id = :userID AND us.date = :uDate)", nativeQuery = true)
	public void setGoalsMet(@Param("userID")int userId, @Param("uDate")String date, @Param("newMetGoal")boolean metGoal);
}
