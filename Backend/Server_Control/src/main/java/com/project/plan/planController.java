package com.project.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
public class planController {

	@Autowired
	private planService planService;
	
	public void addPlan(plan plan) {
		planService.addPlan(plan);
	}
}
