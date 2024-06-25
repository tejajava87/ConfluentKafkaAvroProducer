package com.kafka.producer.service;

import com.kafka.producer.dto.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, EmployeeDTO> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, EmployeeDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Map<EmployeeDTO, String> publishAllRecords(List<EmployeeDTO> employees) {
        Map<EmployeeDTO, String> statusMap = new HashMap<>();
        employees.forEach(employee -> {
            ListenableFuture<SendResult<String, EmployeeDTO>> future = kafkaTemplate.send("all-records-topic", employee);
            future.addCallback(
                    result -> {
                        onSuccess(result);
                        statusMap.put(employee, "SUCCESS");
                    },
                    ex -> {
                        onFailure(ex);
                        statusMap.put(employee, "FAILED");
                    }
            );
        });
        return statusMap;
    }

    private void onSuccess(SendResult<String, EmployeeDTO> result) {
        log.info("Sent message=[{}] with offset=[{}]", result.getProducerRecord().value(),
                result.getRecordMetadata().offset());
    }

    private void onFailure(Throwable ex) {
        log.error("Unable to send message due to: {}", ex.getMessage());
    }
}
