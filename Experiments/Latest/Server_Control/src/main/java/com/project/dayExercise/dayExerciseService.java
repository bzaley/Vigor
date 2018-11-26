package com.project.dayExercise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.plan.*;
import com.project.userPlan.*;
import com.project.trainerPlan.*;
import com.project.user.*;
import com.project.Exercise.*;
import com.project.historian.*;
import com.project.utilities.*;

@Service
public class dayExerciseService {

	
	@Autowired
	private dayExerciseRepository dayExerciseRepo;
	
	@Autowired
	private userPlanRepository userPlanRepo;
	
	@Autowired
	private trainerPlanRepository trainerPlanRepo;
	
	@Autowired
	private historianRepository historianRepo;
	
	@Autowired
	private ExerciseRepository exerciseRepo;
	
	@Autowired
	private UserRepository userRepo;

	
	/*
	 * Returns a list of all exercises that a user has to do
	 * for a the current day.
	 */
	public List<userDayReturn> getDayExercises(int userId) {
		
		List<dayExercise> wrong = dayExerciseRepo.findAllByUserId(userId);
		
		List<userDayReturn> right = new ArrayList<userDayReturn>();
		
		for (dayExercise tmp : wrong) {
			
			//String exerciseName = convert.convertIdToExercise(tmp.getExerciseId());
			Exercise exercise = exerciseRepo.findByExerciseId(tmp.getExerciseId());
			String exerciseName = exercise.getName();
			
			//String userEmail = convert.convertIdToEmail(tmp.getUserId());
			User user = userRepo.findByUserId(tmp.getUserId());
			String userEmail = user.getuserEmail();
			
			userDayReturn userDayReturn = new userDayReturn(
					userEmail,
					tmp.getPlanName(),
					exerciseName,
					tmp.getSets(),
					tmp.getReps());
			right.add(userDayReturn);
		}
		
		return right;
	}
	
	public void addSingle(dayExerciseAdd dayExerciseAdd) {
		
		//int exid = convert.convertExerciseToId(tmp.getExercise());
		Exercise exercise = exerciseRepo.findByName(dayExerciseAdd.getExercise());
		int exid = exercise.getExerciseId();
		
		//int uid = convert.convertEmailToId(tmp.getuserEmail());
		User user = userRepo.findByUserEmail(dayExerciseAdd.getUserEmail());
		int uid = user.getuserId();
		
		dayExerciseRepo.addExercise(uid, "", false, exid, dayExerciseAdd.getSets(), dayExerciseAdd.getReps());
		
	}
	
	/*
	 * Called when a plan is toggled to active.
	 * Fills the dayExercise table with the current day exercises from the plan
	 * that was just activated.
	 */
	public void fillPlan(plan plan) {
		
		// If plan is assigned by user look in userPlan table
		if (plan.getAssignedBy().equals("user")) {
			
			List<userPlan> userPlan = new ArrayList<userPlan>();
			userPlan = userPlanRepo.findAllByUserIdAndPlanNameAndDay(plan.getUserId(), plan.getPlanName(), plan.getCurrentDay());
			// Iterates through list of exercises and adds each to the day exercise table
			for (userPlan tmp : userPlan) {
				dayExerciseRepo.addExercise(tmp.getUserId(), tmp.getPlanName(), false, tmp.getExerciseId(), tmp.getSets(), tmp.getReps());
			}
		} else if (plan.getAssignedBy().equals("trainer")) {
			
			List<trainerPlan> trainerPlan = new ArrayList<trainerPlan>();
			trainerPlan = trainerPlanRepo.findAllByUserIdAndPlanNameAndDay(plan.getUserId(), plan.getPlanName(), plan.getCurrentDay());
			// Iterates through list of exercises and adds each to the day exercise table
			for (trainerPlan tmp : trainerPlan) {
				dayExerciseRepo.addExercise(tmp.getUserId(), tmp.getPlanName(), false, tmp.getExerciseId(), tmp.getSets(), tmp.getReps());
			}
		}
		
	}
	
	
	/*
	 * Called when a plan is toggled to not active.
	 * Clears all exercises that are in the dayExercise table
	 * from the plan that is changed to not active.
	 */
	public void emptyPlan(plan plan) {
		dayExerciseRepo.deleteAll(dayExerciseRepo.findAllByUserIdAndPlanName(plan.getUserId(), plan.getPlanName()));
	}
	
	
	/*
	 * Finds all exercises in dayExercise table for a specific user and the plan
	 * being incremented or decremented. Saves all the completed ones in the historian 
	 * and removes the rest. Finally fills in the plan for the next day.
	 */
	public void exerciseSaver(plan plan) {
		
		// Gets current date for saving
		DateController dateController = new DateController();
		String saveDate = dateController.returnWorkingDateAsString();
		
		// Gets list of all exercises that are in the plan being moved to next day
		List<dayExercise> exercises = dayExerciseRepo.findAllByUserIdAndPlanName(plan.getUserId(), plan.getPlanName());
		
		// Iterates through the list of exercises and saves and removes them
		for (dayExercise tmp : exercises) {
			// If the exercise is complete then save it and delete it
			// Else just delete it
			if (tmp.isComplete()) {
				historianRepo.addHistory(tmp.getUserId(), tmp.getExerciseId(), tmp.getSets(), tmp.getReps(), saveDate);
				dayExerciseRepo.delete(tmp);
			} else {
				dayExerciseRepo.delete(tmp);
			}
		}
		
	}
	
	/*
	 * Changes the complete value to true for the given exercise.
	 * Front end should also prompt for new sets and reps values
	 * if the user did not complete what their goal was. This will update
	 * the values for these fields as well.
	 */
	public void markComplete(dayExerciseComp dayExerciseComp) {
		
		//int exid = convert.convertExerciseToId(dayExerciseComp.getExercise());
		Exercise exercise = exerciseRepo.findByName(dayExerciseComp.getExercise());
		int exid = exercise.getExerciseId();
		
		//int uid = convert.convertEmailToId(dayExerciseComp.getUserEmail());
		User user = userRepo.findByUserEmail(dayExerciseComp.getUserEmail());
		int uid = user.getuserId();
		
		//dayExerciseRepo.markComplete(uid, dayExerciseComp.getPlanName(), true, exid, dayExerciseComp.getSets(), dayExerciseComp.getSets());
		dayExerciseRepo.markComplete(uid, dayExerciseComp.getPlanName(), true, exid, dayExerciseComp.getSets(), dayExerciseComp.getReps());
		
	}
	
}
