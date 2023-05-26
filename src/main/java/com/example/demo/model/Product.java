package com.example.demo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblproduct")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	String id;
	@Column(unique = true, nullable = false)
	String name;
	@Column(name = "url_image")
	String urlImage;
	String description;
	Long available;
	Long price;
	boolean isActive;		
	@ManyToOne
	@JoinColumn(name = "category_id")
	Category category;
}
