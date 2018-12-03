package com.project.classes;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClassesService {

		@Autowired
		private ClassesRepository classRepo;
		
		public void addNewClass(Classes newClass) {
			classRepo.save(newClass);
		}
		
		public ArrayList<Classes> findAllByInstructorId(int instructorId){
			return classRepo.findAllByInstructorId(instructorId);
		}
		
		public Classes getClassByClassId(int classId) {
			return classRepo.findByClassId(classId);
		}
		public void submitEdit(Classes editedClass) {
			classRepo.submitEdit(editedClass.getClassId(), editedClass.getClassName(), editedClass.getClassDescription(), editedClass.getSchedule(), editedClass.getStatus(), editedClass.getBillboard());
		}
		public void lockinChanges(LockInClass changes, int classId, boolean lock) {
			classRepo.lockIn(classId, changes.getNewStatus(), changes.getNewBillboard(), lock);
		}
		public void unlockClass(boolean unlock, int classId) {
			classRepo.unlockClass(classId, unlock);
		}
		public void deleteClass(int classId) {
			classRepo.deleteByClassId(classId);
		}
}
