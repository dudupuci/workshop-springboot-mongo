package com.eduardopucinelli.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eduardopucinelli.workshopmongo.domain.Post;
import com.eduardopucinelli.workshopmongo.domain.User;
import com.eduardopucinelli.workshopmongo.dto.UserDTO;
import com.eduardopucinelli.workshopmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	// Search in mongodb the users and store in list.
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insertUser(@RequestBody UserDTO userDto) {
		User user = service.fromDTO(userDto);
		user = service.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

		// Returns 201 created and pass the new path of new register
		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@RequestBody UserDTO userDto, @PathVariable String id) {
		User user = service.fromDTO(userDto);
		user.setId(id);
		user = service.update(user);
		return ResponseEntity.noContent().build();

	}

	@RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> getPosts(@PathVariable String id) {
		User user = service.findById(id);
		return ResponseEntity.ok().body(user.getPosts());

	}
						

}
