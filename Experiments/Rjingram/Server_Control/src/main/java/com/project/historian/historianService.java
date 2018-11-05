package com.project.historian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class historianService {

	@Autowired
	private historianRepository historianRepo;
	
	
	public void addHistory(historian historian) {
		historianRepo.addHistory(historian.getUserId(),
				historian.getExerciseId(),
				historian.getSets(),
				historian.getReps(),
				historian.getSaveDate());
	}
	
}
