package com.example.kafka.command.action;

import com.example.kafka.api.request.OrderCancelRequest;
import com.example.kafka.api.request.OrderItemRequest;
import com.example.kafka.api.request.OrderRequest;
import com.example.kafka.broker.message.CancelOrderMessage;
import com.example.kafka.broker.message.OrderMessage;
import com.example.kafka.broker.producer.CancelOrderProducer;
import com.example.kafka.broker.producer.OrderProducer;
import com.example.kafka.entities.CancelOrder;
import com.example.kafka.entities.Order;
import com.example.kafka.entities.OrderItem;
import com.example.kafka.repositories.CancelOrderRepository;
import com.example.kafka.repositories.OrderItemRepository;
import com.example.kafka.repositories.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderAction {

    private OrderProducer orderProducer;

    private CancelOrderProducer cancelOrderProducer;

    private OrderRepository orderRepository;

    private OrderItemRepository orderItemRepository;

    private EntityManagerFactory entityManagerFactory;

    private CancelOrderRepository cancelOrderRepository;

    @Autowired
    public void setOrderProducer(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    public void setCancelOrderRepository(CancelOrderRepository cancelOrderRepository) {
        this.cancelOrderRepository = cancelOrderRepository;
    }

    @Autowired
    public void setEntityManager(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Autowired
    public void setCancelOrderProducer(CancelOrderProducer cancelOrderProducer) {
        this.cancelOrderProducer = cancelOrderProducer;
    }

    public Order convertToOrder(OrderRequest orderRequest) {
        Order order = new Order();

        order.setOrderDateTime(LocalDateTime.now());
        order.setCreditCardNumber(orderRequest.getCreditCardNumber());
        order.setOrderLocation(orderRequest.getOrderLocation());
        order.setItems(
                orderRequest.getItems().stream()
                    .map(s -> convertToOrderItem(s, order))
                    .collect(Collectors.toList())
        );
        return order;
    }

    //Настроить Cascading
    public long saveToDatabase(Order order) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        long index = orderRepository.save(order).getOrderId();
        entityManager.getTransaction().commit();

        //order.getItems().forEach(orderItemRepository::save);
        return index;
    }

    public void publishToKafka(OrderItem orderItem) {

        orderProducer.publish(convertToOrderMessage(orderItem));
    }

    private OrderItem convertToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setItemName(orderItemRequest.getItemName());
        orderItem.setPrice(orderItemRequest.getPrice());
        orderItem.setQuantity(orderItemRequest.getQuantity());
        return orderItem;
    }

    private OrderMessage convertToOrderMessage(OrderItem orderItem) {
        OrderMessage orderMessage = new OrderMessage();

        orderMessage.setOrderId(orderItem.getOrder().getOrderId());
        orderMessage.setOrderDateTime(orderItem.getOrder().getOrderDateTime());
        orderMessage.setCreditCardNumber(orderItem.getOrder().getCreditCardNumber());

        orderMessage.setPrice(orderItem.getPrice());
        orderMessage.setQuantity(orderItem.getQuantity());
        orderMessage.setItemName(orderItem.getItemName());

        return orderMessage;
    }

    public CancelOrder convertToCancelOrder(OrderCancelRequest request) {
        CancelOrder cancelOrder = new CancelOrder();
        cancelOrder.setOrder(orderRepository.findById(request.getOrderId()).get());
        cancelOrder.setCanceledOn(LocalDateTime.now());
        return cancelOrder;
    }

    public boolean orderExists(OrderCancelRequest request) {
        return orderRepository.findById(request.getOrderId()).isPresent();
    }

    public CancelOrder saveToDatabase(CancelOrder cancelOrder) {
        return cancelOrderRepository.save(cancelOrder);
    }

    public void publishCanceltoKafka(CancelOrder cancelOrder) {
        CancelOrderMessage cancelOrderMessage = new CancelOrderMessage(cancelOrder.getOrder_id());
        cancelOrderProducer.sendMessage(cancelOrderMessage);
    }
}
