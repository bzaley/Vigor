package com.project.userExercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userExercise")
public class userExerciseController {

	@Autowired
	private userExerciseService userExerciseService;
	
	@RequestMapping(method=RequestMethod.POST, value="/addExercise")
	public void addExercise(@RequestBody userExercise userExercise) {
		userExerciseService.addExercise(userExercise);
	}
	

}
