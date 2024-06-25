package com.kafka.producer.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String name;
    private String position;
    private String department;

}
