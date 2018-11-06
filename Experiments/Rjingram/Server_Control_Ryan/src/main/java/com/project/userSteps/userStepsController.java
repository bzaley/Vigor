package com.project.userSteps;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/steps")
public class userStepsController {

	@Autowired
	private userStepsService stepsService;

	@RequestMapping("/{userId}/{date}")
	public userSteps getStepsByDate(@PathVariable int userId, @PathVariable String date) {
		return stepsService.getToday(userId, date);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addStepsToUser(@RequestBody userSteps user) {

		stepsService.addNewEntry(user);

	}
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public void updateStepsToUser(@RequestBody userSteps user) {
		String date = user.getDate();
		int userId = user.getUserId();
		int steps = user.getSteps();
		stepsService.updateStepEntry(userId, date, steps);

	}
	
	@RequestMapping("/multiple/{userId}/{numDays}")
	public ArrayList<userSteps> getMultipleDates(@PathVariable int userId, @PathVariable int numDays) {
		return stepsService.getMultipleDays(userId, numDays);
	}
	
	@RequestMapping("/updateStepGoal/{userId}/{date}/{stepGoal}")
	public void updateStepGoal(@PathVariable int userId, @PathVariable String date, @PathVariable int stepGoal) {
		stepsService.updateStepGoal(userId, date, stepGoal);
	}


}
