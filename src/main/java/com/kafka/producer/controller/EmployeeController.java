package com.kafka.producer.controller;

import com.kafka.producer.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/process-records")
    public String processRecords() {
        employeeService.processAndPublishRecords();
        return "Records processed and published successfully";
    }
}
