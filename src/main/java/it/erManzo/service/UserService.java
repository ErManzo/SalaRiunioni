package it.erManzo.service;

import it.erManzo.model.User;

public interface UserService {
	
	User findByUsername(String username);
	
	void delete(User user);
	
	boolean controlloUnique(User user);

}
