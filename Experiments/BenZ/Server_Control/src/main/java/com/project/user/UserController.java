package com.project.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
	
	@Autowired //declares dependency of
	private UserService userService;
	
	@RequestMapping("/users")
	public List<User> getAllUsers(){
		return Arrays.asList(
				new User(0,"jb@iastate.edu","Joe","Bob","Jbrulz","trainee"),
				new User(1,"cberry@iastate.edu","Carrie", "Berry","Carebear123","Personal Trainer")
				);
	}
	
}
	
