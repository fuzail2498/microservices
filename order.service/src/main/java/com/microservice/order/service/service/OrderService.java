package com.microservice.order.service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservice.order.service.domain.Order;
import com.microservice.order.service.domain.OrderLineItems;
import com.microservice.order.service.dto.InventoryResponse;
import com.microservice.order.service.dto.OrderRequest;
import com.microservice.order.service.dto.OrderResponse;
import com.microservice.order.service.event.OrderNotificationEvent;
import com.microservice.order.service.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	KafkaTemplate<String, OrderNotificationEvent> kafkaTemplate;
	
	public static final String baseUrl = "http://inventory-service/api/inventory";

	public OrderResponse placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItems> ol = orderRequest.getOrderLineItemsDTO().stream().map(o -> new OrderLineItems(o))
				.collect(Collectors.toList());
		order.setOrderLineItems(ol);
		for (OrderLineItems o : ol) {
			o.setOrder(order);
		}
		List<String> skuCodes = orderRequest.getOrderLineItemsDTO().stream().map(o -> o.getSkuCode())
				.collect(Collectors.toList());

		// check wheather the item is in stock or not
		InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
				.uri(baseUrl, uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build()).retrieve()
				.bodyToMono(InventoryResponse[].class).block();
		boolean allMatch = Arrays.stream(inventoryResponses).allMatch(i -> i.isInStock());
		if (allMatch) {
			order = orderRepository.save(order);
			ListenableFuture<SendResult<String,OrderNotificationEvent>> send = kafkaTemplate.send("notification-topic", new OrderNotificationEvent(order.getOrderNumber()));
			send.addCallback(new ListenableFutureCallback<SendResult<String, OrderNotificationEvent>>() {

	        @Override
	        public void onSuccess(SendResult<String, OrderNotificationEvent> o) {
	            System.out.println("Save successfully send order notification event =[" + o + 
	              "] with offset=[" + o.getRecordMetadata().offset() + "]");
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	            System.out.println("Unable to send event msg " + ex.getMessage());
	        }
	    });
			log.info("Order Placed Successfully");
		} else {
			log.error("Currently the user want to place the order in not present in the inventory");
			throw new RuntimeException("Items not in the inventory");
		}
		return new OrderResponse(order);

	}

}
