package com.example.kafka_warehouse.configs;

import com.example.kafka_warehouse.entities.CancelOrderMessage;
import com.example.kafka_warehouse.entities.OrderMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<Long, OrderMessage> consumerFactory() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.INFERRED);
        typeMapper.addTrustedPackages("com.example.kafka.broker.message");

        JsonDeserializer<OrderMessage> valueDeserializer = new JsonDeserializer<>(OrderMessage.class);
        valueDeserializer.setTypeMapper(typeMapper);
        valueDeserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<Long, OrderMessage>(consumerProps(), new LongDeserializer(), valueDeserializer);
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        /*props.put(JsonDeserializer.TYPE_MAPPINGS, "OrderMessage:com.example.kafka.broker.message.OrderMessage, \n" +
                "      CancelOrderMessage:com.example.kafka.broker.message.CancelOrderMessage");*/
        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, OrderMessage> kafkaListenerContainerFactory(
            ConsumerFactory<Long, OrderMessage> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, OrderMessage> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory);
        containerFactory.setConcurrency(3);
        containerFactory.setMissingTopicsFatal(false);
        return containerFactory;
    }

    @Bean
    public ConsumerFactory<Long, CancelOrderMessage> consumerFactoryOrderCancel() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.INFERRED);
        typeMapper.addTrustedPackages("com.example.kafka.broker.message");

        JsonDeserializer<CancelOrderMessage> valueDeserializer = new JsonDeserializer<>(CancelOrderMessage.class);
        valueDeserializer.setTypeMapper(typeMapper);
        valueDeserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<Long, CancelOrderMessage>(consumerProps(), new LongDeserializer(), valueDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, CancelOrderMessage> kafkaListenerContainerFactoryCancelMessage(
            ConsumerFactory<Long, CancelOrderMessage> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, CancelOrderMessage> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory);
        containerFactory.setConcurrency(3);
        containerFactory.setMissingTopicsFatal(false);
        return containerFactory;
    }


}
