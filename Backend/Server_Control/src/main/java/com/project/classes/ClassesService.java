package com.project.classes;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClassesService {

		@Autowired
		private ClassesRepository classRepo;
		/**
		 * Save new class to DB.
		 * @param newClass
		 */
		public void addNewClass(Classes newClass) {
			classRepo.save(newClass);
		}
		/**
		 * Return list of classes with same instructorId.
		 * @param instructorId
		 * @return
		 */
		public ArrayList<Classes> findAllByInstructorId(int instructorId){
			return classRepo.findAllByInstructorId(instructorId);
		}
		/**
		 * Return class with same classId.
		 * @param classId
		 * @return
		 */
		public Classes getClassByClassId(int classId) {
			return classRepo.findByClassId(classId);
		}
		/**
		 * Call Repository function to submit edited class.
		 * @param editedClass
		 */
		public void submitEdit(Classes editedClass) {
			classRepo.submitEdit(editedClass.getClassId(), editedClass.getClassName(), editedClass.getClassDescription(), editedClass.getSchedule(), editedClass.getStatus(), editedClass.getBillboard());
		}
		/**
		 * Submit changes to billboard and status
		 * @param changes
		 * @param classId
		 * @param lock
		 */
		public void lockinChanges(LockInClass changes, int classId, boolean lock) {
			classRepo.lockIn(classId, changes.getNewStatus(), changes.getNewBillboard(), lock);
		}
		/**
		 * Set the locked field of a class to false.
		 * @param unlock
		 * @param classId
		 */
		public void unlockClass(boolean unlock, int classId) {
			classRepo.unlockClass(classId, unlock);
		}
		/**
		 * Delete class with matching classId.
		 * @param classId
		 */
		public void deleteClass(int classId) {
			classRepo.deleteByClassId(classId);
		}
}
