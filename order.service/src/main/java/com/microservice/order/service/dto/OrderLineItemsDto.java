package com.microservice.order.service.dto;

import java.math.BigDecimal;

import com.microservice.order.service.domain.OrderLineItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
	private Long id;
	private String skuCode;
	private BigDecimal price;
	private Integer quantity;

	public OrderLineItemsDto(OrderLineItems o) {
		this.id = o.getId();
		this.skuCode = o.getSkuCode();
		this.price = o.getPrice();
		this.quantity = o.getQuantity();
	}

}
