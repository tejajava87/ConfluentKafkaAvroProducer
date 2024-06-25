package com.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConfluentKafkaAvroProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfluentKafkaAvroProducerApplication.class, args);
	}

}
