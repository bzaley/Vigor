package com.project.classHistory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author Ryan Ingram
 *
 */
@RestController
@RequestMapping("/classHistory")
public class classHistoryController {

	
	@Autowired
	private classHistoryService classHistoryService;
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getAll/{userId}")
	public List<classHistory> getUserHistoryAll(@PathVariable int userId) {
		return classHistoryService.getUserHistoryAll(userId);
	}
	/**
	 * 
	 * @param classHistory
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void addHistory(@RequestBody classHistory classHistory) {
		classHistoryService.addHistory(classHistory);
	}
	/**
	 * 
	 * @param userId
	 * @param date
	 * @return
	 */
	@RequestMapping("/get/{userId}/{date}")
	public List<classHistory> getUserHistoryDay(@PathVariable int userId, @PathVariable String date) {
		return classHistoryService.getUserHistoryDay(userId, date);
	}
	
}
