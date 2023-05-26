package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CategoryDto;

public interface CategoryService {
	List<CategoryDto> findAll();
	CategoryDto findById(String id);
}
