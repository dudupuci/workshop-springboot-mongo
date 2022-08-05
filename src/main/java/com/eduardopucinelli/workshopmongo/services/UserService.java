package com.eduardopucinelli.workshopmongo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardopucinelli.workshopmongo.domain.User;
import com.eduardopucinelli.workshopmongo.dto.UserDTO;
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

	public User insert(User user) {
		return repo.insert(user);
	}

	public void updateData(User newUser, User oldUser) {
		newUser.setName(oldUser.getName());
		newUser.setEmail(oldUser.getEmail());
	}

	public User update(User oldUser) {
		User newUser = repo.findById(oldUser.getId()).orElse(null);
		updateData(newUser, oldUser);
		return repo.save(newUser);

	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
