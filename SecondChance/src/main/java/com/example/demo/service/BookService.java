package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.repo.BookRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService {
	@Autowired
	private BookRepo repo;
	
	public List<Book>getByGenre(String gen){
		if(repo.findByGenre(gen).isEmpty()) {
			throw new EntityNotFoundException("Book not Found");
		}
		return repo.findByGenre(gen);
	}
}
