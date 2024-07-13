package com.aec.romanos.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RomanToNumberDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"I", "IV", "IX", "XL", "L", "XC", "C", "CD", "D", "CM", "M", "MCMLIV", "MMXXI"})
    void testValidRomanNumerals(String romanNumeral) {
        RomanToNumberDto dto = new RomanToNumberDto();
        dto.setRoman(romanNumeral);
        Set<ConstraintViolation<RomanToNumberDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Valid Roman numeral " + romanNumeral + " should not produce violations");
    }

    @ParameterizedTest
    @ValueSource(strings = {"IIII", "VV", "LL", "DD", "XXXX", "CCCC", "MMMM", "IC", "XM", "XD", "ABC", "123"})
    void testInvalidRomanNumerals(String invalidRomanNumeral) {
        RomanToNumberDto dto = new RomanToNumberDto();
        dto.setRoman(invalidRomanNumeral);
        Set<ConstraintViolation<RomanToNumberDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Invalid Roman numeral " + invalidRomanNumeral + " should produce violations");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("{roman.invalid}")));
    }

    @Test
    void testNullRomanNumeral() {
        RomanToNumberDto dto = new RomanToNumberDto();
        dto.setRoman(null);
        Set<ConstraintViolation<RomanToNumberDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("{roman.null}")));
    }

    @Test
    void testBlankRomanNumeral() {
        RomanToNumberDto dto = new RomanToNumberDto();
        dto.setRoman("");
        Set<ConstraintViolation<RomanToNumberDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("{roman.blank}")));
    }

    @Test
    void testBuilderAndAllArgsConstructor() {
        RomanToNumberDto dto1 = RomanToNumberDto.builder().roman("X").build();
        RomanToNumberDto dto2 = new RomanToNumberDto("X");

        assertEquals(dto1.getRoman(), dto2.getRoman());
        assertEquals("X", dto1.getRoman());
    }
}