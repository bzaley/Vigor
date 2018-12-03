package com.project.userClass;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @author Ryan Ingram
 *
 */
public interface userClassRepository extends CrudRepository<userClass, Integer> {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<userClass> findAllByUserId(int userId);
	/**
	 * 
	 * @param classId
	 * @return
	 */
	public List<userClass> findAllByClassId(int classId);
	/**
	 * 
	 * @param userId
	 */
	@Transactional
	public void deleteByUserId(int userId);
}
