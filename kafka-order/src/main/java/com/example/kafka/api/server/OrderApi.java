package com.example.kafka.api.server;

import com.example.kafka.api.request.OrderCancelRequest;
import com.example.kafka.api.request.OrderRequest;
import com.example.kafka.api.response.OrderResponse;
import com.example.kafka.command.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderApi {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
                                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        Long orderNumber = orderService.saveOrder(orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponse(orderNumber));
    }

    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String cancelOrder(@RequestBody OrderCancelRequest request) {
        orderService.cancelOrder(request);
        return null;
    }

}
