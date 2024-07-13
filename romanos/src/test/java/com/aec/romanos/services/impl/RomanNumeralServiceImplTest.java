package com.aec.romanos.services.impl;

import com.aec.romanos.exceptions.InvalidParameterException;
import com.aec.romanos.services.MessageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
@SpringBootTest
public class RomanNumeralServiceImplTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private RomanNumeralServiceImpl romanNumeralService;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testToRomanValid() {
        assertEquals("I", romanNumeralService.toRoman(1));
        assertEquals("IV", romanNumeralService.toRoman(4));
        assertEquals("IX", romanNumeralService.toRoman(9));
        assertEquals("XII", romanNumeralService.toRoman(12));
        assertEquals("XXI", romanNumeralService.toRoman(21));
        assertEquals("XL", romanNumeralService.toRoman(40));
        assertEquals("XC", romanNumeralService.toRoman(90));
        assertEquals("C", romanNumeralService.toRoman(100));
        assertEquals("CD", romanNumeralService.toRoman(400));
        assertEquals("CM", romanNumeralService.toRoman(900));
        assertEquals("MMMCMXCIX", romanNumeralService.toRoman(3999));
    }

    @Test
    public void testParamToRomanInvalid() {
        when(messageService.getMessage("invalid.number.range")).thenReturn("Number out of range");

        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToRoman(0));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToRoman(4000));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToRoman(4001));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToRoman(-10));
    }

    @Test
    public void testParamToNumberValid() {
        when(messageService.getMessage("roman.invalid")).thenReturn("Invalid Roman numeral");

        assertEquals(1, romanNumeralService.paramToNumber("I"));
        assertEquals(4, romanNumeralService.paramToNumber("IV"));
        assertEquals(9, romanNumeralService.paramToNumber("IX"));
        assertEquals(12, romanNumeralService.paramToNumber("XII"));
        assertEquals(21, romanNumeralService.paramToNumber("XXI"));
        assertEquals(40, romanNumeralService.paramToNumber("XL"));
        assertEquals(90, romanNumeralService.paramToNumber("XC"));
        assertEquals(100, romanNumeralService.paramToNumber("C"));
        assertEquals(400, romanNumeralService.paramToNumber("CD"));
        assertEquals(900, romanNumeralService.paramToNumber("CM"));
        assertEquals(3999, romanNumeralService.paramToNumber("MMMCMXCIX"));
    }

    @Test
    public void testParamToNumberInvalid() {
        when(messageService.getMessage("roman.invalid")).thenReturn("Invalid Roman numeral");

        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToNumber("IIII"));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToNumber("VV"));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToNumber("XXXX"));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToNumber("LL"));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToNumber("CCCC"));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToNumber("DD"));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToNumber("MMMM"));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToNumber("IC"));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToNumber("IL"));
        assertThrows(InvalidParameterException.class, () -> romanNumeralService.paramToNumber("VX"));
    }

}
