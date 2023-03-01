package com.github.yildizmy.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.dto.response.ApiResponse;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.EmployeeResponse;
import com.github.yildizmy.service.EmployeeService;
import com.github.yildizmy.util.CsvHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static com.github.yildizmy.common.Constants.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final Clock clock;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper mapper;
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> create(@RequestBody EmployeeRequest request) {
        final EmployeeResponse employee = employeeService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESSFULLY_CREATED, employee));
    }

    @PostMapping("/{fileName:.+}")
    public ResponseEntity<ApiResponse<CommandResponse>> createFromFile(@PathVariable("fileName") String fileName) throws IOException {
        final Resource resource = resourceLoader.getResource("classpath:data/" + fileName);
        final List<EmployeeRequest> requests = mapper.readValue(resource.getFile(), new TypeReference<>() {});
        employeeService.create(requests);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESSFULLY_CREATED));
    }

    @PostMapping("/import/{fileName:.+}")
    public ResponseEntity<ApiResponse<CommandResponse>> importFromCsv(@PathVariable("fileName") String fileName) {
        final List<EmployeeRequest> requests = CsvHelper.importFromCsv(fileName);
        employeeService.create(requests);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESSFULLY_CREATED));
    }

    @GetMapping("/export/{fileName:.+}")
    public void exportToCsv(HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        final List<EmployeeResponse> employees = employeeService.findAll();
        CsvHelper.exportToCsv(response.getWriter(), employees);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> findByEmail(@PathVariable String email) {
        final EmployeeResponse employee = employeeService.findByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, employee));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> findAll() {
        final List<EmployeeResponse> employees = employeeService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, employees));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
       employeeService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAll() {
        employeeService.deleteAll();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
