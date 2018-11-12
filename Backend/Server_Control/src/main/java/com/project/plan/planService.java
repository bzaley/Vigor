package com.project.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class planService {

	@Autowired
	private planRepository planRepo;
	
	public void addPlan(plan plan) {
		planRepo.save(plan);
	}
	
}
