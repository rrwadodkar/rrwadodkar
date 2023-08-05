package com.user.management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.model.User;
import com.user.management.repository.UserRepository;

import exceptions.ErrorResponse;

@RestController
@RequestMapping(value = "/userService")
public class UserController {

	@Autowired
	UserRepository userRepo;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/users")
	public ResponseEntity<Object> getUsers() {
		try {
			List<User> users = new ArrayList<User>();
			userRepo.findAll().forEach(users::add); 
			return ResponseEntity.ok(users);

		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new ErrorResponse(99L, "An Error has Occurred, Please try the request again"));
		}

	}
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "/addUser")
	public ResponseEntity<Object> addUser(@RequestBody(required = true) User user) {
		try {

			List<User> existingUsers = userRepo.findByUserName(user.getUserName());
			System.out.println(existingUsers);
			if (existingUsers.size() > 0) {
				return ResponseEntity.badRequest().body(new ErrorResponse(99L, "User Already Exists!!!"));
			}
			User userResponse = userRepo.save(user);
			return ResponseEntity.accepted().body(userResponse);

		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/updateUser")
	public ResponseEntity<Object> updateUser(@RequestBody User user) {

		try {
			List<User> users = userRepo.findByUserName(user.getUserName());
			if (users.size() > 0) {
				for (User u : users) {
					Optional<User> updated = userRepo.findById(u.getUserID());
					updated.get().setFirstName(user.getFirstName());
					updated.get().setLastName(user.getLastName());
					updated.get().setPassword(user.getPassword());
					userRepo.save(updated.get());
				}
				return ResponseEntity.ok(userRepo.findByUserName(user.getUserName())) ;

			} else {
				return ResponseEntity.badRequest().body(new ErrorResponse(99L,"No User found for Updating data"));

			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping(value = "/deleteUser/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable(name = "id") Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (user.isEmpty()) {
			return ResponseEntity.notFound().build();

		} else {
			userRepo.deleteById(userId);
			return ResponseEntity.ok(new ErrorResponse(0L, "User Sccessfully Deleted"));
		}
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<Object> getUser(@PathVariable(name="id") Long userId) { 
		Optional<User> user = userRepo.findById(userId);
		if(user.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(user); 
		}
	}
}
