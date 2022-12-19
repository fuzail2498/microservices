package com.microservice.inventory.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.inventory.service.domain.Inventory;
import com.microservice.inventory.service.dto.InventoryRequest;
import com.microservice.inventory.service.dto.InventoryResponse;
import com.microservice.inventory.service.repository.InventoryRepository;

@Service
public class InventoryService {
	@Autowired
	private InventoryRepository inventoryRepository;

	public List<InventoryResponse> isItemInStock(List<String> skuCodes) {
		List<Inventory> itemFromInventory = inventoryRepository.findBySkuCodeIn(skuCodes);
		if (itemFromInventory == null) {
			throw new RuntimeException("Invalid SKU-CODE");
		}
		return itemFromInventory.stream().map(i -> new InventoryResponse(i)).collect(Collectors.toList());
	}

	public InventoryResponse createAndUpdateInventory(InventoryRequest inventoryRequest) {
		Inventory itemFromInventory = inventoryRepository.findBySkuCode(inventoryRequest.getSkuCode());
		if (itemFromInventory != null) {
			itemFromInventory.setSkuCode(inventoryRequest.getSkuCode());
			itemFromInventory.setQuantity(inventoryRequest.getQuantity());
		} else {
			itemFromInventory = new Inventory(null, inventoryRequest.getSkuCode(), inventoryRequest.getQuantity());
		}
		inventoryRepository.save(itemFromInventory);
		return new InventoryResponse(itemFromInventory);
	}

	public List<InventoryResponse> getAllItemsFormInventory() {
		return inventoryRepository.findAll().parallelStream().map(i -> new InventoryResponse(i))
				.collect(Collectors.toList());
	}

}
