package com.example.demo.model;

import java.io.Serializable;
					
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tblcategory")
@Data // Auto generate getter, setter, toString, hasCode, equals
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	String id;

	@Column(nullable = false, unique = true)
	String name;
	
	public Category(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	List<Product> products = new ArrayList<>();
	
	@JsonIgnore
	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setCategory(this);
		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCategory(null);
		return product;
	}
	
}
