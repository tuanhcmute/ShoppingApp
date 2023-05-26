package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

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

import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.models.ProductRequest;
import com.example.demo.models.ResponseObject;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.utils.Constants;


@RestController
@RequestMapping(path = Constants.PREFIX_URL + "/products")
public class ProductController {
	@Autowired
	ProductRepository productRepository;	
	
	@Autowired
	CategoryRepository categoryRepository;

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
			Optional<Product> product = productRepository.findById(id);
			if(product.isPresent()) {
				message = "Get product by id successfully";
				return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(status, message, product)
				);
			}
			message = "Can not find product with id = " + id;
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject(status, message, product)
			);
		} catch (Exception e) {
			status = Constants.STATUS_FAIL;
			message = e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				new ResponseObject(status, message, null)
			);
		}
	}
	
	@PostMapping(value = "/upsert", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseObject> upsertProduct(@RequestBody ProductRequest product) {
		String status = "";
		String message = "";
		Product productSaved = null;
		try {
			status = Constants.STATUS_OK;
			if(product.getId().isEmpty() || product.getId().equals("")) {
				message = "Product id is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new ResponseObject(status, message, productSaved)
				);
			}
			if(product.getName().isEmpty() || product.getName().equals("")) {
				message = "Product name is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new ResponseObject(status, message, productSaved)
				);
			}
			if(product.getCategoryId().isEmpty() || product.getCategoryId().equals("")) {
				message = "Category id is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new ResponseObject(status, message, productSaved)
				);
			}
			Category foundCategory = categoryRepository.getCategoryById(product.getCategoryId());
			if(foundCategory == null) {
				message = "Category is not exist";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new ResponseObject(status, message, productSaved)
				);
			}
			productSaved = productRepository.findById(product.getId().trim())
					.map(p -> {
						p.setName(product.getName());
						p.setActive(product.isActive());
						p.setDescription(product.getDescription());
						p.setUrlImage(product.getUrlImage());
						return productRepository.save(p);
					})
					.orElseGet(() -> {
						Product p = new Product();
						p.setId(product.getId());
						p.setName(product.getName());
						p.setActive(product.isActive());
						p.setDescription(product.getDescription());
						p.setUrlImage(product.getUrlImage());
						p.setCategory(foundCategory);
						return productRepository.save(p);
					});
			message = "Create product successfully";
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(status, message, productSaved)
			);
		} catch (Exception e) {
			message = e.getMessage();
			status = Constants.STATUS_FAIL;
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseObject(status, message, productSaved)
			);
		}
	}

	@GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseObject> findByCategoryId(@RequestParam("category_id") String categoryId) {
		String status = "";
		String message = "";
		try {
				status = Constants.STATUS_OK;
			if(categoryId.isEmpty() || categoryId.equals("")) {
				message = "Category id is required";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponseObject(status, message, null)
				);
			}
			List<Product> products = productRepository.findByCategoryId(categoryId);
			if(products == null) {
				message = "Category id is not exist";
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
					new ResponseObject(status, message, null)
				);
			}
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject(status, message, products)
			);
		} catch (Exception e) {
			message = e.getMessage();
			status = Constants.STATUS_FAIL;
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseObject(status, message, null)
			);
		}
	}
}
