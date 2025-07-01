package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, String>{
	@Query("select b from Book b where b.genre = ?1")
	public List<Book> findByGenre(String gen);
}
