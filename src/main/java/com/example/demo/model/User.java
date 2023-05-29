package com.example.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbluser")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false, name = "hashed_password")
	private String hashedPassword;
	
	@Column(name = "full_name", nullable = false)
	private String fullName;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	Set<Role> roles = new HashSet<>();
}
