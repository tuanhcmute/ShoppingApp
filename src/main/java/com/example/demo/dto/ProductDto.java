package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // Generate constructor with all parameter
@NoArgsConstructor // Generate default constructor
@Builder
public class ProductDto {
	String id;
	String name;
	String urlImage;
	String description;
	String categoryId;
	Long available;
	Long price;
	boolean isActive;
}