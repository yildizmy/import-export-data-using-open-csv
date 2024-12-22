package com.github.yildizmy.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Long id;

    @NonNull
    @CsvBindByPosition(position = 1)
    private String firstName;

    @NonNull
    @CsvBindByPosition(position = 2)
    private String lastName;

    @NonNull
    @CsvBindByPosition(position = 3)
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @CsvBindByPosition(position = 4)
    private String country;

    @NonNull
    @CsvBindByPosition(position = 5)
    private LocalDate dateOfBirth;

    public Employee(String firstName, String lastName, String email, String country, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return email.equals(employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
