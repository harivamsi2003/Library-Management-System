package com.example.LMS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.LMS.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	@Query("SELECT p FROM Book p WHERE "+
			"p.title LIKE CONCAT('%',:query,'%')"+
			"OR p.author LIKE CONCAT('%',:query,'%')")
	List<Book> searchBooks(String query);
	
	Optional<Book> findById(Long book_id);
}
