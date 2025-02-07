package com.wipro.bankofamerica.estore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.bankofamerica.estore.model.User;
import com.wipro.bankofamerica.estore.service.UserService;

@RestController
@RequestMapping("/api/userinfo")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User savedUser = service.saveUser(user);
		return ResponseEntity.ok(savedUser);
		
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {
		List<User> allUser = service.getAllUser();
		return ResponseEntity.ok(allUser);
	}
	
	@GetMapping("/getcity/{city}")
	public ResponseEntity<List<User>> getListUserByCity(@PathVariable("city") String city) {
	   List<User> user = service.getListByCity(city);
	   return ResponseEntity.ok(user);
	}
	
//	@GetMapping("/user/{username}")
//	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
//		User user = service.getUserByUsername(username);
//		return ResponseEntity.ok(user);
//	}
	

}
