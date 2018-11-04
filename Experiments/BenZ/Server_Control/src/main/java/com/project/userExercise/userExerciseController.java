package com.project.userExercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userExercise")
public class userExerciseController {

	@Autowired
	private userExerciseService userExerciseService;
	
	/*
	 * USE CASE: A user adds a single exercise (Not involved with plan)
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addUserSingle")
	public void addUserSingleExercise(@RequestBody userAddEntry userAddEntry) {
		userExerciseService.addUserSingleExercise(userAddEntry);
	}
	
	/*
	 * USE CASE: A user adds a plan that they created
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addUserPlan")
	public void addUserPlanExercises(@RequestBody List<userAddEntry> plan) {
		userExerciseService.addUserPlanExercises(plan);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getPlan/{userId}/{planName}")
	public List<userEntry> getExercisesForPlan(@PathVariable int userId, @PathVariable String planName) {
		return userExerciseService.getExercisesForPlan(userId, planName);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/{userId}")
	public List<userEntry> getExercises(@PathVariable int userId) {
		return userExerciseService.getExercises(userId);
	}
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/remove")
	public void removeUserExercise(@RequestBody userEntry userEntry) {
		userExerciseService.removeUserExercise(userEntry);
	}
	
	/*
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public void updateUserExercise(@RequestBody userEntry userEntry) {
		userExerciseService.updateUserExercise(userEntry);
	}*/
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/complete/{remove}/{saveDate}")
	public void markComplete(@RequestBody userEntry userEntry, @PathVariable boolean remove, @PathVariable String saveDate) {
		userExerciseService.markComplete(userEntry, remove, saveDate);
	}
	
}
