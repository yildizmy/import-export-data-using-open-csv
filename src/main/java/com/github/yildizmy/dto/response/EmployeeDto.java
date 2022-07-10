package com.github.yildizmy.dto.response;

import com.github.yildizmy.model.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String name;
    private String email;
    private String country;
    private int age;

    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.country = employee.getCountry();
        this.age = employee.getAge();
    }
}
