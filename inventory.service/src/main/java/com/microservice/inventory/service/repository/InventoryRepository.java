package com.microservice.inventory.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.inventory.service.domain.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	Inventory findBySkuCode(String skuCode);

	List<Inventory> findBySkuCodeIn(List<String> skuCodes);

}
