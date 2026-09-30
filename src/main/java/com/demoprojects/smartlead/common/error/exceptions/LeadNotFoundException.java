package com.demoprojects.smartlead.common.error.exceptions;

public class LeadNotFoundException extends RuntimeException {

    public LeadNotFoundException(String message) {
        super(message);
    }
}
