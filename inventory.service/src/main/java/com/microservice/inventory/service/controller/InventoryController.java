package com.microservice.inventory.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.inventory.service.dto.InventoryRequest;
import com.microservice.inventory.service.dto.InventoryResponse;
import com.microservice.inventory.service.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
	@Autowired
	private InventoryService inventoryService;

	@GetMapping()
	public List<InventoryResponse> isItemInStock(@RequestParam(name = "skuCodes") List<String> skuCodes)
			throws InterruptedException {
//		System.out.println("-----------Wait started-----------");
//		Thread.sleep(10000);
//		System.out.println("-----------Wait ended-----------");
		return inventoryService.isItemInStock(skuCodes);
	}

	@PostMapping()
	public InventoryResponse createAndUpdateInventory(@RequestBody InventoryRequest inventoryRequest) {
		return inventoryService.createAndUpdateInventory(inventoryRequest);

	}

	@GetMapping("/all")
	public List<InventoryResponse> getAllItemsFormInventory() {
		return inventoryService.getAllItemsFormInventory();
	}

}
