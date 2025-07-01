package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Borrow;

public interface BorrowRepo extends JpaRepository<Borrow, Integer>{
	@Query("select b from Borrow b where b.book.id =?1")
	public List<Borrow> whoBorrowed(String bid);

}
