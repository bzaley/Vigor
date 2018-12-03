package com.project.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.project.classHistory.*;
import com.project.dayExercise.*;
import com.project.historian.*;
import com.project.plan.*;
import com.project.userPlan.*;
import com.project.trainerPlan.*;
import com.project.userSteps.*;
import com.project.userClass.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //Tells spring to run as a service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private userPlanRepository userPlanRepo;
	
	@Autowired
	private trainerPlanRepository trainerPlanRepo;
	
	@Autowired
	private classHistoryRepository classHistoryRepo;
	
	@Autowired
	private historianRepository historianRepo;
	
	@Autowired
	private dayExerciseRepository dayExerciseRepo;
	
	@Autowired
	private planRepository planRepo;
	
	@Autowired
	private userStepsRepository userStepsRepo;
	
	@Autowired
	private userClassRepository userClassRepo;

	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>(); 
		userRepo.findAll().forEach(users::add);
		return users;
	}

	public User getUserByID(int id) {
		return userRepo.findById(id).get();
	}

	public SignupResponse signUp(User user) {
		if(userRepo.existsByUserEmail(user.getuserEmail())) {
			return new SignupResponse(true,"An account for this email already exists." );

		}
		userRepo.save(user);
		return new SignupResponse(false, "Account creation successful.");

	}
	public LoginResponse login(String email, String Password) {
		try {
			User u = userRepo.findByUserEmail(email);
			if(u == null) {
				throw(new NullPointerException());
			}
		}catch (Exception e) {
			LoginResponse sendBack = new LoginResponse(true, "The email entered does not exist.", "", 0, "", "", "");
			return sendBack;
		}
		try {
			User us = userRepo.findByUserEmailAndPassword(email, Password);
			if(us == null) {
				throw(new NullPointerException());
			}
		}catch(Exception e) {
			LoginResponse sendBack = new LoginResponse(true, "The email and password do not match.", "", 0, "", "", "");
			return sendBack;
		}
		User success = userRepo.findByUserEmailAndPassword(email, Password);
		LoginResponse sendBack = new LoginResponse(false, "", success.getuserEmail(), success.getuserId(), success.getFirstname(), success.getLastname(), success.getRole());
		return sendBack;
	}
	
	
	public void deleteUser(int userId) {
		
		userRepo.deleteByUserId(userId);
		
		classHistoryRepo.deleteByUserId(userId);
		
		dayExerciseRepo.deleteByUserId(userId);
		
		historianRepo.deleteByUserId(userId);
		
		planRepo.deleteByUserId(userId);
		
		trainerPlanRepo.deleteByUserId(userId);
		
		userRepo.deleteByUserId(userId);
		
		userPlanRepo.deleteByUserId(userId);
		
		userStepsRepo.deleteByUserId(userId);
		
		userClassRepo.deleteByUserId(userId);
		
		
	}
}
