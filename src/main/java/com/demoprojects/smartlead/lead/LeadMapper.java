package com.demoprojects.smartlead.lead;

import com.demoprojects.smartlead.lead.dto.LeadResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeadMapper {

    @Mapping(target = "type", source = "leadType")
    @Mapping(target = "urgency", source = "urgencyLevel")
    LeadResponse mapToResponse(Lead lead);
}
