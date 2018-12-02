package com.project.classes;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClassesRepository extends JpaRepository<Classes, Integer>, CrudRepository<Classes, Integer> {

	public ArrayList<Classes> findAllByInstructorId(int instructorId);
	public Classes findByClassId(int classId);
	@Transactional
	public void deleteByClassId(int classId);
	@Modifying
	@Transactional
	@Query(value = "UPDATE classes cl SET cl.class_name = :eclassName, cl.class_description = :eclassDescription, cl.schedule = :eschedule, cl.status = :estatus, cl.billboard = :ebillboard WHERE(cl.class_id = :classId)", nativeQuery = true)
	public void submitEdit(@Param("classId") int classId, @Param("eclassName") String className, @Param("eclassDescription") String classDescription, @Param("eschedule") String schedule, @Param("estatus") String status, @Param("ebillboard") String billboard);
	@Modifying
	@Transactional
	@Query(value = "UPDATE classes cl SET cl.status = :nStatus, cl.billboard = :nbillboard, cl.locked = :nLock WHERE(cl.class_id = :classId)", nativeQuery = true)
	public void lockIn(@Param("classId") int classId, @Param("nStatus") String status, @Param("nbillboard") String billboard, @Param("nLock") boolean lock);
	@Modifying
	@Transactional
	@Query(value = "UPDATE classes cl SET cl.locked = :unlock WHERE(cl.class_id = :classId)", nativeQuery = true)
	public void unlockClass(@Param("classId") int classId, @Param("unlock") boolean unlock);
}
