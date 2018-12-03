package com.project.userClass;

import java.util.ArrayList;
import java.util.List;
import com.project.classes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userClassService {

	
	@Autowired
	private ClassesRepository classesRepo;
	
	@Autowired
	private userClassRepository userClassRepo;
	
	public addClassResponse assignUserClass(int userId, int classId) {
		
		boolean exists = classesRepo.existsByClassId(classId);
		
		if (exists) {
			userClass userClass = new userClass(userId, classId);
			
			userClassRepo.save(userClass);
			
			return new addClassResponse(false, "");
		} else {
			return new addClassResponse(true, "Class does not exist");
		}
		
		
		
	}
	
	
	public void removeUserClass(int userId, int classId) {
		
		userClass userClass = new userClass(userId, classId);
		
		userClassRepo.delete(userClass);
		
	}
	
	public List<Classes> getClasses(int userId) {
		
		List<userClass> class_list = userClassRepo.findAllByUserId(userId);
		
		List<Classes> right = new ArrayList<Classes>();
		
		for (userClass tmp : class_list) {
			
			Classes toAdd = classesRepo.findByClassId(tmp.getClassId());
			
			right.add(toAdd);
			
		}
		
		return right;
		
	}
	
	public List<userClass> getUsers(int classId) {
		
		return userClassRepo.findAllByClassId(classId);
		
	}
	
}
