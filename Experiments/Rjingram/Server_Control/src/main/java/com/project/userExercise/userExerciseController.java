package com.project.userExercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public void markSave(@RequestBody userEntry userEntry) {
		userExerciseService.markSave(userEntry);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/next/{userId}/{planName}")
	public ResponseEntity<?> nextDay(@PathVariable int userId, @PathVariable String planName) {
		return ResponseEntity.ok().body(userExerciseService.nextDay(userId, planName));
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/last/{userId}/{planName}")
	public ResponseEntity<?> prevDay(@PathVariable int userId, @PathVariable String planName) {
		return ResponseEntity.ok().body(userExerciseService.prevDay(userId, planName));
	}
	
}
