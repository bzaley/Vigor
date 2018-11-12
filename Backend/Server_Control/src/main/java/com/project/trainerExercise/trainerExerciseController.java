package com.project.trainerExercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.userExercise.userEntry;

@RestController
@RequestMapping("/trainerExercise")
public class trainerExerciseController {

	@Autowired
	private trainerExerciseService trainerExerciseService;

	@RequestMapping(method = RequestMethod.POST, value = "/addTrainerSingle")
	public void addTrainerExercise(@RequestBody trainerAddEntry trainerAddEntry) {
		trainerExerciseService.addTrainerExercise(trainerAddEntry);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addTrainerPlan")
	public void addTrainerPlan(@RequestBody List<trainerAddEntry> plan) {
		trainerExerciseService.addTrainerPlan(plan);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getTrainerExercises/{userId}")
	public List<trainerEntry> getTrainerExercises(@PathVariable("userId") int userId) {
		return trainerExerciseService.getTrainerExercise(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getTrainerPlan/{userId}/{planName}")
	public List<trainerEntry> getTrainerPlanExercises(@PathVariable("userId") int userId, @PathVariable("planName") String planName) {
		return trainerExerciseService.getTrainerPlanExercises(userId, planName);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/remove")
	public void removeUserExercise(@RequestBody trainerEntry trainerEntry) {
		trainerExerciseService.removeTrainerExercise(trainerEntry);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public void markSave(@RequestBody trainerEntry trainerEntry) {
		trainerExerciseService.markSave(trainerEntry);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/next/{userId}/{planName}")
	public ResponseEntity<?> nextDay(@PathVariable int userId, @PathVariable String planName) {
		return ResponseEntity.ok().body(trainerExerciseService.nextDay(userId, planName));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/last/{userId}/{planName}")
	public ResponseEntity<?> prevDay(@PathVariable int userId, @PathVariable String planName) {
		return ResponseEntity.ok().body(trainerExerciseService.prevDay(userId, planName));
	}
}
