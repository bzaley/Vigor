package com.project.plan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
public class planController {

	@Autowired
	private planService planService;
	
	public void addPlan(plan plan) {
		planService.addPlan(plan);
	}
	
	
	@RequestMapping("/toggle/{userId}/{planName}")
	public void togglePlan(@PathVariable("userId") int userId, @PathVariable("planName") String planName) {
		planService.togglePlan(userId, planName);
	}
	
	// Changer == 1 is increment
	// Changer == 2 is decrement
	@RequestMapping("/{userId}/{planName}/{changer}")
	public void dayChanger(@PathVariable("userId") int userId, @PathVariable("planName") String planName, @PathVariable("changer") int changer) throws InterruptedException {
		planService.dayChanger(userId, planName, changer);
	}
	
	
	@RequestMapping("/getAll/{userId}")
	public List<planReturn> getPlans(@PathVariable("userId") int userId) {
		return planService.getPlans(userId);
	}
}
