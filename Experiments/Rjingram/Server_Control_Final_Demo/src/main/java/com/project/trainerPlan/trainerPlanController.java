package com.project.trainerPlan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainerPlan")
public class trainerPlanController {

	
	@Autowired
	private trainerPlanService trainerPlanService;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addTrainerPlan(@RequestBody List<trainerAdd> plan) {
		trainerPlanService.addTrainerPlan(plan);
	}
}
