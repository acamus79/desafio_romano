package com.aec.romanos.services;

/**
 * The MessageService interface defines methods for retrieving internationalized messages.
 */
public interface MessageService {

    /**
     * Retrieves a message based on the provided message code.
     *
     * @param code The code of the message to retrieve.
     * @return The internationalized message corresponding to the provided code.
     */
    String getMessage(String code);

}
