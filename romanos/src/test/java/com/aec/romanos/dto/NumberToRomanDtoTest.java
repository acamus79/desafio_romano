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
class NumberToRomanDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidNumber() {
        NumberToRomanDto dto = new NumberToRomanDto();
        dto.setNumber(1000);
        Set<ConstraintViolation<NumberToRomanDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void testNumberBelowMinimum(int number) {
        NumberToRomanDto dto = new NumberToRomanDto();
        dto.setNumber(number);
        Set<ConstraintViolation<NumberToRomanDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("{min.number.pattern}", violations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {4000, 5000, 10000})
    void testNumberAboveMaximum(int number) {
        NumberToRomanDto dto = new NumberToRomanDto();
        dto.setNumber(number);
        Set<ConstraintViolation<NumberToRomanDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("{max.number.pattern}", violations.iterator().next().getMessage());
    }

    @Test
    void testNullNumber() {
        NumberToRomanDto dto = new NumberToRomanDto();
        Set<ConstraintViolation<NumberToRomanDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Null should not be valid as @NotNull is used");
        assertEquals(1, violations.size(), "Should have exactly one violation");
        assertEquals("{number.null}", violations.iterator().next().getMessage());
    }

    @Test
    void testBuilderAndAllArgsConstructor() {
        NumberToRomanDto dto1 = NumberToRomanDto.builder().number(100).build();
        NumberToRomanDto dto2 = new NumberToRomanDto(100);

        assertEquals(dto1.getNumber(), dto2.getNumber());
        assertEquals(100, dto1.getNumber());
    }
}