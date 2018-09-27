package com.project.user;

import org.springframework.stereotype.Controller;

@RestController
public class DataController {
	
	@RequestMapping
	public String sayHi() {
		return "Hi";
	}
}
