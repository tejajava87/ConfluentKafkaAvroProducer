package com.kafka.producer.service;

import com.kafka.producer.dto.EmployeeDTO;
import com.kafka.producer.entity.Employee;
import com.kafka.producer.entity.RecordLog;
import com.kafka.producer.repo.EmployeeRepository;
import com.kafka.producer.repo.RecordLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private RecordLogRepository recordLogRepository;


    @Transactional
    public void processAndPublishRecords() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = convertToDTO(employees);

        kafkaProducerService.publishAllRecords(employeeDTOs);
        Map<EmployeeDTO, String> publishStatusMap = kafkaProducerService.publishAllRecords(employeeDTOs);
        logRecords(employeeDTOs, "ALL", publishStatusMap);

    }

    private List<EmployeeDTO> convertToDTO(List<Employee> employees) {
        return employees.stream()
                .map(this::employeeToDTO)
                .collect(Collectors.toList());
    }

    private EmployeeDTO employeeToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setPosition(employee.getPosition());
        dto.setDepartment(employee.getDepartment());
        return dto;
    }


    private void logRecords(List<EmployeeDTO> employeeDTOs, String flag, Map<EmployeeDTO, String> statusMap) {
        employeeDTOs.forEach(employeeDTO -> {
            RecordLog log = new RecordLog();
            log.setRecord(employeeDTO.toString());
            log.setEventType(flag);
            log.setStatus(statusMap.getOrDefault(employeeDTO, "UNKNOWN"));
            recordLogRepository.save(log);
        });
    }
}
