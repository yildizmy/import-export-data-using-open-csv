package com.github.yildizmy.common;

public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException(CLASS_CANNOT_BE_INSTANTIATED);
    }

    public static final String TRACE = "trace";
    public static final String CONTENT_TYPE = "text/csv";
    public static final String DATE_PATTERN_FOR_EXPORT = "dd/MM/yyyy";
    public static final String DATE_PATTERN_FOR_IMPORT = "yyyy-MM-dd";

    public static final String SUCCESS = "Success";
    public static final String SUCCESSFULLY_CREATED = "Employee(s) created successfully";
    public static final String VALIDATION_ERROR = "Validation error. Check 'errors' field for details";
    public static final String METHOD_ARGUMENT_NOT_VALID = "MethodArgumentNotValid exception";
    public static final String ENTITY_NOT_FOUND = "Item not found";
    public static final String UNKNOWN_ERROR = "Unknown error occurred";
    public static final String FAILED_TO_IMPORT = "Failed to import data to csv file: ";
    public static final String MAX_UPLOAD_SIZE_EXCEEDED = "Maximum upload size exceeded";
    public static final String CLASS_CANNOT_BE_INSTANTIATED = "This is a utility class and cannot be instantiated";
}
