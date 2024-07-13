package com.aec.romanos.services.impl;

import com.aec.romanos.exceptions.InvalidParameterException;
import com.aec.romanos.services.MessageService;
import com.aec.romanos.services.RomanNumeralService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RomanNumeralServiceImpl implements RomanNumeralService {

    private static final String[] ROMAN_SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private final MessageService messageService;

    /**
     * Converts the given integer number to its Roman numeral representation.
     *
     * @param number The integer number to convert.
     * @return The Roman numeral representation of the input number.
     */
    @Override
    public String toRoman(int number) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < VALUES.length; i++) {
            while (number >= VALUES[i]) {
                number -= VALUES[i];
                result.append(ROMAN_SYMBOLS[i]);
            }
        }
        return result.toString();
    }

    /**
     * Converts the given Roman numeral to its corresponding integer value.
     *
     * @param roman The Roman numeral string to convert.
     * @return The integer value of the Roman numeral.
     */
    @Override
    public int fromRoman(String roman) {
        int result = 0;
        int index = 0;
        for (int i = 0; i < ROMAN_SYMBOLS.length; i++) {
            while (roman.startsWith(ROMAN_SYMBOLS[i], index)) {
                result += VALUES[i];
                index += ROMAN_SYMBOLS[i].length();
            }
        }
        if (!toRoman(result).equals(roman)) {
            throw new InvalidParameterException("roman.invalid",messageService);
        }
        return result;
    }

    /**
     * Converts the provided integer number to its Roman numeral representation.
     * Validates the number range and throws an exception if invalid.
     *
     * @param number The integer number to convert to Roman numeral.
     * @return The Roman numeral representation of the input number.
     * @throws InvalidParameterException If the number is out of valid range.
     */
    @Override
    public String paramToRoman(int number) {
        if ( number <= 0 || number >= 4000) {
            throw new InvalidParameterException("invalid.number.range", messageService);
        }
        return this.toRoman(number);
    }

    /**
     * Converts the provided Roman numeral string to its integer value.
     *
     * @param roman The Roman numeral string to convert to an integer.
     * @return The integer value of the Roman numeral.
     * @throws InvalidParameterException If the Roman numeral is invalid.
     */
    @Override
    public int paramToNumber(String roman) {
        String romanRegex = "^(?=[MDCLXVI])M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

        if (!roman.matches(romanRegex)) {
            throw new InvalidParameterException("roman.invalid", messageService);
        }
        return this.fromRoman(roman);
    }

}
