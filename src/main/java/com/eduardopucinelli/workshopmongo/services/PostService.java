package com.eduardopucinelli.workshopmongo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardopucinelli.workshopmongo.domain.Post;
import com.eduardopucinelli.workshopmongo.repositories.PostRepository;
import com.eduardopucinelli.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public Post findById(String id) {
		Post post = postRepository.findById(id).orElse(null);
		if (post == null) {
			throw new ObjectNotFoundException("Post not found, try again.");
		}
		return post;
	}

	public List<Post> findByTitle(String text) {
		return postRepository.searchTitle(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return postRepository.fullSearch(text, minDate, maxDate);
	}
	
	
}
