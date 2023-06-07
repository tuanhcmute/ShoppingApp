package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Category;

@SpringBootTest
public class CategoryServiceTest {
	@Autowired
	CategoryService categoryService;
	
	@Test
	void findAllWithNoArgs() {
		assertThat(categoryService.findAll()).isNotNull();
	}
	
	@Test
	void findByIdWithoutId() {
		String id = "";
		assertThat(categoryService.findById(id)).isNull();
	}
	
	@Test 
	void findByIdWithIdString() {
		String id = "c4c3173e5a7a4674aea11e730e07db2f";
		assertThat(categoryService.findById(id));
	}
	
//	@Test
	void createCategory() {
		Category category = Category.builder()
								.id("12345")
								.name("Laptop")
								.build();
		assertThat(categoryService.upsert(category)).isNotNull();
	}
	
	@Test
	void updateCategoryById() {
		Category category = Category.builder()
				.id("c4c3173e5a7a4674aea11e730e07db2f")
				.name("Mobile")
				.build();
		assertThat(categoryService.upsert(category)).isNotNull();	
	}
}
