package com.example.LMS.model;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	private Long user_id;
	private String name;
	private boolean returning;
	private boolean borrowing;
	private String bookIds;
	private String returnBookIds;
	@OneToMany(mappedBy = "borrowedby", cascade = CascadeType.ALL)
	private List<Book> books;
}
