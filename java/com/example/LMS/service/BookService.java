package com.example.LMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.example.LMS.model.Book;
import com.example.LMS.model.User;
import com.example.LMS.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	private UserService userService;
	
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	public List<Book> searchBooks(String query) {
		if(query==null || query.trim().isEmpty()) {
			return bookRepository.findAll();
		}
		try {
			Long id=Long.parseLong(query);
			return bookRepository.findById(id).stream().toList();
		}
		catch(NumberFormatException e) {
			return bookRepository.searchBooks(query);
		}
	}
	
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}
	
	public Book getBookById(Long id) {
		Optional<Book> op=bookRepository.findById(id);
		Book book=null;
		if(op.isPresent())
		{
			book=op.get();
		}
		else throw new RuntimeException("Employee not found for id : " + id);
		return book;
	}
	
	public Book borrowBook(Long bookId, Long userId) {
		Book book=getBookById(bookId);
		User user=userService.getUserById(userId);
		
		if(user!=null && !book.isBorrowed() && user!=null) {
			book.setBorrowed(true);
			book.setBorrowedby(user);
			user.getBooks().add(book);
			userService.saveUser(user);
			return saveBook(book);
		}
		return null;
	}
	
	public Book returnBook(Long bookId, Long userId) {
		Book book=getBookById(bookId);
		User user=userService.getUserById(userId);
		
		if(book!=null && book.isBorrowed()) {
			book.setBorrowed(false);
			book.setBorrowedby(null);
			user.getBooks().remove(book);
			userService.saveUser(user);
			return saveBook(book);
		}
		return null;
	}
	
	public void deleteBook(Long id)
	{
		Optional<Book> existproduct=bookRepository.findById(id);
		if(existproduct.isPresent())
		{
			bookRepository.deleteById(id);
		}
		else
		{
			throw new RuntimeException("Book not found");
		}
	}
}
