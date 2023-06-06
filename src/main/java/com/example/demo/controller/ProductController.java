package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ResponseObject;
import com.example.demo.expection.InternalServerException;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.util.HttpStatusCodeUtil;
import com.example.demo.util.HttpStatusUtil;
import com.example.demo.util.PathUtil;

@RestController
@RequestMapping(path = PathUtil.PREFIX_URL + "/products")
public class ProductController {
	
	private final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;

	@GetMapping("/{id}")
	ResponseEntity<ResponseObject> findById(@PathVariable String id) {
		String message = "";
		try {
			if(id.isEmpty() || id.equals("")) {
				message = "Id is required";
				logger.warn(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.BAD_REQUEST.toString())
					.message(message)
					.statusCode(HttpStatusCodeUtil.BAD_REQUEST)
					.data("")
					.build()
				);
			}
			ProductDto productDto = productService.findById(id);
			if(productDto != null) {
				message = "Get product by id successfully";
				logger.info(message);
				return ResponseEntity.status(HttpStatus.OK).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.OK.toString())
					.message(message)
					.statusCode(HttpStatusCodeUtil.OK)
					.data(productDto)
					.build()
				);
			}
			message = "Can not find product with id = " + id;
			logger.warn(message);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				ResponseObject.builder()
				.status(HttpStatusUtil.NOT_FOUND.toString())
				.message(message)
				.statusCode(HttpStatusCodeUtil.NOT_FOUND)
				.data("")
				.build()
			);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
	}
	
	@PostMapping(value = "/upsert", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseObject> upsertProduct(@RequestBody ProductDto product) {
		String message = "";
		try {
			if(product.getId().isEmpty() || product.getId().equals("")) {
				message = "Product id is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
						.status(HttpStatusUtil.BAD_REQUEST.toString())
						.message(message)
						.statusCode(HttpStatusCodeUtil.BAD_REQUEST)
						.data("")
						.build()
				);
			}
			if(product.getName().isEmpty() || product.getName().equals("")) {
				message = "Product name is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
						.status(HttpStatusUtil.BAD_REQUEST.toString())
						.message(message)
						.statusCode(HttpStatusCodeUtil.BAD_REQUEST)
						.data("")
						.build()
				);
			}
			if(product.getCategoryId().isEmpty() || product.getCategoryId().equals("")) {
				message = "Category id is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ResponseObject.builder()
						.status(HttpStatusUtil.BAD_REQUEST.toString())
						.message(message)
						.statusCode(HttpStatusCodeUtil.BAD_REQUEST)
						.data("")
						.build()
				);
			}
			CategoryDto foundCategory = categoryService.findById(product.getCategoryId());
			if(foundCategory == null) {
				message = "Category is not exist";
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						ResponseObject.builder()
						.status(HttpStatusUtil.NOT_FOUND.toString())
						.message(message)
						.statusCode(HttpStatusCodeUtil.NOT_FOUND)
						.data("")
						.build()
				);
			}
			// Nếu tìm product tồn tại thì tiến hành update, còn không tồn tại thì tiến hành tạo mới
			ProductDto productDto = productService.upsert(product);
			message = "Create product successfully";
			logger.info(message);
			return ResponseEntity.status(HttpStatus.OK).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.OK.toString())
					.message(message)
					.statusCode(HttpStatusCodeUtil.OK)
					.data(productDto)
					.build()
			);
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	@GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseObject> findByCategoryId(@RequestParam("category_id") String categoryId) {
		String message = "";
		try {
			if(categoryId.isEmpty() || categoryId.equals("")) {
				message = "Category id is required";
				logger.warn(message);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.BAD_REQUEST.toString())
					.message(message)
					.statusCode(HttpStatusCodeUtil.BAD_REQUEST)
					.data("")
					.build()
				);
			}

			List<ProductDto> products = productService.findByCategoryId(categoryId);			
			if(products == null) {
				message = "Category id is not exist";
				logger.warn(message);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					ResponseObject.builder()
					.status(HttpStatusUtil.NOT_FOUND.toString())
					.message(message)
					.statusCode(HttpStatusCodeUtil.NOT_FOUND)
					.data("")
					.build()
				);
			}
			message = "Find products by category successfully";
			return ResponseEntity.status(HttpStatus.OK).body(
				ResponseObject.builder()
				.status(HttpStatusUtil.OK.toString())
				.message(message)
				.statusCode(HttpStatusCodeUtil.OK)
				.data(products)
				.build()
			);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
	}
}
