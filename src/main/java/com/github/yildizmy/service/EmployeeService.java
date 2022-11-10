package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.EmployeeRequestMapper;
import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.EmployeeDto;
import com.github.yildizmy.exception.EntityNotFoundException;
import com.github.yildizmy.model.Employee;
import com.github.yildizmy.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.yildizmy.common.Constants.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // if @NaturalId is used for email field of Employee then findBySimpleNaturalId() method is used instead of findByEmail()
    public EmployeeDto findByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .map(EmployeeDto::new)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
    }

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

    public CommandResponse deleteById(Long id) {
        final Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
        employeeRepository.delete(employee);
        return CommandResponse.builder().id(id).build();
    }

    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
