package com.aec.romanos.controllers;

import com.aec.romanos.dto.NumberToRomanDto;
import com.aec.romanos.dto.RomanToNumberDto;
import com.aec.romanos.services.MessageService;
import com.aec.romanos.services.RomanNumeralService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class NumberControllerTest {

    @Mock
    private RomanNumeralService service;

    @Mock
    private MessageService messageService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private NumberController controller;

    @BeforeEach
    void setUp() {
        when(messageService.getMessage("result")).thenReturn("result");
        when(messageService.getMessage("status")).thenReturn("status");
        when(messageService.getMessage("msg")).thenReturn("message");
        when(messageService.getMessage("count")).thenReturn("count");
        when(messageService.getMessage("success")).thenReturn("success");
        when(messageService.getMessage("success_msg")).thenReturn("Conversion successful.");
        when(messageService.getMessage("error")).thenReturn("error");
        when(messageService.getMessage("validation_failed")).thenReturn("Invalid input");
        when(messageService.getMessage("errors")).thenReturn("errors");
        when(messageService.getMessage("internal_error")).thenReturn("An unexpected error occurred");
    }

    @Test
    void testGetRomanNumber_Success() {
        NumberToRomanDto dto = new NumberToRomanDto();
        dto.setNumber(1999);
        when(service.toRoman(1999)).thenReturn("MCMXCIX");

        ResponseEntity<?> response = controller.getRomanNumber(dto, bindingResult, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertEquals("MCMXCIX", Objects.requireNonNull(body).get("result"));
        assertEquals("success", body.get("status"));
        assertEquals("Conversion successful.", body.get("message"));
        assertNotNull(body.get("count"));
    }

    @Test
    void testGetRomanNumber_ValidationError() {
        NumberToRomanDto dto = new NumberToRomanDto();
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = controller.getRomanNumber(dto, bindingResult, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetRomanNumber_Exception() {
        NumberToRomanDto dto = new NumberToRomanDto();
        dto.setNumber(1999);
        when(service.toRoman(1999)).thenThrow(new RuntimeException("Test exception"));
        when(messageService.getMessage("error")).thenReturn("error");
        when(messageService.getMessage("internal_error")).thenReturn("An unexpected error occurred");

        ResponseEntity<?> response = controller.getRomanNumber(dto, bindingResult, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assert body != null;
        assertEquals("error", body.get("status"));
        assertEquals("An unexpected error occurred", body.get("message"));
    }

    @Test
    void testGetNumber_Success() {
        RomanToNumberDto dto = new RomanToNumberDto();
        dto.setRoman("MCMXCIX");
        when(service.fromRoman("MCMXCIX")).thenReturn(1999);

        ResponseEntity<?> response = controller.getNumber(dto, bindingResult, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertEquals(1999, Objects.requireNonNull(body).get("result"));
        assertEquals("success", body.get("status"));
        assertEquals("Conversion successful.", body.get("message"));
        assertNotNull(body.get("count"));
    }

    @Test
    void testGetNumber_ValidationError() {
        RomanToNumberDto dto = new RomanToNumberDto();
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = controller.getNumber(dto, bindingResult, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetNumber_Exception() {
        RomanToNumberDto dto = new RomanToNumberDto();
        dto.setRoman("MCMXCIX");
        when(service.fromRoman("MCMXCIX")).thenThrow(new RuntimeException("Test exception"));
        when(messageService.getMessage("error")).thenReturn("error");
        when(messageService.getMessage("internal_error")).thenReturn("An unexpected error occurred");

        ResponseEntity<?> response = controller.getNumber(dto, bindingResult, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assert body != null;
        assertEquals("error", body.get("status"));
        assertEquals("An unexpected error occurred", body.get("message"));
    }

    @Test
    void testToRoman_Success() {
        when(service.paramToRoman(1999)).thenReturn("MCMXCIX");

        ResponseEntity<String> response = controller.toRoman(1999);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("MCMXCIX", response.getBody());
    }

    @Test
    void testFromRoman_Success() {
        when(service.paramToNumber("MCMXCIX")).thenReturn(1999);
        ResponseEntity<String> response = controller.fromRoman("MCMXCIX");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.valueOf(1999), response.getBody());
    }

    @Test
    void testLanguageHeader() {
        NumberToRomanDto dto = new NumberToRomanDto();
        dto.setNumber(1999);
        when(service.toRoman(1999)).thenReturn("MCMXCIX");

        ResponseEntity<?> response = controller.getRomanNumber(dto, bindingResult, "es");

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void testCountIncrement() {
        NumberToRomanDto dto1 = new NumberToRomanDto();
        dto1.setNumber(1999);
        RomanToNumberDto dto2 = new RomanToNumberDto();
        dto2.setRoman("MCMXCIX");

        when(service.toRoman(1999)).thenReturn("MCMXCIX");
        when(service.fromRoman("MCMXCIX")).thenReturn(1999);

        ResponseEntity<?> response1 = controller.getRomanNumber(dto1, bindingResult, null);
        ResponseEntity<?> response2 = controller.getNumber(dto2, bindingResult, null);

        Map<String, Object> body1 = (Map<String, Object>) response1.getBody();
        Map<String, Object> body2 = (Map<String, Object>) response2.getBody();

        assert body1 != null;
        assertEquals(1, body1.get("count"));
        assertEquals(2, Objects.requireNonNull(body2).get("count"));
    }

    @ParameterizedTest
    @CsvSource({
            "1, I",
            "4, IV",
            "9, IX",
            "49, XLIX",
            "99, XCIX",
            "500, D",
            "999, CMXCIX",
            "3999, MMMCMXCIX"
    })
    void testToRoman_ParameterizedSuccess(int number, String expectedRoman) {
        when(service.paramToRoman(number)).thenReturn(expectedRoman);

        ResponseEntity<String> response = controller.toRoman(number);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRoman, response.getBody());
    }

    @ParameterizedTest
    @CsvSource({
            "I, 1",
            "IV, 4",
            "IX, 9",
            "XLIX, 49",
            "XCIX, 99",
            "D, 500",
            "CMXCIX, 999",
            "MMMCMXCIX, 3999"
    })
    void testFromRoman_ParameterizedSuccess(String roman, int expectedNumber) {
        when(service.paramToNumber(roman)).thenReturn(expectedNumber);

        ResponseEntity<String> response = controller.fromRoman(roman);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.valueOf(expectedNumber), response.getBody());
    }

    @ParameterizedTest
    @CsvSource({
            "0, The number must be greater than 0",
            "4000, The number must be less than 4000",
            "-1, The number must be greater than 0"
    })
    void testToRoman_InvalidInput(int number, String expectedErrorMessage) {
        NumberToRomanDto dto = new NumberToRomanDto();
        dto.setNumber(number);

        Errors errors = new BeanPropertyBindingResult(dto, "numberToRomanDto");
        errors.rejectValue("number", "error.number", expectedErrorMessage);

        ResponseEntity<?> response = controller.getRomanNumber(dto, errors, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("error", Objects.requireNonNull(responseBody).get("status"));
        assertEquals("Invalid input", responseBody.get("message"));
        List<String> errorMessages = (List<String>) responseBody.get("errors");
        assertTrue(errorMessages.contains(expectedErrorMessage));
    }

    @ParameterizedTest
    @CsvSource({
            "IIII, Invalid Roman numeral",
            "VV, Invalid Roman numeral",
            "IC, Invalid Roman numeral",
            "MMMM, Invalid Roman numeral"
    })
    void testFromRoman_InvalidInput(String roman, String expectedErrorMessage) {
        RomanToNumberDto dto = new RomanToNumberDto();
        dto.setRoman(roman);

        Errors errors = new BeanPropertyBindingResult(dto, "romanToNumberDto");
        errors.rejectValue("roman", "error.roman", expectedErrorMessage);

        ResponseEntity<?> response = controller.getNumber(dto, errors, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("error", Objects.requireNonNull(responseBody).get("status"));
        assertEquals("Invalid input", responseBody.get("message"));
        List<String> errorMessages = (List<String>) responseBody.get("errors");
        assertTrue(errorMessages.contains(expectedErrorMessage));
    }

}