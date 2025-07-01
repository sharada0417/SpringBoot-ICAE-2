package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.model.BookLend;
import com.example.demo.model.Borrow;
import com.example.demo.repo.BookRepo;
import com.example.demo.repo.BorrowRepo;
import com.example.demo.repo.StudentRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BorrowService {
	@Autowired
	private BorrowRepo repo;
	
	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private StudentRepo studentRepo;
	
	public List<Borrow>getStu(String bid){
		if(repo.whoBorrowed(bid).isEmpty()) {
			throw new EntityNotFoundException("student not found");
		}
		return repo.whoBorrowed(bid);
	}
	
	public String borrowBook(BookLend lend) {
		if(!bookRepo.findById(lend.getBookId()).isPresent()) {
			throw new EntityNotFoundException("Book not found");
		}
		
		if(!studentRepo.findById(lend.getStudentId()).isPresent()){
			throw new EntityNotFoundException("student not found");
		}
		
		if(repo.unreturnBooks(lend.getStudentId())>2) {
			throw new RuntimeException("Please return the books");
		}
		
		if(bookRepo.findById(lend.getBookId()).get().getCopiesAvailable()<2){
			throw new RuntimeException("Not enough copies available");
		}
		
		
		Borrow borrow = new Borrow();
		borrow.setBook(bookRepo.findById(lend.getBookId()).get());
		borrow.setStudent(studentRepo.findById(lend.getStudentId()).get());
		Book book = bookRepo.findById(lend.getBookId()).get();
		
		int copies=book.getCopiesAvailable();
		book.setCopiesAvailable(copies - 1);
		bookRepo.save(book);
		repo.save(borrow);
		return "sucess";
	}
	
	
}
