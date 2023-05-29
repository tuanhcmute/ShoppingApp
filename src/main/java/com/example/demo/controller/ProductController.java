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
		try {
			String status = HttpStatusUtil.OK.toString();
			String message = "";
			if(id.isEmpty() || id.equals("")) {
				message = "Id is required";
				logger.warn(message);
				return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(status, message, "")
				);
			}
			ProductDto productDto = productService.findById(id);
			if(productDto != null) {
				message = "Get product by id successfully";
				logger.info(message);
				return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(status, message, productDto)
				);
			}
			message = "Can not find product with id = " + id;
			logger.warn(message);
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject(status, message, "")
			);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
	}
	
	@PostMapping(value = "/upsert", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseObject> upsertProduct(@RequestBody ProductDto product) {
		try {
			String status = HttpStatusUtil.OK.toString();
			String message = "";
			if(product.getId().isEmpty() || product.getId().equals("")) {
				message = "Product id is required";
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject(status, message, "")
				);
			}
			if(product.getName().isEmpty() || product.getName().equals("")) {
				message = "Product name is required";
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject(status, message, "")
				);
			}
			if(product.getCategoryId().isEmpty() || product.getCategoryId().equals("")) {
				message = "Category id is required";
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject(status, message, "")
				);
			}
			CategoryDto foundCategory = categoryService.findById(product.getCategoryId());
			if(foundCategory == null) {
				message = "Category is not exist";
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject(status, message, "")
				);
			}
			// Nếu tìm product tồn tại thì tiến hành update, còn không tồn tại thì tiến hành tạo mới
			ProductDto productDto = productService.upsert(product);
			message = "Create product successfully";
			logger.info(message);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(status, message, productDto)
			);
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	@GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseObject> findByCategoryId(@RequestParam("category_id") String categoryId) {
		try {
			String status = HttpStatusUtil.OK.toString();
			String message = "";
			if(categoryId.isEmpty() || categoryId.equals("")) {
				message = "Category id is required";
				logger.warn(message);
				return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(status, message, "")
				);
			}

			List<ProductDto> products = productService.findByCategoryId(categoryId);			
			if(products == null) {
				message = "Category id is not exist";
				logger.warn(message);
				return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject(status, message, "")
				);
			}

			message = "Find products by category successfully";
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject(status, message, products)
			);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalServerException(e.getMessage());
		}
	}
}
