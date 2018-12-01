package com.project.userPlan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userPlan")
public class userPlanController {

	@Autowired
	private userPlanService userPlanService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addUserPlan(@RequestBody List<userAdd> plan) {
		userPlanService.addUserPlan(plan);
	}
	
	@RequestMapping("/remove/{userId}/{planName}")
	public void removeUserPlan(@PathVariable int userId, @PathVariable String planName) {
		userPlanService.removeUserPlan(userId, planName);
	}
}
