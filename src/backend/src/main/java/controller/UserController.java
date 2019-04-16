package controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import repository.UserRepository;

@RestController
@RequestMapping(value="/rest/users")
public class UserController {
		
	
	@Autowired
	UserRepository userRepository;

	
	@GetMapping(value= "/all")
	public List<User> getAll() {
		userRepository.findAll();
	}
	
	@PostMapping(value = "/load")
	public List<Users> persist (@RequestBody final User user) {
		userRepository.save(user);
		return userRepository.findaAll();
	}
}
