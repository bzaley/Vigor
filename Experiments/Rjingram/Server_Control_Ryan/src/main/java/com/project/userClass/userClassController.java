package com.project.userClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userClass")
public class userClassController {

	
	@Autowired
	private userClassService userClassService;
	
	@RequestMapping("/assign/{userId}/{classId}")
	public void assignToClass(@PathVariable int userId, @PathVariable int classId) {
		userClassService.assignToClass(userId, classId);
	}
	
	
}
