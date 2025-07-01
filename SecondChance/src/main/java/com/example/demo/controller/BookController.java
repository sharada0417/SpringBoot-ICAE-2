package com.example.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService service;
	@GetMapping("/genere/{gen}")
	public ResponseEntity<List<Book>> getDept(@PathVariable("gen") String gen){
		return new ResponseEntity<List<Book>>(service.getByGenre(gen),HttpStatus.OK);
	}
}
