package com.project.userClass;

import java.util.List;
import com.project.classes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author Ryan Ingram
 *
 */
@RestController
@RequestMapping("/userClass")
public class userClassController {

	
	@Autowired
	private userClassService userClassService;
	/**
	 * 
	 * @param userId
	 * @param classId
	 * @return
	 */
	@RequestMapping("/assign/{userId}/{classId}")
	public addClassResponse assignUserClass(@PathVariable int userId, @PathVariable int classId) {
		return userClassService.assignUserClass(userId, classId);
	}
	/**
	 * 
	 * @param userId
	 * @param classId
	 */
	@RequestMapping("/remove/{userId}/{classId}")
	public void removeUserClass(@PathVariable int userId, @PathVariable int classId) {
		userClassService.removeUserClass(userId, classId);
	}
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getClasses/{userId}")
	public List<Classes> getClasses(@PathVariable int userId) {
		return userClassService.getClasses(userId);
	}
	/**
	 * 
	 * @param classId
	 * @return
	 */
	@RequestMapping("/getUsers/{classId}")
	public List<userClass> getUsers(@PathVariable int classId) {
		return userClassService.getUsers(classId);
	}
}
