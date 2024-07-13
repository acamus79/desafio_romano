package com.aec.romanos.services.impl;
import com.aec.romanos.config.AplicationConfig;
import com.aec.romanos.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AplicationConfig.class, MessageServiceImpl.class})
class MessageServiceImplTest {

    @Autowired
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
    }

    @Test
    void testGetMessageInEnglish() {
        String message = messageService.getMessage("test.message");
        assertEquals("This is a test message", message);
    }

    @Test
    void testGetMessageInSpanish() {
        LocaleContextHolder.setLocale(new Locale("es"));
        String message = messageService.getMessage("test.message");
        assertEquals("Este es un mensaje de prueba", message);
    }

    @Test
    void testGetMessageInPortuguese() {
        LocaleContextHolder.setLocale(new Locale("pt"));
        String message = messageService.getMessage("test.message");
        assertEquals("Esta é uma mensagem de teste", message);
    }

}