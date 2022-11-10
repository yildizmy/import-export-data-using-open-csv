package com.github.yildizmy.dto.response;

import com.github.yildizmy.model.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private LocalDate dateOfBirth;

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.country = employee.getCountry();
        this.dateOfBirth = employee.getDateOfBirth();
    }
}
