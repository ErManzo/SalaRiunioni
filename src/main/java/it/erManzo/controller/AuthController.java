package it.erManzo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.erManzo.model.User;
import it.erManzo.repository.UserRepository;
import it.erManzo.security.service.AuthService;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("/login")
	public UserDetails authenticate(@RequestBody User principal)throws Exception{
		UserDetails useredetail=authService.authenticate(principal);
		return useredetail;
	}
	
	@PostMapping("/register")
	public User addUser (@RequestBody User user){
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@GetMapping("/getUserModel")
	public User getModel() {
		return new User();
	}
	
	@RequestMapping(value="/logoutApp", method = RequestMethod.GET)
	 public void logoutPage (HttpServletRequest request, HttpServletResponse response) {
	     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	     if (auth != null){    
	         new SecurityContextLogoutHandler().logout(request, response, auth);
	     }
	 }
}
