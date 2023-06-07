package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryControllerTest {
	
	@Autowired
	CategoryController categoryController;
	
	@Test
	void findAll() {
		@SuppressWarnings("null")
		int statusCode = categoryController.getAllCategories().getBody().getStatusCode();
		assertEquals(statusCode, 200);
	}
	
	@SuppressWarnings("null")
//	@Test
	void findByIdWithoutId() {
		String id = "";
		assertEquals(categoryController.findById(id).getBody().getStatus(), 400);
	}
}
