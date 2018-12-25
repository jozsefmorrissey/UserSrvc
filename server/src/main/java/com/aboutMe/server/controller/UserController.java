package com.aboutMe.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aboutMe.server.entities.User;
import com.aboutMe.server.service.UserSrvc;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserSrvc userSrvc;
	
	@PostMapping("/add")
	public void addUser(@RequestBody User user) {
		userSrvc.addUser(user);
	}

	@PostMapping("/login")
	public User loginUser(@RequestBody User user) {
		return userSrvc.loginUser(user);
	}

	@PostMapping("/get")
	public User getUser(@RequestBody User user) {
		return userSrvc.getUser(user.getEmail());
	}

	@PostMapping("/update")
	public void updatePassword(@RequestBody User user) {
		userSrvc.updatePassword(user);
	}

	@PostMapping("/reset/password")
	public void updateEmail(@RequestBody User user) {
		userSrvc.resetPassword(user);
	}
}
