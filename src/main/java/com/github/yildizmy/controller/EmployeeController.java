package com.github.yildizmy.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.dto.response.EmployeeDto;
import com.github.yildizmy.service.EmployeeService;
import com.github.yildizmy.util.CsvHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.github.yildizmy.common.Constants.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper mapper;
    private final EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDto> create(
            @RequestBody EmployeeRequest request) {
        final EmployeeDto employee = employeeService.create(request);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/employees/{fileName}")
    public ResponseEntity<List<EmployeeDto>> createFromFile(
            @PathVariable("fileName") String fileName) throws IOException {
        Resource resource =resourceLoader.getResource("classpath:data/" + fileName + ".json");
        List<EmployeeRequest> requests = mapper.readValue(resource.getFile(), new TypeReference<>() {});
        final List<EmployeeDto> employees = employeeService.create(requests);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> findAll() {
        final List<EmployeeDto> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{email}")
    public ResponseEntity<EmployeeDto> findByEmail(@PathVariable String email) {
        final EmployeeDto employee = employeeService.findByEmail(email);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("employees/export")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FILE_NAME);
        List<EmployeeDto> employees = employeeService.findAll();
        CsvHelper.generateCsv(response.getWriter(), employees);
    }

    @DeleteMapping("/employees")
    public ResponseEntity<String> deleteAll() {
        employeeService.deleteAll();
        return ResponseEntity.ok(SUCCESSFULLY_DELETED);
    }
}
