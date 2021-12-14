package com.example.kafka.broker.producer;

import com.example.kafka.broker.message.CancelOrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class CancelOrderProducer {

    private KafkaTemplate<Long, CancelOrderMessage> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger(CancelOrderProducer.class);

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<Long, CancelOrderMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(CancelOrderMessage cancelOrderMessage) {
        kafkaTemplate.send("t_order", cancelOrderMessage.getOrderId(), cancelOrderMessage).addCallback(
                new ListenableFutureCallback<SendResult<Long, CancelOrderMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        logger.error("Error : could not proccess {}", cancelOrderMessage);
                    }

                    @Override
                    public void onSuccess(SendResult<Long, CancelOrderMessage> result) {
                        logger.info("Success : processed {}", cancelOrderMessage);
                    }
                }
        );
    }
}
