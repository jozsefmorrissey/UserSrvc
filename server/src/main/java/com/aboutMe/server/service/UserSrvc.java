package com.aboutMe.server.service;

import com.aboutMe.server.entities.User;

public interface UserSrvc {
	public void addUser(User user);
	public User loginUser(User user);
	public boolean authinticate(User user);
	public User getUser(String email);
	public void updatePassword(User user);
	public void updateEmail(User user, String newEmail);
	void resetPassword(User user);
}
