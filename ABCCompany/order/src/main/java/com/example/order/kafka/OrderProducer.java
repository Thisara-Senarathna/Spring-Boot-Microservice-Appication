package com.example.order.kafka;

import com.example.base.dto.OrderEventDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventDTO.class);

    private final NewTopic orderTopic;
    private final KafkaTemplate<String,OrderEventDTO> kafkaTemplate;

    public OrderProducer(NewTopic orderTopic, KafkaTemplate<String, OrderEventDTO> kafkaTemplate) {
        this.orderTopic = orderTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(OrderEventDTO orderEventDTO){
        LOGGER.info(String.format("Sending order event to topic %s", orderEventDTO.toString()));


    }

}
