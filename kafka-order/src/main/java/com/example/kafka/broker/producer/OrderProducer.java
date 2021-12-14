package com.example.kafka.broker.producer;

import com.example.kafka.broker.message.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class OrderProducer {

    private final static Logger logger = LoggerFactory.getLogger(OrderProducer.class);

    private KafkaTemplate<Long, OrderMessage> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<Long, OrderMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish (OrderMessage message) {
        kafkaTemplate.send("t_order", message.getOrderId(), message).addCallback(
                new ListenableFutureCallback<SendResult<Long, OrderMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        logger.error("Order {}, item {} failed to publish because {}", message.getOrderId(),
                                message.getItemName(), ex.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<Long, OrderMessage> result) {
                        logger.info("Order {}, item {} published successfully", message.getOrderId(),
                                message.getItemName());
                    }
                }
        );
        logger.info("Just a dummy message for order {}, item {} ", message.getOrderId(), message.getItemName());
    }
}
