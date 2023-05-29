package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ProductDto;

public interface ProductService {
	ProductDto findById(String id);
	ProductDto upsert(ProductDto product);
	List<ProductDto> findByCategoryId(String categoryId);
}
