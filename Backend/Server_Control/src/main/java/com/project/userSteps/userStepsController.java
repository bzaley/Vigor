package com.project.userSteps;


import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.websocket.WebSocketServer;
/**
 * 
 * @author Ben Zaley
 *
 */
@RestController
@RequestMapping("/steps")
public class userStepsController {

	@Autowired
	private userStepsService stepsService;

	@Autowired
	private WebSocketServer socket;
	/**
	 * 
	 * @param userId
	 * @param date
	 * @return
	 */
	@RequestMapping("/{userId}/{date}")
	public userSteps getStepsByDate(@PathVariable int userId, @PathVariable String date) {
		return stepsService.getToday(userId, date);
	}
	/**
	 * 
	 * @param user
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addStepsToUser(@RequestBody userSteps user) {

		stepsService.addNewEntry(user);

	}
	/**
	 * 
	 * @param user
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public void updateStepsToUser(@RequestBody userSteps user) throws IOException{
		String date = user.getDate();
		int userId = user.getUserId();
		int steps = user.getSteps();
		stepsService.updateStepEntry(userId, date, steps);

		if(!stepsService.getToday(userId, date).isGoalMet()) {
			boolean compare = stepsService.compareStepsVsGoal(userId, date);
			if(compare) {
				socket.sendMessageToParticularUser(userId, "Congrats");
				stepsService.updateIsGoalMet(userId, date, true);
			}
		}

	}
	/**
	 * 
	 * @param userId
	 * @param numDays
	 * @return
	 */
	@RequestMapping("/multiple/{userId}/{numDays}")
	public ArrayList<userSteps> getMultipleDates(@PathVariable int userId, @PathVariable int numDays) {
		return stepsService.getMultipleDays(userId, numDays);
	}
	/**
	 * 
	 * @param userId
	 * @param date
	 * @param stepGoal
	 */
	@RequestMapping("/updateStepGoal/{userId}/{date}/{stepGoal}")
	public void updateStepGoal(@PathVariable int userId, @PathVariable String date, @PathVariable int stepGoal) {
		stepsService.updateStepGoal(userId, date, stepGoal);
		stepsService.updateIsGoalMet(userId, date, false);

	}


}
