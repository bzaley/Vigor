package com.project.historian;

import java.util.ArrayList;
import java.util.List;
import com.project.user.*;
import com.project.Exercise.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class historianService {

	@Autowired
	private historianRepository historianRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ExerciseRepository exerciseRepo;
	
	public void addHistory(historian historian) {
		historianRepo.addHistory(historian.getUserId(),
				historian.getExerciseId(),
				historian.getSets(),
				historian.getReps(),
				historian.getSaveDate());
	}
	
	public List<historianReturn> getExercisesForDate(String userEmail, String saveDate) {

		User user = userRepo.findByUserEmail(userEmail);
		int userId = user.getuserId();

		List<historianReturn> right = new ArrayList<historianReturn>();

		List<historian> wrong = historianRepo.findAllByUserIdAndSaveDate(userId, saveDate);

		for (historian tmp : wrong) {

			Exercise exercise = exerciseRepo.findByExerciseId(tmp.getExerciseId());
			String name = exercise.getName();

			historianReturn toAdd = new historianReturn(name, tmp.getSets(), tmp.getReps());

			right.add(toAdd);
		}

		return right;

	}

}
