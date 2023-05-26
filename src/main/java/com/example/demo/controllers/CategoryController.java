package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Category;
import com.example.demo.models.ResponseObject;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.utils.Constants;


@RestController
@RequestMapping(path = Constants.PREFIX_URL + "/categories")
public class CategoryController {
	
	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("")
	ResponseEntity<ResponseObject> getAllCategories() {
		String status = "";
		String message = "";
		List<Category> categories = null;
		try {
			status = Constants.STATUS_OK;
			message = "Get all categories successfully";
			categories = categoryRepository.findAll();
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject(status, message, categories)
			);
		} catch (Exception e) {
			status = Constants.STATUS_OK;
			message = e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				new ResponseObject(status, message, categories)
			);
		}
	}
	
	@GetMapping("/{id}")
	ResponseEntity<ResponseObject> findById(@PathVariable String id) {
		String status = "";
		String message = "";
		try {
			status = Constants.STATUS_OK;
			if(id.isEmpty() || id.equals("")) {
				message = "Id is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponseObject(status, message, null)
				);
			}
			Optional<Category> category = categoryRepository.findById(id);
			if(category.isPresent()) {
				message = "Get category by id successfully";
				return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(status, message, category)
				);
			}
			message = "Can not find category with id = " + id;
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject(status, message, category)
			);
		} catch (Exception e) {
			status = Constants.STATUS_FAIL;
			message = e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				new ResponseObject(status, message, null)
			);
		}
	}
	@PostMapping("/upsert")
	ResponseEntity<ResponseObject> insertCategory(@RequestBody Category category) {
		String status = "";
		String message = "";
		Category categorySaved = null;
		try {
			status = Constants.STATUS_OK;
			if(category == null) {
				message = "Category cannot null";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponseObject(status, message, categorySaved)
				);
			}
			if(category.getId().isEmpty() || category.getId().equals("")) {
				message = "Category id is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new ResponseObject(status, message, categorySaved)
					);
			}
			if(category.getName().isEmpty() || category.getName().equals("")) {
				message = "Category name is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new ResponseObject(status, message, categorySaved)
				);
			}
			categorySaved = categoryRepository.findById(category.getId())
					.map(c -> {
						c.setName(category.getName());
						return categoryRepository.save(c);
					}).orElseGet(() -> {
						return categoryRepository.save(category);
					});
			message = "Create product successfully";
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(status, message, categorySaved)
			);
		} catch (Exception e) {
			status = Constants.STATUS_FAIL;
			message = e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseObject(status, message, categorySaved)
			);
		}
	}
}














