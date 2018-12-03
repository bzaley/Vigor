package com.project.userClass;

import java.util.ArrayList;
import java.util.List;
import com.project.classes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author Ryan Ingram
 *
 */
@Service
public class userClassService {

	
	@Autowired
	private ClassesRepository classesRepo;
	
	@Autowired
	private userClassRepository userClassRepo;
	/**
	 * assign users to a class.
	 * @param userId
	 * @param classId
	 * @return
	 */
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
	
	/**
	 * remove a user from a class.
	 * @param userId
	 * @param classId
	 */
	public void removeUserClass(int userId, int classId) {
		
		userClass userClass = new userClass(userId, classId);
		
		userClassRepo.delete(userClass);
		
	}
	/**
	 * get all classes for a given userId
	 * @param userId
	 * @return
	 */
	public List<Classes> getClasses(int userId) {
		
		List<userClass> class_list = userClassRepo.findAllByUserId(userId);
		
		List<Classes> right = new ArrayList<Classes>();
		
		for (userClass tmp : class_list) {
			
			Classes toAdd = classesRepo.findByClassId(tmp.getClassId());
			
			right.add(toAdd);
			
		}
		
		return right;
		
	}
	/**
	 * get all users given a classId.
	 * @param classId
	 * @return
	 */
	public List<userClass> getUsers(int classId) {
		
		return userClassRepo.findAllByClassId(classId);
		
	}
	
}
