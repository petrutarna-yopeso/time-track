package com.example.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
public class ErrorResponse {

    private int statusCode;

    @Builder.Default
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "YY-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    private String description;

}
