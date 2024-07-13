package com.aec.romanos.config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = AplicationConfig.class)
class AplicationConfigTest {

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private MessageSource messageSource;

    @Test
    void testLocaleResolverDefaultLocale() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Locale resolvedLocale = localeResolver.resolveLocale(request);
        assertEquals(Locale.ENGLISH, resolvedLocale);
    }

    @Test
    void testMessageSourceEncoding() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        String message = messageSource.getMessage("test.encoding", null, Locale.ENGLISH);
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        assertTrue(new String(messageBytes, StandardCharsets.UTF_8).equals(message),
                "The message is not encoded in UTF-8");
    }

    @Test
    void testMessageSourceBasename() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        String message = messageSource.getMessage("test.message", null, Locale.ENGLISH);
        assertEquals("This is a test message", message);
    }
}