package com.example.kafka_warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaWarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaWarehouseApplication.class, args);
	}

}
