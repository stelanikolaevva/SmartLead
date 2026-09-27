package com.demoprojects.smartlead.model.dto;

import jakarta.validation.constraints.NotBlank;

public record MessageDTO(
        @NotBlank(message = "Message can't be blank.")
        String content
) {
}