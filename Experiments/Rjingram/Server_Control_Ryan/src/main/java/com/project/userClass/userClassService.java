package com.project.userClass;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userClassService {

	@Autowired
	private userClassRepository userClassRepo;
	
	public void assignToClass(int userId, int classId) {
		
		userClass userClass = new userClass(userId, classId);
		
		userClassRepo.save(userClass);
	}
	
	public void removeFromClass(int userId, int classId) {
		
		userClass userClass = new userClass(userId, classId);
		
		userClassRepo.delete(userClass);
		
	}
	
	public List<userClass> getUsersClasses(int userId) {
		
		return userClassRepo.findAllByUserId(userId);
		
	}
	
}
