package com.github.yildizmy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private final String message;
    private final T data;
    private Long timestamp;
    private String trace;

    public ApiResponse(Long timestamp, String message, T data) {
        this.timestamp = timestamp;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
        this.data = null;
    }
}
