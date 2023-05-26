package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.ResponseObject;
import com.example.demo.expection.InternalServerException;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.util.Constants;


@RestController
@RequestMapping(path = Constants.PREFIX_URL + "/categories")
public class CategoryController {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	CategoryService categoryService;

	@GetMapping("")
	ResponseEntity<ResponseObject> getAllCategories() {
		String status = "";
		String message = "";
		try {
			status = Constants.STATUS_OK;
			message = "Get all categories successfully";
			List<CategoryDto> categorDtos = categoryService.findAll();
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject(status, message, categorDtos)
			);
		} catch (Exception e) {
			throw new InternalAuthenticationServiceException(e.getMessage());
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
			CategoryDto categoryDto = categoryService.findById(id);
			if(categoryDto != null) {
				message = "Get category by id successfully";
				return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(status, message, categoryDto)
				);
			}
			message = "Can not find category with id = " + id;
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject(status, message, null)
			);
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
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
			throw new InternalServerException(e.getMessage());
		}
	}
}














