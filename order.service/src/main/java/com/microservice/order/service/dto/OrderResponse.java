package com.microservice.order.service.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.microservice.order.service.domain.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
	private long id;
	private String orderNumber;
	private List<OrderLineItemsDto> orderLineItemsDto;

	public OrderResponse(Order order) {
		this.id = order.getId();
		this.orderNumber = order.getOrderNumber();
		this.orderLineItemsDto = order.getOrderLineItems().stream().map(o -> new OrderLineItemsDto(o))
				.collect(Collectors.toList());
	}
}
