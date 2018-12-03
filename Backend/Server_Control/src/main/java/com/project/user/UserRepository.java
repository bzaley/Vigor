package com.project.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
//CrudRepository handles all generic calls for entity classes
//Extension is CrudRepository<Class being used, id type>
public interface UserRepository extends CrudRepository<User, Integer>{
	
	public User findByUserEmailAndPassword(String userEmail, String password);
	public User findByUserEmail(String userEmail);
	public boolean existsByUserEmail(String userEmail);
	public User findByUserId(int userId);
	
	@Transactional
	public void deleteByUserId(int userId);
}
