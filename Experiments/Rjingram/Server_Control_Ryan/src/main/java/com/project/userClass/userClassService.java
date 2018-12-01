package com.project.userClass;

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
	
	public List<userClasses> getUsersClasses(int userId) {
		
		userClassRepo.
		
	}
	
}
