package com.demoprojects.smartlead.userMessage.dto;

import jakarta.validation.constraints.NotBlank;

public record UserMessageRequest(
        @NotBlank(message = "Message can't be blank.")
        String content) {
}