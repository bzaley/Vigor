package com.project.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
//CrudRepository handles all generic calls for entity classes
//Extension is CrudRepository<Class being used, id type>
/**
 * 
 * @author Ben Zaley
 *
 */
public interface UserRepository extends CrudRepository<User, Integer>{
	/**
	 * 
	 * @param userEmail
	 * @param password
	 * @return
	 */
	public User findByUserEmailAndPassword(String userEmail, String password);
	/**
	 * 
	 * @param userEmail
	 * @return
	 */
	public User findByUserEmail(String userEmail);
	/**
	 * 
	 * @param userEmail
	 * @return
	 */
	public boolean existsByUserEmail(String userEmail);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User findByUserId(int userId);
	
	@Transactional
	public void deleteByUserId(int userId);
}
