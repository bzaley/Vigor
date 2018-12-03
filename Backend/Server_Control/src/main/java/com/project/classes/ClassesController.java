package com.project.classes;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classes")
public class ClassesController {

	@Autowired
	private ClassesService classesService;
	/**
	 * 
	 * @param newClass
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/newclass") //Creates a new class and returns join code
	public ResponseEntity<?> newClass(@RequestBody Classes newClass){
		classesService.addNewClass(newClass);
		sendClassId neat = new sendClassId(newClass.getClassId());
		return ResponseEntity.ok().body(neat);
	}
	/**
	 * 
	 * @param instructorId
	 * @return
	 */
	@RequestMapping("/getallinstructorclasses/{instructorId}")
	public ArrayList<Classes> getClassesWithInstructorId(@PathVariable int instructorId){
		return classesService.findAllByInstructorId(instructorId);
	}
	/**
	 * 
	 * @param classId
	 * @return
	 */
	@RequestMapping("/editclass/{classId}")
	public Classes pullForEdit(@PathVariable int classId) {
		return classesService.getClassByClassId(classId);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/submitedit")
	public void submitEdit(@RequestBody Classes editedClass){
		classesService.submitEdit(editedClass);
	}
	/**
	 * 
	 * @param lockingIn
	 * @param classId
	 * @param lock
	 */
	@RequestMapping (method = RequestMethod.POST, value = "/padlock/{classId}/{lock}")
	public void lockUnlock(@RequestBody LockInClass lockingIn, @PathVariable int classId, @PathVariable boolean lock) {
		if(lock) {
			classesService.lockinChanges(lockingIn, classId, lock);
		}else {
			classesService.unlockClass(lock, classId);
		}
	}
	/**
	 * 
	 * @param classId
	 */
	@RequestMapping("/deleteclass/{classId}")
	public void deleteClass(@PathVariable int classId) {
		classesService.deleteClass(classId);
	}
}
