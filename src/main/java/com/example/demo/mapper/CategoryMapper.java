package com.example.demo.mapper;

import com.example.demo.dto.CategoryDto;
import com.example.demo.model.Category;

public class CategoryMapper{
	public static CategoryDto toDto(Category category) {
		CategoryDto categoryDto = CategoryDto.builder()
				.id(category.getId())
				.name(category.getName())
				.build();
		return categoryDto;	
	}
}
