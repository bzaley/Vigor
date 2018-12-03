package com.project.plan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author Ryan Ingram
 *
 */
@RestController
@RequestMapping("/plan")
public class planController {

	@Autowired
	private planService planService;
	/**
	 * 
	 * @param plan
	 */
	public void addPlan(plan plan) {
		planService.addPlan(plan);
	}
	
	/**
	 * 
	 * @param userId
	 * @param planName
	 */
	@RequestMapping("/toggle/{userId}/{planName}")
	public void togglePlan(@PathVariable("userId") int userId, @PathVariable("planName") String planName) {
		planService.togglePlan(userId, planName);
	}
	/**
	 *
	 * @param userId
	 * @param planName
	 * @param changer
	 */
	// Changer == 1 is increment
	// Changer == 2 is decrement
	@RequestMapping("/{userId}/{planName}/{changer}")
	public changerResponse dayChanger(@PathVariable("userId") int userId, @PathVariable("planName") String planName, @PathVariable("changer") int changer) {
		return planService.dayChanger(userId, planName, changer);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getAll/{userId}")
	public List<planReturn> getPlans(@PathVariable("userId") int userId) {
		return planService.getPlans(userId);
	}
}
