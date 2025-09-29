package com.restaurant.rms.service;

import com.restaurant.rms.models.User;
import com.restaurant.rms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void registerUser(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole("USER");
		userRepository.save(user);
	}

}
