package com.example.kafka.command.service;

import com.example.kafka.api.request.OrderCancelRequest;
import com.example.kafka.api.request.OrderRequest;
import com.example.kafka.command.action.OrderAction;
import com.example.kafka.entities.CancelOrder;
import com.example.kafka.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OrderService {


    private OrderAction orderAction;

    @Autowired
    public void setOrderAction(OrderAction orderAction) {
        this.orderAction = orderAction;
    }

    public Long saveOrder(OrderRequest orderRequest) {
        //1. Convert OrderRequest to Order
        Order order = orderAction.convertToOrder(orderRequest);
        //2. Save order to database
        Long id = orderAction.saveToDatabase(order);
        //3. flatten item and order as kafka message and publish it
        order.getItems().forEach(orderAction::publishToKafka);
        //4. Return order number (auto generated).
        return id;


    }

    public void cancelOrder(OrderCancelRequest request) {
        //0 Есть ли такой ордер?
        if(!orderAction.orderExists(request)) throw new NoSuchElementException(String.format("Order with id: '%s' does not exist",
                request.getOrderId()));
        //1. Convert OrderCancelRequest to CancelOrder
        CancelOrder cancelOrder = orderAction.convertToCancelOrder(request);
        //2. Save CancelOrder to database
        orderAction.saveToDatabase(cancelOrder);
        //3. Send to kafka
        orderAction.publishCanceltoKafka(cancelOrder);

    }
}
