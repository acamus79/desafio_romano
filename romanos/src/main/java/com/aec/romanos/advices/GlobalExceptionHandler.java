package com.aec.romanos.advices;

import com.aec.romanos.exceptions.InvalidParameterException;
import com.aec.romanos.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageService msg;
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(msg.getMessage("status"), msg.getMessage("error"));
        body.put(msg.getMessage("msg"), msg.getMessage("missing_or_empty_request_body"));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(msg.getMessage("status"), msg.getMessage("error"));
        body.put(msg.getMessage("msg"), msg.getMessage("missing_or_empty_request_param"));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(msg.getMessage("status"), msg.getMessage("error"));
        body.put(msg.getMessage("msg"), msg.getMessage("failed_type"));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(InvalidParameterException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(msg.getMessage("status"), msg.getMessage("error"));
        body.put(msg.getMessage("msg"), ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


}
