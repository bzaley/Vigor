package com.project.classHistory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classHistory")
public class classHistoryController {

	
	@Autowired
	private classHistoryService classHistoryService;
	
	
	@RequestMapping("/getAll/{userId}")
	public List<classHistory> getUserHistoryAll(@PathVariable int userId) {
		return classHistoryService.getUserHistoryAll(userId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addHistory(@RequestBody classHistory classHistory) {
		classHistoryService.addHistory(classHistory);
	}
	
	@RequestMapping("/get/{userId}/{date}")
	public List<classHistory> getUserHistoryDay(@PathVariable int userId, @PathVariable String date) {
		return classHistoryService.getUserHistoryDay(userId, date);
	}
	
}
