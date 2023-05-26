package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> findAll() {
		List<Category> categories = categoryRepository.findAll();
		if(categories == null) return null;
		List<CategoryDto> categoryDtos = new ArrayList<>();
		for (Category category : categories) {
			categoryDtos.add(CategoryMapper.toDto(category));
		}
		return categoryDtos;
	}

	@Override
	public CategoryDto findById(String id) {
		 Optional<Category> category = categoryRepository.findById(id);
		 if(category.isPresent()) 
			 return CategoryMapper.toDto(category.get());
		 return null;
	}
}
