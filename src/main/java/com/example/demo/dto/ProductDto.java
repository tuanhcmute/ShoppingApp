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
	private String id;
	private String name;
	private String urlImage;
	private String description;
	private String categoryId;
	private Long available;
	private Long price;
	private boolean isActive;
}