package com.aec.romanos.controllers;

import com.aec.romanos.dto.NumberToRomanDto;
import com.aec.romanos.dto.RomanToNumberDto;
import com.aec.romanos.services.MessageService;
import com.aec.romanos.services.RomanNumeralService;
import com.aec.romanos.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Number Controller", description = "API endpoints for converting between numbers and Roman numerals.")
public class NumberController {

    private final RomanNumeralService service;
    private final MessageService msg;
    private final AtomicInteger count = new AtomicInteger(1);

    @PostMapping(value = "/toRoman", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Convert number to Roman numeral",
            description = "Convert an integer to its Roman numeral representation using POST method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversion successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(
                                    value = """
            {
              "result": "MCMXCIX",
              "count": 1,
              "msg": "Conversion successful.",
              "status": "success"             \s
            }
            """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(
                                    value = """
            {
              "message": "Invalid input",
              "errors": [
                "The number must be less than 4000"
              ],
              "status": "error"
            }
            """)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(
                                    value = """
            {
              "message": "An unexpected error occurred",
              "status": "error"
            }
            """)
                    )
            )
    })
    public ResponseEntity<?> getRomanNumber(
            @Parameter(description = "Number to convert", required = true) @RequestBody @Valid NumberToRomanDto request,
            Errors errors,
            @Parameter(description = "Preferred language for response") @RequestHeader(name = "Accept-Language", required = false) String language) {

        setLocale(language);
        Map<String, Object> response = new HashMap<>();

        ResponseEntity<Map<String, Object>> validationErrorsResponse = ValidationUtils.handleValidationErrors(errors, msg);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }

        try {
            response.put(msg.getMessage("result"), service.toRoman(request.getNumber()));
            response.put(msg.getMessage("status"), msg.getMessage("success"));
            response.put(msg.getMessage("msg"), msg.getMessage("success_msg"));
            response.put(msg.getMessage("count"), count.getAndIncrement());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return handleException(response);
        }
    }

    @PostMapping(value = "/toNumber", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Convert Roman numeral to number", description = "Convert a Roman numeral representation to integer using POST method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversion successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(
                                    value = """
            {
              "result": 1899,
              "count": 2,
              "msg": "Conversion successful.",
              "status": "success"
            }
            """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Map.class),
                    examples = @ExampleObject(
                            value = """
            {
                "message": "Invalid input",
                "errors": [
                    "Invalid Roman numeral"
                ],
                "status": "error"
            }
            """)
            )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(
                                    value = """
            {
              "message": "An unexpected error occurred",
              "status": "error"
            }
            """)
                    )
            )
    })
    public ResponseEntity<?> getNumber(
            @Parameter(description = "Roman numeral to convert", required = true) @RequestBody @Valid RomanToNumberDto request,
            Errors errors,
            @Parameter(description = "Preferred language for response") @RequestHeader(name = "Accept-Language", required = false) String language) {

        setLocale(language);

        ResponseEntity<Map<String, Object>> validationErrorsResponse = ValidationUtils.handleValidationErrors(errors, msg);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }

        Map<String, Object> response = new HashMap<>();
        try {
            response.put(msg.getMessage("result"), service.fromRoman(request.getRoman()));
            response.put(msg.getMessage("status"), msg.getMessage("success"));
            response.put(msg.getMessage("msg"), msg.getMessage("success_msg"));
            response.put(msg.getMessage("count"), count.getAndIncrement());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return handleException(response);
        }
    }

    @GetMapping(value = "/toRoman", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Converting an Arabic numeral to a Roman numeral (GET)", description = "Convert an integer to its Roman numeral representation using GET method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversion successful",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> toRoman(
            @Parameter(description = "Number to convert", required = true)
            @RequestParam int number) {
        return ResponseEntity.ok(service.paramToRoman(number));
    }

    @GetMapping(value = "/toNumber", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Convert a Roman numeral to an Arabic numeral (GET)", description = "Convert a Roman numeral representation to integer using GET method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversion successful",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "integer"))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> fromRoman(
            @Parameter(description = "Roman numeral to convert", required = true) @RequestParam String roman) {
        return ResponseEntity.ok(String.valueOf(service.paramToNumber(roman)));
    }

    private void setLocale(String language) {
        if (language != null && !language.isEmpty()) {
            Locale locale = Locale.forLanguageTag(language);
            LocaleContextHolder.setLocale(locale);
        }
    }

    private ResponseEntity<Map<String, Object>> handleException(Map<String, Object> response) {
        response.put(msg.getMessage("status"), msg.getMessage("error"));
        response.put(msg.getMessage("msg"), msg.getMessage("internal_error"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


