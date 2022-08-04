package com.eduardopucinelli.workshopmongo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardopucinelli.workshopmongo.domain.User;
import com.eduardopucinelli.workshopmongo.repositories.UserRepository;
import com.eduardopucinelli.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	// Get all users
	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		User user = repo.findById(id).orElse(null);
		if (user == null) {
			throw new ObjectNotFoundException("Object not found, try again.");
		}
		return user;
	}

}
