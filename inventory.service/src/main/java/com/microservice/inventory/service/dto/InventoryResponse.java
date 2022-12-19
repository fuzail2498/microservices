package com.microservice.inventory.service.dto;

import com.microservice.inventory.service.domain.Inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
	private Long id;
	private String skuCode;
	private Integer quantity;
	private boolean inStock;

	public InventoryResponse(Inventory itemFromInventory) {
		this.id = itemFromInventory.getId();
		this.skuCode = itemFromInventory.getSkuCode();
		this.quantity = itemFromInventory.getQuantity();
		this.inStock = itemFromInventory.getQuantity() <= 0 ? false : true;
	}

}
