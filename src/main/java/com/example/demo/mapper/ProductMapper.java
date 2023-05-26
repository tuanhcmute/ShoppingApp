package com.example.demo.mapper;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.Product;

public class ProductMapper {

	public static ProductDto toDto(Product product) {
		return ProductDto.builder()
				.id(product.getId())
				.name(product.getName())
				.urlImage(product.getUrlImage())
				.description(product.getDescription())
				.available(product.getAvailable())
				.price(product.getPrice())
				.build();
	}
}
