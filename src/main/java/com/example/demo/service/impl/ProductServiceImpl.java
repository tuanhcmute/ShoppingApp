package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDto;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.IdUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;	
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public ProductDto findById(String id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent())
			return ProductMapper.toDto(product.get());
		return null;
	}

	@Override
	public ProductDto upsert(ProductDto product) {
		Category foundCategory = categoryRepository.findById(product.getCategoryId()).get();
		Product productSaved = productRepository.findById(product.getId().trim())
				.map(p -> {
					p.setName(product.getName());
					p.setActive(product.isActive());
					p.setDescription(product.getDescription());
					p.setUrlImage(product.getUrlImage());
					return productRepository.save(p);
				})
				.orElseGet(() -> {
					Product p = Product.builder()
							.id(IdUtil.generatedId())
							.name(product.getName())
							.isActive(product.isActive())
							.urlImage(product.getUrlImage())
							.description(product.getDescription())
							.category(foundCategory)
							.build();
					return productRepository.save(p);
				});
		return ProductMapper.toDto(productSaved);
	}

	@Override
	public List<ProductDto> findByCategoryId(String categoryId) {
		List<Product> products = productRepository.findByCategoryId(categoryId);			
		List<ProductDto> productDtos = new ArrayList<>();
		if(products == null || products.size() == 0) return null;
		for (Product product : products) {
			productDtos.add(ProductMapper.toDto(product));
		}
		return productDtos;
	}

}
