package com.project.classes;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @author Ben Zaley
 *
 */
public interface ClassesRepository extends JpaRepository<Classes, Integer>, CrudRepository<Classes, Integer> {
	/**
	 *
	 * @param instructorId
	 * @return
	 */
	public ArrayList<Classes> findAllByInstructorId(int instructorId);
	/**
	 *
	 * @param classId
	 * @return
	 */
	public Classes findByClassId(int classId);
	/**
	 * 
	 * @param classId
	 * @return
	 */
    public boolean existsByClassId(int classId);
	/**
	 *
	 * @param classId
	 */
	@Transactional
	public void deleteByClassId(int classId);
	/**
	 * Update existing entry in SQL table with updated className, classDescription, schedule, status, and billboard.
	 * @param classId
	 * @param className
	 * @param classDescription
	 * @param schedule
	 * @param status
	 * @param billboard
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE classes cl SET cl.class_name = :eclassName, cl.class_description = :eclassDescription, cl.schedule = :eschedule, cl.status = :estatus, cl.billboard = :ebillboard WHERE(cl.class_id = :classId)", nativeQuery = true)
	public void submitEdit(@Param("classId") int classId, @Param("eclassName") String className, @Param("eclassDescription") String classDescription, @Param("eschedule") String schedule, @Param("estatus") String status, @Param("ebillboard") String billboard);
	/**
	 * Lock in updates to class billboard and status. Set class lock to true, found with classId.
	 * @param classId
	 * @param status
	 * @param billboard
	 * @param lock
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE classes cl SET cl.status = :nStatus, cl.billboard = :nbillboard, cl.locked = :nLock WHERE(cl.class_id = :classId)", nativeQuery = true)
	public void lockIn(@Param("classId") int classId, @Param("nStatus") String status, @Param("nbillboard") String billboard, @Param("nLock") boolean lock);
	/**
	 * Set class lock to false, found with classId.
	 * @param classId
	 * @param unlock
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE classes cl SET cl.locked = :unlock WHERE(cl.class_id = :classId)", nativeQuery = true)
	public void unlockClass(@Param("classId") int classId, @Param("unlock") boolean unlock);
}
