package com.demoprojects.smartlead.ai.dto;

import com.demoprojects.smartlead.lead.LeadType;
import com.demoprojects.smartlead.lead.UrgencyLevel;

public record LeadClassificationResponse(
        boolean isLead,
        String title,
        LeadType type,
        UrgencyLevel urgency,
        String summary,
        String reason) {
}