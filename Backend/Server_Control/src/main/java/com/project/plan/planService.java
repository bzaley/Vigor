package com.project.plan;

import java.util.ArrayList;
import java.util.List;
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
	public changerResponse dayChanger(int userId, String planName, int changer) {
		
		// Finds the plan to be updated
		plan plan = planRepo.findByUserIdAndPlanName(userId, planName);
		
		
		// If statement to get the correct new day
		int new_day = 0;
		
		if (changer == 1) { // Increments
			if (plan.getCurrentDay() == plan.getMaxDay()) {
				new_day = 1;
			} else {
				new_day = plan.getCurrentDay() + 1;
			}
		} else if (changer == 2) { // Decrements
			if (plan.getCurrentDay() == 1) {
				new_day = plan.getMaxDay();
			} else {
				new_day = plan.getCurrentDay() - 1;
			}
		}
		
		plan.setCurrentDay(new_day);
		planRepo.save(plan);
		
		// Should find the newly updated plan. Is grabbing the same thing as the plan variable above
		plan plan_updated = planRepo.findByUserIdAndPlanName(userId, planName);
		
		
		// Saves and deletes the exercises that are part of the plan being changed
		dayExerciseService.exerciseSaver(plan_updated);
		
		// Fills in the exercises for the next/prev day of the plan
		dayExerciseService.fillPlan(plan_updated);
		
		return new changerResponse("GO");
		
	}
	
	
	public List<planReturn> getPlans(int userId) {
		
		List<plan> wrong = planRepo.findAllByUserId(userId);
		
		List<planReturn> right = new ArrayList<planReturn>();
		
		for (plan tmp : wrong) {
			
			planReturn next = new planReturn(
					tmp.getPlanName(),
					tmp.isActive());
			right.add(next);
		}
		return right;
		
		
	}
}
