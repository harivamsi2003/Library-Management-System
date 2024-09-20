package com.example.LMS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.LMS.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT p FROM User p WHERE "+
			"p.name LIKE CONCAT('%',:query,'%')")
	List<User> searchUsers(String query);
	
	Optional<User> findById(Long id);
}
