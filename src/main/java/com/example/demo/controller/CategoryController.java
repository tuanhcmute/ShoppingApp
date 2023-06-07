package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.example.demo.expection.InternalServerException;
import com.example.demo.model.Category;
import com.example.demo.response.ResponseObject;
import com.example.demo.service.CategoryService;
import com.example.demo.util.HttpStatusUtil;
import com.example.demo.util.PathUtil;


@RestController
@RequestMapping(path = PathUtil.PREFIX_URL + "/categories")
public class CategoryController {
	
	private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;

	@GetMapping("")
	ResponseEntity<ResponseObject> getAllCategories() {
		try {
			String status = HttpStatusUtil.OK.toString();
			String message = "Get all categories successfully";
			// Lấy tất cả category
			List<CategoryDto> categorDtos = categoryService.findAll();
			logger.info(message);
			return ResponseEntity.status(HttpStatus.OK).body(
					ResponseObject.builder()
					.status(status)
					.message(message)
					.statusCode(HttpStatusUtil.OK.getValue())
					.data(categorDtos)
					.build()
			);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalAuthenticationServiceException(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	ResponseEntity<ResponseObject> findById(@PathVariable String id) {
		try {
			String message = "";
			if(id.isEmpty() || id.equals("")) {
				message = "Id is required";
				logger.warn(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					ResponseObject.builder()
						.status(HttpStatusUtil.BAD_REQUEST.toString())
						.message(message)
						.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
						.data("")
						.build()
				);
			}
			// Lấy category theo id
			CategoryDto categoryDto = categoryService.findById(id);
			if(categoryDto != null) {
				message = "Get category by id successfully";
				logger.info(message);
				return ResponseEntity.status(HttpStatus.OK).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.OK.toString())
					.message(message)
					.statusCode(HttpStatusUtil.OK.getValue())
					.data(categoryDto)
					.build()
				);
			}
			message = "Can not find category with id = " + id;
			logger.warn(message);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.NOT_FOUND.toString())
					.message(message)
					.statusCode(HttpStatusUtil.NOT_FOUND.getValue())
					.data("")
					.build()
			);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
	}

	@PostMapping("/upsert")
	ResponseEntity<ResponseObject> upsertCategory(@RequestBody Category category) {
		try {
			String message = "";
			if(category == null) {
				message = "Category cannot null";
				logger.warn(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.BAD_REQUEST.toString())
					.message(message)
					.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
					.data("")
					.build()
				);
			}
			if(category.getId().isEmpty() || category.getId().equals("")) {
				message = "Category id is required";
				logger.warn(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
						.status(HttpStatusUtil.BAD_REQUEST.toString())
						.message(message)
						.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
						.data("")
						.build()
					);
			}
			if(category.getName().isEmpty() || category.getName().equals("")) {
				message = "Category name is required";
				logger.warn(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
						.status(HttpStatusUtil.BAD_REQUEST.toString())
						.message(message)
						.statusCode(HttpStatusUtil.BAD_REQUEST.getValue())
						.data("")
						.build()
				);
			}
			// Nếu trùng thì update, không trùng thì tiến hành create
			CategoryDto categorySaved = categoryService.upsert(category);
			message = "Upsert category successfully";
			logger.info(message);
			return ResponseEntity.status(HttpStatus.OK).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.OK.toString())
					.message(message)
					.statusCode(HttpStatusUtil.OK.getValue())
					.data(categorySaved)
					.build()
			);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
	}
}














