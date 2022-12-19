package com.microservice.product.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.product.service.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
