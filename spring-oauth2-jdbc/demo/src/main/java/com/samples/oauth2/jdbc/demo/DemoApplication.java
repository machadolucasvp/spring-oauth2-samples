package com.samples.oauth2.jdbc.demo;

import java.util.HashSet;
import java.util.Set;

import com.samples.oauth2.jdbc.demo.models.Role;
import com.samples.oauth2.jdbc.demo.models.User;
import com.samples.oauth2.jdbc.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		createUser("Admin", "lucasvufma@gmail.com", passwordEncoder.encode("123"), "ROLE_USER");
		createUser("Cliente", "cliente@gmail.com", passwordEncoder.encode("123"), "ROLE_ADMIN");
	}

	public void createUser(String name, String email, String password, String roleName) {

		Role role = new Role();
		role.setRole(roleName);
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		User user = new User(name, email, password, roles);
		userRepository.save(user);
	}

}
