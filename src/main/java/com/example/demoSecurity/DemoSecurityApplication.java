package com.example.demoSecurity;

import com.example.demoSecurity.entity.Role;
import com.example.demoSecurity.entity.User;
import com.example.demoSecurity.service.UserService;
import org.hibernate.mapping.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class DemoSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args ->{
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "Mattia Lombardi", "Mattia", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Ken Adams", "ken", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Regina Phalange", "regina", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Chandler Bing", "Miss Bong", "1234", new ArrayList<>()));

			userService.addRoleToUser("Mattia", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("ken", "ROLE_USER");
			userService.addRoleToUser("ken", "ROLE_USER");
			userService.addRoleToUser("regina", "ROLE_ADMIN");
			userService.addRoleToUser("Miss Bong", "ROLE_MANAGER");
		};
	}

}
