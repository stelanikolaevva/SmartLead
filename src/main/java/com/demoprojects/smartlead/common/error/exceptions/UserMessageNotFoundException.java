package com.demoprojects.smartlead.common.error.exceptions;

public class UserMessageNotFoundException extends RuntimeException {

    public UserMessageNotFoundException(String message) {
        super(message);
    }
}
