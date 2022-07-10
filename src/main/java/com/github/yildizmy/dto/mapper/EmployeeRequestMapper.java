package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.model.Employee;

public class EmployeeRequestMapper {

    public static Employee mapToEntity(EmployeeRequest request) {
        return new Employee(request.getName(), request.getEmail(), request.getCountry(), request.getAge());
    }
}
