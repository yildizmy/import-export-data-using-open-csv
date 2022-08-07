package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.EmployeeRequestMapper;
import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.dto.response.EmployeeDto;
import com.github.yildizmy.model.Employee;
import com.github.yildizmy.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream()
                .map(EmployeeDto::new)
                .toList();
    }

    public EmployeeDto create(EmployeeRequest request) {
        final Employee employee = EmployeeRequestMapper.mapToEntity(request);
        return new EmployeeDto(employeeRepository.save(employee));
    }

    public List<EmployeeDto> create(List<EmployeeRequest> requests) {
        final List<Employee> employees = requests.stream()
                .map(EmployeeRequestMapper::mapToEntity)
                .toList();
        final List<Employee> saved = employeeRepository.saveAll(employees);
        return saved.stream().map(EmployeeDto::new).toList();
    }

    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
