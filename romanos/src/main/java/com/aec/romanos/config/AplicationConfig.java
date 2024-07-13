package com.aec.romanos.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * Configuration class for settings in the application.
 */
@Configuration
@RequiredArgsConstructor
public class AplicationConfig {

    private MessageSource messageSource;

    /**
     * Configures the locale resolver for the application.
     * This bean determines the current locale based on the "Accept-Language" header in HTTP requests.
     * It defaults to English if no locale is specified.
     *
     * @return The configured LocaleResolver.
     */
    @Bean
    @DependsOn("messageSource")
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    /**
     * Configures the message source for the application.
     * This bean is responsible for resolving messages from resource bundles (property files)
     * based on the current locale. It sets the base name of the resource bundles to "messages"
     * and uses UTF-8 encoding.
     *
     * @return The configured MessageSource.
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

}
