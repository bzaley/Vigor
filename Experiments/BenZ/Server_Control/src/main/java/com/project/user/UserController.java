package com.project.user;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import com.project.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired 
	private UserService userService;

	@RequestMapping("/all") //RequestMapping without a set method functions as a GET method
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@RequestMapping("/{userId}") //Gets user based on integer userId passed into address 
	public User getUser(@PathVariable int userId) {
		return userService.getUser(userId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add") //Setting the RequestMEthod to POST will allow adding new values
	public void addUser(@RequestBody User user) { //RequestBody tells spring you will provide JSON package of instance and convert it into an object instance
		userService.addUser(user);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public void signUp(@RequestBody User user) {
		user.setPassword(encode(user.getPassword()));
	}
	
	
}

