package it.erManzo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.erManzo.model.User;
import it.erManzo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);

	}

	@Override
	public boolean controlloUnique(User user) {
		List<User> listaUser = (List<User>) userRepository.findAll();
		for (User u : listaUser) {
			if (user.getUsername().equals(u.getUsername())) {
				return false; // gia' registrato
			}
		}
		return true; // disponibile
	}

}
