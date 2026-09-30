package com.demoprojects.smartlead.lead.dto;

import com.demoprojects.smartlead.lead.LeadType;
import com.demoprojects.smartlead.lead.UrgencyLevel;

public record LeadResponse(
        Long id,
        String title,
        LeadType type,
        UrgencyLevel urgency,
        String summary) {
}
