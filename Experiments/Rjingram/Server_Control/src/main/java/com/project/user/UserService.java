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

	public User getUser(int id) {
		return userRepo.findById(id).get();
	}

	public void addUser(User user) {
		userRepo.save(user);

	}
}
