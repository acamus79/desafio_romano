package com.aec.romanos.controllers;

import com.aec.romanos.dto.NumberToRomanDto;
import com.aec.romanos.dto.RomanToNumberDto;
import com.aec.romanos.services.MessageService;
import com.aec.romanos.services.RomanNumeralService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
public class NumberControllerTest {

    @Mock
    private RomanNumeralService romanNumeralService;

        @Mock
        private MessageService messageService;

        @Mock
        private BindingResult bindingResult;

        @InjectMocks
        private NumberController numberController;

        @BeforeEach
        void setUp() {
            when(messageService.getMessage("result")).thenReturn("result");
            when(messageService.getMessage("status")).thenReturn("status");
            when(messageService.getMessage("msg")).thenReturn("msg");
            when(messageService.getMessage("count")).thenReturn("count");
            when(messageService.getMessage("success")).thenReturn("success");
            when(messageService.getMessage("success_msg")).thenReturn("success_msg");
        }

        @Test
        void testGetRomanNumber() {
            NumberToRomanDto dto = new NumberToRomanDto();
            dto.setNumber(5);
            when(romanNumeralService.toRoman(5)).thenReturn("V");

            ResponseEntity<?> response = numberController.getRomanNumber(dto, bindingResult, null);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody() instanceof Map);
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            assertEquals("V", responseBody.get("result"));
            assertEquals("success", responseBody.get("status"));
            assertEquals("success_msg", responseBody.get("msg"));
            assertEquals(1, responseBody.get("count"));
        }

        @Test
        void testGetNumber() {
            RomanToNumberDto dto = new RomanToNumberDto();
            dto.setRoman("V");
            when(romanNumeralService.fromRoman("V")).thenReturn(5);

            ResponseEntity<?> response = numberController.getNumber(dto, bindingResult, null);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody() instanceof Map);
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            assertEquals(5, responseBody.get("result"));
            assertEquals("success", responseBody.get("status"));
            assertEquals("success_msg", responseBody.get("msg"));
            assertEquals(1, responseBody.get("count"));
        }

        @Test
        void testToRoman() {
            when(romanNumeralService.paramToRoman(5)).thenReturn("V");

            ResponseEntity<String> response = numberController.toRoman(5);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("V", response.getBody());
        }

        @Test
        void testFromRoman() {
            when(romanNumeralService.fromRoman("V")).thenReturn(5);

            ResponseEntity<Integer> response = numberController.fromRoman("V");

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(5, response.getBody());
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
    void testGetRomanNumberBoundaryValues(int number, String expectedRoman) {
        NumberToRomanDto dto = new NumberToRomanDto();
        dto.setNumber(number);
        when(romanNumeralService.toRoman(number)).thenReturn(expectedRoman);

        ResponseEntity<?> response = numberController.getRomanNumber(dto, bindingResult, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(expectedRoman, responseBody.get("result"));
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
    void testGetNumberBoundaryValues(String roman, int expectedNumber) {
        RomanToNumberDto dto = new RomanToNumberDto();
        dto.setRoman(roman);
        when(romanNumeralService.fromRoman(roman)).thenReturn(expectedNumber);

        ResponseEntity<?> response = numberController.getNumber(dto, bindingResult, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(expectedNumber, responseBody.get("result"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "MMMM", "IVI"})
    void testGetNumberInvalidValues(String roman) {
        RomanToNumberDto dto = new RomanToNumberDto();
        dto.setRoman(roman);
        when(romanNumeralService.fromRoman(roman)).thenThrow(new IllegalArgumentException("Invalid Roman numeral"));

        ResponseEntity<?> response = numberController.getNumber(dto, bindingResult, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("error", responseBody.get("status"));
    }

}


