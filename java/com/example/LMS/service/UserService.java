package com.example.LMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.example.LMS.model.User;
import com.example.LMS.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public List<User> searchUsers(String query)
	{
		if (query == null || query.trim().isEmpty()) {
            return userRepository.findAll();
        }
		try {
			Long id=Long.parseLong(query);
			return userRepository.findById(id).stream().toList();
		}
		catch(NumberFormatException e){
			return userRepository.searchUsers(query);
		}
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public User getUserById(Long id) {
		Optional<User> op=userRepository.findById(id);
		User user=null;
		if(op.isPresent())
		{
			user=op.get();
		}
		else throw new RuntimeException("User not found for id : " + id);
		return user;
	}
	
	public void deleteUser(Long id) {
		Optional<User> existUser=userRepository.findById(id);
		if(existUser.isPresent())
		{
			User user=existUser.get();
			if(user.getBooks().size()==0) {
				userRepository.deleteById(id);
			}
			else {
				throw new RuntimeException("Book not returned");
			}
		}
		else {
			throw new RuntimeException("User not found");
		}
	}
}
