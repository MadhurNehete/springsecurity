package com.learn.controller;

import java.security.Principal;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.model.Users;
import com.learn.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userservice;

	@GetMapping("/allUsers")
	public List<Users> getAllUsers() {
		return userservice.getAllUsers();
	}

	@GetMapping("/{username}")
	public Users getUser(@PathVariable String username) {
		return userservice.getUser(username);

	}

	@PostMapping("/add")
	public Users addUsers(@Valid @RequestBody Users user) {
		return userservice.addUser(user);

	}

	@PutMapping("/update")
	public Users updateUsers(@Valid @RequestBody Users user, Principal principal) {

		return userservice.updateUser(user, principal);

	}

	@DeleteMapping("/deleteAcc")
	public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> payload, Principal principal) {

		String rawPassword = payload.get("password");
		String userName = principal.getName();

		userservice.deleteUserIfPasswordMatches(userName, rawPassword);
		return ResponseEntity.ok("User Removed Succesfully!!!");

	}

}
