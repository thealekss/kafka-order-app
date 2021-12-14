package com.example.kafka_warehouse.consumers;

import com.example.kafka_warehouse.entities.CancelOrderMessage;
import com.example.kafka_warehouse.entities.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderMessageConsumer.class);

    @KafkaListener(topics = "t_order", concurrency = "3", groupId = "cg-warehouse", containerFactory = "kafkaListenerContainerFactory")
    public void orderConsumer(OrderMessage orderMessage) {
        logger.info("Order {} is consumed", orderMessage);
    }

    @KafkaListener(topics = "t_order", concurrency = "3", groupId = "cg-warehouse-cancel", containerFactory = "kafkaListenerContainerFactoryCancelMessage")
    public void orderCancelConsumer(CancelOrderMessage cancelOrderMessage) {
        logger.info("Order {} is canceled", cancelOrderMessage.getOrderId());
    }
}
