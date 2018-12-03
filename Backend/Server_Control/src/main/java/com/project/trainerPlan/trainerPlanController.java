package com.project.trainerPlan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author Ryan Ingram
 *
 */
@RestController
@RequestMapping("/trainerPlan")
public class trainerPlanController {

	
	@Autowired
	private trainerPlanService trainerPlanService;
	
	/**
	 * 
	 * @param plan
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addTrainerPlan(@RequestBody List<trainerAdd> plan) {
		trainerPlanService.addTrainerPlan(plan);
	}
	/**
	 * 
	 * @param userId
	 * @param planName
	 */
	@RequestMapping("/remove/{userId}/{planName}")
	public void removeUserPlan(@PathVariable int userId, @PathVariable String planName) {
		trainerPlanService.removeTrainerPlan(userId, planName);
	}
}
