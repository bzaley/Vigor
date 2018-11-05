package com.project.user;

import org.springframework.data.repository.CrudRepository;
//CrudRepository handles all generic calls for entity classes
//Extension is CrudRepository<Class being used, id type>
public interface UserRepository extends CrudRepository<User, Integer>{
	
	public User findByUserEmail(String email);
	
	public User findByUserId(int userId);
}
