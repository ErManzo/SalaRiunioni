package it.erManzo.repository;

import org.springframework.data.repository.CrudRepository;

import it.erManzo.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	User findByUsername(String username);

}
