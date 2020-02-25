package com.samples.oauth2.mysql.demo;

import com.samples.oauth2.mysql.demo.models.User;
import com.samples.oauth2.mysql.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	

		
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User("Lucas","123456","lucasvufma@gmail.com");
		userRepository.save(user);
	}

}
