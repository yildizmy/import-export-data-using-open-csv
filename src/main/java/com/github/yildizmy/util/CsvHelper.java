package com.github.yildizmy.util;

import com.github.yildizmy.dto.response.EmployeeDto;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.github.yildizmy.common.Constants.FAILED_TO_IMPORT;
import static com.opencsv.ICSVWriter.*;

@SuppressWarnings("java:S1118")
public class CsvHelper {

    public static void exportToCsv(PrintWriter writer, List<EmployeeDto> employees) throws IOException {
        try {
            // create a csv writer
            StatefulBeanToCsv<EmployeeDto> beanWriter = new StatefulBeanToCsvBuilder<EmployeeDto>(writer)
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
