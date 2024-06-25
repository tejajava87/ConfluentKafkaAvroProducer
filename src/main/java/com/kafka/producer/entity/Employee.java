package com.kafka.producer.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    private Long id;
    private String name;
    private String position;
    private String department;
}