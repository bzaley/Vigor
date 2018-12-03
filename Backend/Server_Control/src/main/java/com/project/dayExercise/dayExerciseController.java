package com.project.dayExercise;

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
@RequestMapping("/dayExercise")
public class dayExerciseController {

	
	@Autowired
	private dayExerciseService dayExerciseService;
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/get/{userId}")
	public List<userDayReturn> getDayExercises(@PathVariable("userId") int userId) {
		return dayExerciseService.getDayExercises(userId);
	}
	/**
	 * 
	 * @param dayExerciseAdd
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addSingle")
	public void addSingle(@RequestBody dayExerciseAdd dayExerciseAdd) {
		dayExerciseService.addSingle(dayExerciseAdd);
	}
	/**
	 * 
	 * @param dayExerciseComp
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/markComplete")
	public void markComplete(@RequestBody dayExerciseComp dayExerciseComp) {
		dayExerciseService.markComplete(dayExerciseComp);
	}
	/**
	 * 
	 * @param dayExerciseRemove
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/remove")
	public void remove(@RequestBody dayExerciseRemove dayExerciseRemove) {
		dayExerciseService.remove(dayExerciseRemove);
	}
}
