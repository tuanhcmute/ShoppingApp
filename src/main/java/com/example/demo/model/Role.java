package com.example.demo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tblrole")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Column(unique = true, nullable = false)
	private String name;
}
