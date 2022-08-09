package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.model.Employee;

@SuppressWarnings("java:S1118")
public class EmployeeRequestMapper {

    // TODO: JMapper or MapStruct library might be used for mapping

    public static Employee mapToEntity(EmployeeRequest request) {
        return new Employee(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getCountry(),
                request.getDateOfBirth()
        );
    }
}
