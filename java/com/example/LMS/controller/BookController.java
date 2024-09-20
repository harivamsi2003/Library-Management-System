package com.example.LMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;
import com.example.LMS.model.Book;
import com.example.LMS.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@GetMapping("/")
	public String viewBooks(Model model) {
		model.addAttribute("allBookList", bookService.getAllBooks());
		return "books";
	}
	
	@GetMapping("/addNewBook")
	public String addNewBook(Model model) {
		Book book=new Book();
		model.addAttribute("book", book);
		return "newBook";
	}
	
	@PostMapping("/save")
	public String saveBook(@ModelAttribute("book") Book book) {
		bookService.saveBook(book);
		return "redirect:/books/";
	}
	
	@GetMapping("/showFormForUpdateBook/{id}")
	public String updateBook(@PathVariable(value = "id") long id, Model model) {
		Book book=bookService.getBookById(id);
		model.addAttribute("book",book);
		return "updateBook";
	}
	
	@GetMapping("/deleteBook/{id}")
	public String deleteBookById(@PathVariable(value = "id") long id, Model model) {
		bookService.deleteBook(id);
		return "redirect:/books/";
	}
	
	@GetMapping("/searchBook")
	public String searchBooks(Model model, @RequestParam("keyword") String query) {
		List<Book> list=bookService.searchBooks(query);
		model.addAttribute("allBookList", list);
		model.addAttribute("keyword", query);
		return "books";
	}
}
