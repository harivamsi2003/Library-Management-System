package com.example.LMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

import com.example.LMS.model.Book;
import com.example.LMS.model.User;
import com.example.LMS.service.BookService;
import com.example.LMS.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/")
	public String viewUsers(Model model) {
		model.addAttribute("allUserList", userService.getAllUsers());
		return "users";
	}
	
	@GetMapping("/addNewUser")
	public String addNewUser(Model model) {
		User user=new User();
		model.addAttribute("user", user);
		return "newUser";
	}
	
	@PostMapping("/save")
	public String saveUser(@ModelAttribute("user") User user) {
		userService.saveUser(user);
		boolean isreturning=user.isReturning();
		boolean isborrowing=user.isBorrowing();
		System.out.println(isreturning+" "+isborrowing+" "+user.getReturnBookIds()+" "+user.getBookIds());
		String[] borrowids=user.getBookIds().split(",");
		String[] returnids = null;
		if(user.getReturnBookIds()!=null) returnids=user.getReturnBookIds().split(",");
		if(isreturning)
		{
			for(String str:returnids)
			{
				Long id=Long.parseLong(str);
				Book book=bookService.getBookById(id);
				book.setBorrowed(false);
				book.setBorrowedby(null);
				if(user.getBooks()!=null) user.getBooks().remove(book);
				userService.saveUser(user);
			}
		}
		if(isborrowing)
		{
			for(String str:borrowids)
			{
				Long id=Long.parseLong(str);
				Book book=bookService.getBookById(id);
				if(!book.isBorrowed())
				{
					book.setBorrowed(true);
					book.setBorrowedby(user);
					if(user.getBooks()==null) user.setBooks(new ArrayList<Book>());
					user.getBooks().add(book);
					userService.saveUser(user);
				}
			}
		}
		return "redirect:/users/";
	}
	
	@GetMapping("/showFormForUpdateUser/{id}")
	public String updateUser(@ModelAttribute(value = "id") long id, Model model) {
		User user=userService.getUserById(id);
		model.addAttribute("user", user);
		return "updateUser";
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@ModelAttribute(value = "id") long id, Model model) {
		userService.deleteUser(id);
		return "redirect:/users/";
	}
	
	@GetMapping("/searchUsers")
	public String searchUsers(Model model,@RequestParam("keyword") String query) {
		List<User> list=userService.searchUsers(query);
		model.addAttribute("allUserList", list);
		model.addAttribute("keyword", query);
		return "users";
	}
}
