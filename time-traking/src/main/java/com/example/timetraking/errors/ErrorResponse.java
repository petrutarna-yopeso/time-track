package com.example.timetraking.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private int statusCode;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "YYY-mm-dd HH:mm:ss")
    private Date timestamp;
    private String message;
    private String description;
}
