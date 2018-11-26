package com.project.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.dayExercise.*;

@Service
public class planService {

	@Autowired
	private planRepository planRepo;
	
	@Autowired
	private dayExerciseService dayExerciseService;
	
	public void addPlan(plan plan) {
		planRepo.save(plan);
	}
	
	
	/*
	 * Toggles the boolean active variable.
	 * If the plan is being activated the dayExercise table gets filled with the
	 * current day of the plan. If the plan is being deactivated the dayExercise table
	 * clears all exercises that are apart of the plan.
	 */
	public void togglePlan(int userId, String planName) {
		
		plan plan = planRepo.findByUserIdAndPlanName(userId, planName);
		
		if (plan.isActive()) {
			planRepo.updateActive(userId, planName, false);
			dayExerciseService.emptyPlan(plan);
		} else {
			planRepo.updateActive(userId, planName, true);
			dayExerciseService.fillPlan(plan);
		}
		
	}
	
	/*
	 * Increments or decrements the current day of the plan
	 */
	public void dayChanger(int userId, String planName, int changer) {
		
		plan plan = planRepo.findByUserIdAndPlanName(userId, planName);
		
		int new_day = 0;
		
		if (changer == 1) { // Increments
			if (plan.getCurrentDay() == plan.getMaxDay()) {
				new_day = 1;
			} else {
				new_day = plan.getCurrentDay() + 1;
			}
		} else if (changer == 2) { // Decrements
			if (plan.getCurrentDay() == 0) {
				new_day = plan.getMaxDay();
			} else {
				new_day = plan.getCurrentDay() - 1;
			}
		}
		// Updates the plan with the new current day
		planRepo.updateDay(userId, planName, new_day);
		
		// Isn't Updating the new day ????????
		// Finds the newly updated plan
		plan plan_updated = planRepo.findByUserIdAndPlanName(userId, planName);
		
		// Saves and deletes the exercises that are part of the plan being changed
		dayExerciseService.exerciseSaver(plan_updated);
		
		// Fills in the exercises for the next/prev day of the plan
		dayExerciseService.fillPlan(plan_updated);
	}
	
	
}
