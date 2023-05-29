package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CategoryDto;
import com.example.demo.model.Category;

public interface CategoryService {
	List<CategoryDto> findAll();
	CategoryDto findById(String id);
	CategoryDto upsert(Category category);
}
