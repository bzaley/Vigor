package com.project.userSteps;

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

	@RequestMapping("/{user_id}/{date}")
	public userSteps getStepsByDate(@PathVariable int user_id, @PathVariable int date) {
		return stepsService.getToday(user_id, date);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addStepsToUser(@RequestBody userSteps user) {
		stepsService.updateStepEntry(user);
	}

}
