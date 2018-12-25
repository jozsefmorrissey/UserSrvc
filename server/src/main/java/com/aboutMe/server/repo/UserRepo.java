package com.aboutMe.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aboutMe.server.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User getByEmail(String email);
    User getByEmailAndPassword(String email, String password);
}
