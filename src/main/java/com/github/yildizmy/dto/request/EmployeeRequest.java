package com.github.yildizmy.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvDate;
import lombok.*;

import java.time.LocalDate;

import static com.github.yildizmy.common.Constants.DATE_PATTERN_FOR_EXPORT;
import static com.github.yildizmy.common.Constants.DATE_PATTERN_FOR_IMPORT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    @NonNull
    @JsonProperty("first_name")
    private String firstName;

    @NonNull
    @JsonProperty("last_name")
    private String lastName;

    @NonNull
    @JsonProperty("email")
    private String email;

    @NonNull
    @JsonProperty("country")
    private String country;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN_FOR_EXPORT) // for writing to JSON (same format as in the file)
    @CsvDate(value = DATE_PATTERN_FOR_IMPORT) // for reading from CSV (same format as in the file)
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;
}
