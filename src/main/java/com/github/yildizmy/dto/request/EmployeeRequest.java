package com.github.yildizmy.dto.request;

import lombok.Value;

@Value
public class EmployeeRequest {
    String name;
    String email;
    String country;
    int age;
}
