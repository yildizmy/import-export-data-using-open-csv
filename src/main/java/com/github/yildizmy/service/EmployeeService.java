package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.EmployeeRequestMapper;
import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.dto.response.EmployeeResponse;
import com.github.yildizmy.domain.Employee;
import com.github.yildizmy.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.github.yildizmy.common.Constants.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // if @NaturalId is used for email field of Employee then findBySimpleNaturalId() method is used instead of findByEmail()
    public EmployeeResponse findByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .map(EmployeeResponse::new)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
    }

    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll().stream()
                .map(EmployeeResponse::new)
                .toList();
    }

    public EmployeeResponse create(EmployeeRequest request) {
        final Employee employee = EmployeeRequestMapper.mapToEntity(request);
        return new EmployeeResponse(employeeRepository.save(employee));
    }

    public List<EmployeeResponse> create(List<EmployeeRequest> requests) {
        final List<Employee> employees = requests.stream()
                .map(EmployeeRequestMapper::mapToEntity)
                .toList();
        final List<Employee> saved = employeeRepository.saveAll(employees);
        return saved.stream().map(EmployeeResponse::new).toList();
    }

    public void deleteById(Long id) {
        final Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
        employeeRepository.delete(employee);
    }

    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
