package it.erManzo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.erManzo.model.Event;
import it.erManzo.model.User;
import it.erManzo.repository.UserRepository;
import it.erManzo.security.service.AuthService;
import it.erManzo.service.UserService;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public UserDetails authenticate(@RequestBody User principal) throws Exception {
		UserDetails useredetail = authService.authenticate(principal);
		return useredetail;
	}

	@PostMapping("/register")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		try {
			User saved = new User();
			if (userService.controlloUnique(user)) {
				user.setPassword(encoder.encode(user.getPassword()));
				saved = userRepository.save(user);
			} else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getUserModel")
	public User getModel() {
		return new User();
	}

	@RequestMapping(value = "/logoutApp", method = RequestMethod.GET)
	public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}
	
	@GetMapping("/findByUsername/{username}")
	public ResponseEntity<User> findByUsername(@PathVariable("username") String username) {
		try {
			User found = userService.findByUsername(username);
			return new ResponseEntity<>(found, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
