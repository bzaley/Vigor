package com.project.userPlan;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Exercise.*;
import com.project.Exercise.Exercise;
import com.project.plan.*;
import com.project.user.*;
import com.project.utilities.*;

@Service
public class userPlanService {

	@Autowired
	private userPlanRepository userPlanRepo;
	
	@Autowired
	private planRepository planRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ExerciseRepository exerciseRepo;
	
	private Converter convert;
	
	public void addUserPlan(List<userAdd> plan) {
		
		// Inserts every element into userPlan table
		
		
		// Loops through all exercises in the plan given
		for (userAdd tmp : plan) {
			
 			//int exid = convert.convertExerciseToId(tmp.getExercise());
			Exercise exercise = exerciseRepo.findByName(tmp.getExercise());
			int exid = exercise.getExerciseId();
			
			//int uid = convert.convertEmailToId(tmp.getuserEmail());
			User user = userRepo.findByUserEmail(tmp.getUserEmail());
			int uid = user.getuserId();
			
			userPlanRepo.addUserExercise( // Inserts the exercise stored in tmp into the plan table
					uid,
					tmp.getPlanName(),
					tmp.getDay(),
					exid,
					tmp.getSets(),
					tmp.getReps());
		}
		
		// Add the plan to the plan table
		// Plan starts with current day zero and max equal to the last day given and active equal to false and assigned by user
		planRepo.addPlan(userRepo.findByUserEmail(plan.get(0).getUserEmail()).getuserId(), plan.get(0).getPlanName(), 1, plan.get(plan.size()-1).getDay(), false, "user");
		
		
	}
	
	
}
