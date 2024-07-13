package com.aec.romanos.services;

import com.aec.romanos.exceptions.InvalidParameterException;

/**
 * The RomanNumeralService interface defines methods for converting Arabic numerals to Roman numerals
 * and conversely from Roman numerals to Arabic numerals.
 */
public interface RomanNumeralService {

    /**
     * Converts the given integer number to its Roman numeral representation.
     *
     * @param number The integer number to convert.
     * @return The Roman numeral representation of the input number.
     */
    String toRoman(int number);

    /**
     * Converts the given Roman numeral to its corresponding integer value.
     *
     * @param roman The Roman numeral string to convert.
     * @return The integer value of the Roman numeral.
     */
    int fromRoman(String roman);

    /**
     * Converts the provided integer number to its Roman numeral representation.
     * Validates the number range and throws an exception if invalid.
     *
     * @param number The integer number to convert to Roman numeral.
     * @return The Roman numeral representation of the input number.
     * @throws InvalidParameterException If the number is out of valid range.
     */
    String paramToRoman(int number);

    /**
     * Converts the provided Roman numeral string to its integer value.
     *
     * @param roman The Roman numeral string to convert to an integer.
     * @return The integer value of the Roman numeral.
     * @throws InvalidParameterException If the Roman numeral is invalid.
     */
    int paramToNumber(String roman);

}