package com.github.yildizmy.util;

import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.dto.response.EmployeeResponse;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.yildizmy.common.Constants.FAILED_TO_IMPORT;
import static com.opencsv.ICSVWriter.*;

@SuppressWarnings("java:S1118")
public class CsvHelper {

    public static List<EmployeeRequest> importFromCsv(String fileName) {
        List<EmployeeRequest> employees = new ArrayList<>();
        try {
            // create a reader
            Resource resource = new ClassPathResource("data/" + fileName);
            Reader reader = new FileReader(resource.getFile());

            // can also be created using BufferedReader
            // Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            // column names
            String[] columns = {"id", "firstName", "lastName", "email", "country", "dateOfBirth"};

            // create a mapping strategy
            ColumnPositionMappingStrategy<EmployeeRequest> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(EmployeeRequest.class);
            strategy.setColumnMapping(columns);

            // create csv bean reader
            CsvToBean<EmployeeRequest> csvToBean = new CsvToBeanBuilder<EmployeeRequest>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // iterate through employees
            for (EmployeeRequest request : csvToBean) {
                EmployeeRequest employee = new EmployeeRequest(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getCountry(),
                        request.getDateOfBirth()
                );
                employees.add(employee);
            }
            // close the reader
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    public static void exportToCsv(PrintWriter writer, List<EmployeeResponse> employees) throws IOException {
        try {
            // create a csv writer
            StatefulBeanToCsv<EmployeeResponse> beanWriter = new StatefulBeanToCsvBuilder<EmployeeResponse>(writer)
                    .withSeparator(DEFAULT_SEPARATOR)
                    .withQuotechar(NO_QUOTE_CHARACTER)
                    .withEscapechar(DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(DEFAULT_LINE_END)
                    .withOrderedResults(false)
                    .build();

            // write all employees to csv file
            beanWriter.write(employees);

            // close the writer
            writer.close();
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException ex) {
            throw new IOException(FAILED_TO_IMPORT + ex.getMessage());
        }
    }
}
