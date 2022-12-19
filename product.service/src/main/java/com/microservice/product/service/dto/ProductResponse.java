package com.microservice.product.service.dto;

import java.math.BigDecimal;

import com.microservice.product.service.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;

	public ProductResponse(Product product) {
		this.id = product.getId();
		this.description = product.getDescription();
		this.name = product.getName();
		this.price = product.getPrice();

	}

}
