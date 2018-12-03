package com.project.trainerPlan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Exercise.*;
import com.project.Exercise.Exercise;
import com.project.plan.*;
import com.project.user.*;
import com.project.userPlan.userPlan;
import com.project.utilities.*;
/**
 *
 * @author Ryan Ingram
 *
 */
@Service
public class trainerPlanService {

	
	@Autowired
	private trainerPlanRepository trainerPlanRepo;
	
	@Autowired
	private planRepository planRepo;
	
	@Autowired
	private planService planService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ExerciseRepository exerciseRepo;


	/**
	 * Add plan to trainer table.
	 * @param plan
	 */
	public void addTrainerPlan(List<trainerAdd> plan) {
			
			// Inserts every element into userPlan table
			
			
			// Loops through all exercises in the plan given
			for (trainerAdd tmp : plan) {

				Exercise exercise = exerciseRepo.findByName(tmp.getExercise());
				int exid = exercise.getExerciseId();

				User user = userRepo.findByUserEmail(tmp.getUserEmail());
				int uid = user.getuserId();
				
				trainerPlanRepo.addTrainerExercise( // Inserts the exercise stored in tmp into the plan table
						tmp.getTrainerId(),
						uid,
						tmp.getPlanName(),
						tmp.getDay(),
						exid,
						tmp.getSets(),
						tmp.getReps());
			}
			
			// Add the plan to the plan table
			// Plan starts with current day zero and max equal to the last day given and active equal to false and assigned by trainer
			planService.addPlan(new plan(userRepo.findByUserEmail(plan.get(0).getUserEmail()).getuserId(), plan.get(0).getPlanName(), 1, plan.get(plan.size()-1).getDay(), false, "trainer"));
			
	}
	/**
	 * removes a trainer plan given a userId and planName.
	 * @param userId
	 * @param planName
	 */
	public void removeTrainerPlan(int userId, String planName) {
			
			plan plan = planRepo.findByUserIdAndPlanName(userId, planName);
			
			if (plan.isActive()) {
				planService.togglePlan(userId, planName);
			}
			
			planRepo.delete(plan);
			
			List<trainerPlan> trainerPlan = trainerPlanRepo.findAllByUserIdAndPlanName(userId, planName);
			
			trainerPlanRepo.deleteAll(trainerPlan);
			
	}
	
}
