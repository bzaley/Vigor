package com.project.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //Tells spring to run as a service
public class UserService {

	@Autowired
	private UserRepository userRepo;


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
}
