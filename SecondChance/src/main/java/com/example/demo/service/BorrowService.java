package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Borrow;
import com.example.demo.repo.BorrowRepo;

@Service
public class BorrowService {
	@Autowired
	private BorrowRepo repo;
	
	public List<Borrow>getStu(String bid){
		if(repo.whoBorrowed(bid).isEmpty()) {
			throw new Error("student not found");
		}
		return repo.whoBorrowed(bid);
	}
}
