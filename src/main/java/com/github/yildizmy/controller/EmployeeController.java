package com.github.yildizmy.controller;

import com.github.yildizmy.dto.response.EmployeeDto;
import com.github.yildizmy.service.EmployeeService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws Exception{

        //set file name and content type
        String filename = "employees.csv";

        response.setContentType("text/csv");
        // "Content-Disposition" indicates file attachment to the browser
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<EmployeeDto> writer = new StatefulBeanToCsvBuilder<EmployeeDto>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        //write all employees to csv file
        writer.write(employeeService.findAll());
    }
}
