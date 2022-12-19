package com.microservice.product.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.product.service.domain.Product;
import com.microservice.product.service.dto.ProductRequest;
import com.microservice.product.service.dto.ProductResponse;
import com.microservice.product.service.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
	@Autowired
	private ProductRepository productRepo;

	public ProductResponse createProduct(ProductRequest productRequest) {
		Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription())
				.price(productRequest.getPrice()).build();
		product = productRepo.save(product);
		log.info("Inside Product Service product is created with product id : {}", product.getId());
		return new ProductResponse(product);

	}

	public List<ProductResponse> getAllproducts() {
		return productRepo.findAll().stream().map(p -> new ProductResponse(p)).collect(Collectors.toList());
	}

}
