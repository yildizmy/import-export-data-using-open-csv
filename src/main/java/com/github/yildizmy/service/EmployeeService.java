package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.EmployeeRequestMapper;
import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.dto.response.EmployeeDto;
import com.github.yildizmy.model.Employee;
import com.github.yildizmy.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    public void create(List<EmployeeRequest> request) {
        final List<Employee> employees = request.stream()
                .map(EmployeeRequestMapper::mapToEntity)
                .collect(Collectors.toList());
        employeeRepository.saveAll(employees);
    }
}
