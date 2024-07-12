package com.aec.romanos.utils;

import com.aec.romanos.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ValidationUtils {


    /**
     * Handle validation errors and return a response if there are errors.
     *
     * @param errors Validation errors
     * @return ResponseEntity with validation error details or null if no errors
     */
    public static ResponseEntity<Map<String, Object>> handleValidationErrors(Errors errors, MessageService msg) {

        Map<String, Object> response = new HashMap<>();
        if (errors.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (FieldError error : errors.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            response.put(msg.getMessage("status"), msg.getMessage("error"));
            response.put(msg.getMessage("msg"), msg.getMessage("validation_failed"));
            response.put(msg.getMessage("errors"), errorMessages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return null;
    }
}

