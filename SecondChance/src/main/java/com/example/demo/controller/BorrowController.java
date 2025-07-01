package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Borrow;
import com.example.demo.service.BorrowService;

@RestController
@RequestMapping("/bor")
public class BorrowController {
	@Autowired
	private BorrowService service;
	@GetMapping("/stu/{id}")
	public ResponseEntity<List<Borrow>> getStudents(@PathVariable("id") String id){
		return new ResponseEntity<List<Borrow>>(service.getStu(id),HttpStatus.OK);
	}
}
