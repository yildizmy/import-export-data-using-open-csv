package com.github.yildizmy.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.dto.response.ApiResponse;
import com.github.yildizmy.dto.response.CommandDto;
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
import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static com.github.yildizmy.common.Constants.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {

    private final Clock clock;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper mapper;
    private final EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<ApiResponse<EmployeeDto>> create(@RequestBody EmployeeRequest request) {
        final EmployeeDto employee = employeeService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESSFULLY_CREATED, employee));
    }

    @PostMapping("/employees/{fileName:.+}")
    public ResponseEntity<ApiResponse<CommandDto>> createFromFile(@PathVariable("fileName") String fileName) throws IOException {
        final Resource resource = resourceLoader.getResource("classpath:data/" + fileName);
        final List<EmployeeRequest> requests = mapper.readValue(resource.getFile(), new TypeReference<>() {});
        employeeService.create(requests);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESSFULLY_CREATED));
    }

    @PostMapping("/employees/import/{fileName:.+}")
    public ResponseEntity<ApiResponse<CommandDto>> importFromCsv(@PathVariable("fileName") String fileName) {
        final List<EmployeeRequest> requests = CsvHelper.importFromCsv(fileName);
        employeeService.create(requests);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESSFULLY_CREATED));
    }

    @GetMapping("employees/export/{fileName:.+}")
    public void exportToCsv(HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        final List<EmployeeDto> employees = employeeService.findAll();
        CsvHelper.exportToCsv(response.getWriter(), employees);
    }

    @GetMapping("/employees/{email}")
    public ResponseEntity<ApiResponse<EmployeeDto>> findByEmail(@PathVariable String email) {
        final EmployeeDto employee = employeeService.findByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, employee));
    }

    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> findAll() {
        final List<EmployeeDto> employees = employeeService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, employees));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<CommandDto>> deleteById(@PathVariable Long id) {
        final CommandDto response = employeeService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESSFULLY_DELETED, response));
    }

    @DeleteMapping("/employees")
    public ResponseEntity<ApiResponse<CommandDto>> deleteAll() {
        employeeService.deleteAll();
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESSFULLY_DELETED));
    }
}
