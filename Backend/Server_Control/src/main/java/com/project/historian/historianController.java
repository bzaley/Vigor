package com.project.historian;

import java.util.List;

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
@RequestMapping("/historian")
public class historianController {

	@Autowired
	private historianService historianService;

	/**
	 * get all archived exercises by date.
	 * @param userEmail
	 * @param saveDate
	 * @return
	 */
	@RequestMapping("/get/{userEmail}/{saveDate}")
	public List<historianReturn> getAllByDate(@PathVariable String userEmail, @PathVariable String saveDate) {
		return historianService.getExercisesForDate(userEmail, saveDate);
	}
}
