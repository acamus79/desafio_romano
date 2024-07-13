package com.aec.romanos.exceptions;

import com.aec.romanos.services.MessageService;

public class InvalidParameterException extends RuntimeException{

    private final transient MessageService messageService;
    public InvalidParameterException(String code, MessageService messageService) {
        super(messageService.getMessage(code));
        this.messageService = messageService;
    }

    public InvalidParameterException(String code, Throwable cause, MessageService messageService) {
        super(messageService.getMessage(code), cause);
        this.messageService = messageService;
    }

    public InvalidParameterException(String code, Throwable cause) {
        super(code, cause);
        this.messageService = null;
    }

}
