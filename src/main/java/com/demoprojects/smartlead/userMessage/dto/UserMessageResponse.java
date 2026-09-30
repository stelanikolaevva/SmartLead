package com.demoprojects.smartlead.userMessage.dto;

import com.demoprojects.smartlead.userMessage.Status;

public record UserMessageResponse(Long id, String content, Status status) {
}
